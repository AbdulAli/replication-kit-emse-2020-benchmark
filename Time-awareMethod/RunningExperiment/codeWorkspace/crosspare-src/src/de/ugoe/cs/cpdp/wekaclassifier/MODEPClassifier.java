// Copyright 2015 Georg-August-Universität Göttingen, Germany
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package de.ugoe.cs.cpdp.wekaclassifier;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.UniformMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.runner.AbstractAlgorithmRunner;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.solutionattribute.impl.NumberOfViolatedConstraints;
import org.uma.jmetal.util.solutionattribute.impl.OverallConstraintViolation;

import de.ugoe.cs.util.console.Console;
import weka.classifiers.AbstractClassifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

/**
 * <p>
 * Implements MODEP after Canfora et al., 2013. The class learns the family of MODEP classifiers
 * with the NSGAII algorithm. Based on a defined threshold for the desired recall, we then pick the
 * best of the results, i.e., the one that requires the fewest LOC and is still achieves the desired
 * recall.
 * </p>
 * <p>
 * Our implementation currently only allows a threshold for the desired recall, not for the desired
 * number of LOC.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class MODEPClassifier extends AbstractClassifier {

    /** Default serial ID */
    private static final long serialVersionUID = 1L;

    /**
     * Coefficients of the calculated solution
     */
    double[][] solutionCoefficients = null;

    /**
     * Threshold for the desired recall; default 0.7
     */
    double desiredRecall = 0.7;

    /*
     * (non-Javadoc)
     * 
     * @see weka.classifiers.AbstractClassifier#setOptions(java.lang.String[])
     */
    @Override
    public void setOptions(String[] options) throws Exception {
        String desiredRecallString = Utils.getOption('R', options);
        if (!desiredRecallString.isEmpty()) {
            desiredRecall = Double.parseDouble(desiredRecallString);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see weka.classifiers.AbstractClassifier#distributionForInstance(weka.core.Instance)
     */
    @Override
    public double[] distributionForInstance(Instance instance) throws Exception {
        return distributionForInstance(solutionCoefficients, instance);
    }

    /*
     * (non-Javadoc)
     * 
     * @see weka.classifiers.Classifier#buildClassifier(weka.core.Instances)
     */
    @Override
    public void buildClassifier(Instances traindata) throws Exception {
        double desiredInstances =
            desiredRecall * traindata.attributeStats(traindata.classIndex()).nominalCounts[1];
        MyNSGAIIRunner nsgaIIrunner = new MyNSGAIIRunner(traindata, desiredInstances);
        List<DoubleSolution> solutions = nsgaIIrunner.run();
        DoubleSolution bestSolution = null;
        for (DoubleSolution solution : solutions) {
            if (solution.getObjective(0) >= desiredInstances) {
                if (bestSolution == null) {
                    bestSolution = solution;
                }
                else {
                    if (bestSolution.getObjective(1) < solution.getObjective(1)) {
                        bestSolution = solution;
                    }
                }
            }
        }

        if (bestSolution == null) {
            Console.trace(Level.WARNING,
                          "no solution with desired recall found, pick best recall instead");
            for (DoubleSolution solution : solutions) {
                if (bestSolution == null) {
                    bestSolution = solution;
                }
                if (solution.getObjective(0) > bestSolution.getObjective(0)) {
                    bestSolution = solution;
                }
            }
        }

        solutionCoefficients = solutionToCoefficients(bestSolution);
    }

    /**
     * <p>
     * Internal helper method to get the distribution for an instance given the coefficients of the
     * logistic regression
     * </p>
     *
     * @param coefficients
     *            the coefficients
     * @param instance
     *            the instance
     * @return probability for each class
     */
    private static double[] distributionForInstance(double[][] coefficients, Instance instance) {
        int numClasses = instance.classAttribute().numValues();
        double[] results = new double[numClasses];

        for (int i = 0; i < numClasses; i++) {
            int k = 0;
            for (int j = 0; j < instance.numAttributes(); j++) {
                if (j != instance.classIndex()) {
                    results[i] += coefficients[i][k] * instance.value(j);
                    k++;
                }
            }
            results[i] = sigmoid(results[i]);
        }
        return results;
    }

    /**
     * <p>
     * returns the classification of an instance with the logistic regression for the given
     * coefficients
     * </p>
     *
     * @param coefficients
     *            the coefficients
     * @param instance
     *            the instance
     * @return the binary classification
     */
    private static double logisticRegression(double[][] coefficients, Instance instance) {
        double[] results = distributionForInstance(coefficients, instance);
        double maxResult = Double.MIN_VALUE;
        int maxIndex = 0;
        for (int i = 0; i < results.length; i++) {
            if (maxResult < results[i]) {
                maxResult = results[i];
                maxIndex = i;
            }
        }
        return Double.parseDouble(instance.classAttribute().value(maxIndex));
    }

    /**
     * <p>
     * Sigmoid function, required for logistic regression
     * </p>
     *
     * @param z
     *            value for which the sigmoid is calcualted
     * @return 1/(1+exp(-z))
     */
    private static double sigmoid(double z) {
        return 1.0 / (1.0 + Math.exp(-z));
    }

    /**
     * <p>
     * Converts a {@link DoubleSolution} into a coefficient matrix
     * </p>
     *
     * @param solution
     *            solution the is converted
     * @return coefficient matrix
     */
    public static double[][] solutionToCoefficients(DoubleSolution solution) {
        int numberOfVariables = solution.getNumberOfVariables();
        int numberOfCoefficients = numberOfVariables / 2;

        double[][] coefficients = new double[2][numberOfCoefficients];

        for (int i = 0; i < numberOfVariables; i++) {
            if (i < numberOfCoefficients) {
                coefficients[0][i] = solution.getVariableValue(i);
            }
            else {
                coefficients[1][i - numberOfCoefficients] = solution.getVariableValue(i);
            }
        }
        return coefficients;
    }

    /**
     * <p>
     * Executes the NSGAII algorithm with the MODEP problem.
     * </p>
     * 
     * @author Steffen Herbold
     */
    private class MyNSGAIIRunner extends AbstractAlgorithmRunner {

        /**
         * configured algorithm to solve the {@link MODEPProblem}.
         */
        final Algorithm<List<DoubleSolution>> algorithm;

        /**
         * <p>
         * Constructor. Configures the algorithm object.
         * </p>
         *
         * @param data
         *            data for which the MODEP problem is optimized
         * @param minEffectiveness
         *            minimal desired effectiveness
         */
        private MyNSGAIIRunner(Instances data, double minEffectiveness) {
            final Problem<DoubleSolution> problem = new MODEPProblem(data, minEffectiveness);
            double crossoverProbability = 0.6;
            double crossoverDistributionIndex = 20.0;
            final CrossoverOperator<DoubleSolution> crossover =
                new SBXCrossover(crossoverProbability, crossoverDistributionIndex);

            double mutationProbability = 0.05;
            double mutationPerturbation = 0.0;
            final MutationOperator<DoubleSolution> mutation =
                new UniformMutation(mutationProbability, mutationPerturbation);
            final SelectionOperator<List<DoubleSolution>, DoubleSolution> selection =
                new BinaryTournamentSelection<DoubleSolution>(new RankingAndCrowdingDistanceComparator<DoubleSolution>());

            algorithm = new NSGAIIBuilder<DoubleSolution>(problem, crossover, mutation)
                .setSelectionOperator(selection).setMaxIterations(400).setPopulationSize(100)
                .build();
        }

        /**
         * <p>
         * Executes the NSGAII algorithm.
         * </p>
         *
         * @return solutions of the problem; should be a Pareto front
         */
        public List<DoubleSolution> run() {
            AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
            List<DoubleSolution> population = algorithm.getResult();
            Console.traceln(Level.FINEST,
                            "genetic algorithm run time: " + algorithmRunner.getComputingTime());
            return population;
        }
    }

    /**
     * <p>
     * Problem definition of MODEP.
     * </p>
     * 
     * @author Steffen Herbold
     */
    private class MODEPProblem extends AbstractDoubleProblem
        implements ConstrainedProblem<DoubleSolution>
    {
        /** Default serial ID */
        private static final long serialVersionUID = 1L;

        /**
         * Data for which MODEP is defined
         */
        private final Instances data;

        /**
         * Minimal desired effectiveness.
         */
        private final double minEffectiveness;

        /**
         * Stores the contraint violations for all current solutions
         */
        public OverallConstraintViolation<DoubleSolution> overallConstraintViolationDegree;

        /**
         * Stores the number of violated constraints for all current solutions.
         */
        public NumberOfViolatedConstraints<DoubleSolution> numberOfViolatedConstraints;

        /**
         * <p>
         * Constructor. Initializes the MODEP problem.
         * </p>
         *
         * @param data
         *            data for which MODEP is defined
         * @param minEffectiveness
         *            minimal desired effectiveness
         */
        public MODEPProblem(Instances data, double minEffectiveness) {
            this.data = data;
            this.minEffectiveness = minEffectiveness;
            setNumberOfVariables(2 * (data.numAttributes() - 1));
            setNumberOfObjectives(2);
            setNumberOfConstraints(1);
            setName("MODEPProblem");

            List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());
            List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());

            for (int i = 0; i < getNumberOfVariables(); i++) {
                lowerLimit.add(-100.0);
                upperLimit.add(100.0);
            }

            setLowerLimit(lowerLimit);
            setUpperLimit(upperLimit);

            overallConstraintViolationDegree = new OverallConstraintViolation<>();
            numberOfViolatedConstraints = new NumberOfViolatedConstraints<>();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.uma.jmetal.problem.Problem#evaluate(org.uma.jmetal.solution.Solution)
         */
        @Override
        public void evaluate(DoubleSolution solution) {
            double[][] coefficients = solutionToCoefficients(solution);

            final Attribute loc = data.attribute("loc");
            double effectiveness = 0.0;
            double cost = 0.0;
            for (int i = 0; i < data.size(); i++) {
                double currentClass = logisticRegression(coefficients, data.get(i));
                if (currentClass == 1.0) {
                    if (data.get(i).classValue() == 1.0) {
                        effectiveness++;
                    }
                    cost -= data.get(i).value(loc);
                }
            }

            solution.setObjective(0, effectiveness);
            solution.setObjective(1, cost);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.uma.jmetal.problem.ConstrainedProblem#evaluateConstraints(org.uma.jmetal.solution.
         * Solution)
         */
        @Override
        public void evaluateConstraints(DoubleSolution solution) {
            double constraintViolation = minEffectiveness - solution.getObjective(0);
            if (constraintViolation > 0) {
                overallConstraintViolationDegree.setAttribute(solution, constraintViolation);
                numberOfViolatedConstraints.setAttribute(solution, 1);
            }
            else {
                overallConstraintViolationDegree.setAttribute(solution, 0.0);
                numberOfViolatedConstraints.setAttribute(solution, 0);
            }
        }
    }
}
