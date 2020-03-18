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

package de.ugoe.cs.cpdp.execution;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import de.ugoe.cs.cpdp.ExperimentConfiguration;
import de.ugoe.cs.cpdp.dataprocessing.IProcessesingStrategy;
import de.ugoe.cs.cpdp.dataselection.IPointWiseDataselectionStrategy;
import de.ugoe.cs.cpdp.eval.IEvaluationStrategy;
import de.ugoe.cs.cpdp.eval.IResultStorage;
import de.ugoe.cs.cpdp.loader.IVersionLoader;
import de.ugoe.cs.cpdp.training.ISetWiseTestdataAwareTrainingStrategy;
import de.ugoe.cs.cpdp.training.ISetWiseTrainingStrategy;
import de.ugoe.cs.cpdp.training.ITestAwareTrainingStrategy;
import de.ugoe.cs.cpdp.training.ITrainer;
import de.ugoe.cs.cpdp.training.ITrainingStrategy;
import de.ugoe.cs.cpdp.training.IWekaCompatibleTrainer;
import de.ugoe.cs.cpdp.versions.IVersionFilter;
import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import de.ugoe.cs.util.console.Console;
import weka.core.Instances;

/**
 * This execution strategy allows for within-project experiments, where the first part of the data
 * is used for training and the second part of the data is used for testing. The percentage for the
 * split is defined using the "param" attribute.
 * 
 * This experiment should only be used with time-ordered data in order to guarantee that only the
 * past is used to predict the future.
 * 
 * @author Steffen Herbold
 */
public class WithinProjectOrderedSplitExperiment implements IExecutionStrategy {

    /**
     * configuration of the experiment
     */
    protected final ExperimentConfiguration config;

    /**
     * Constructor. Creates a new experiment based on a configuration.
     * 
     * @param config
     *            configuration of the experiment
     */
    public WithinProjectOrderedSplitExperiment(ExperimentConfiguration config) {
        this.config = config;
    }

    /**
     * Executes the experiment with the steps as described in the class comment.
     * 
     * @see Runnable#run()
     */
    @Override
    public void run() {
        final List<SoftwareVersion> versions = new LinkedList<>();

        for (IVersionLoader loader : config.getLoaders()) {
            versions.addAll(loader.load());
        }

        for (IVersionFilter filter : config.getVersionFilters()) {
            filter.apply(versions);
        }
        boolean writeHeader = true;
        int versionCount = 1;
        int testVersionCount = 0;
        int numTrainers = 0;

        for (SoftwareVersion testVersion : versions) {
            if (isVersion(testVersion, config.getTestVersionFilters())) {
                testVersionCount++;
            }
        }

        numTrainers += config.getSetWiseTrainers().size();
        numTrainers += config.getSetWiseTestdataAwareTrainers().size();
        numTrainers += config.getTrainers().size();
        numTrainers += config.getTestAwareTrainers().size();

        // sort versions
        Collections.sort(versions);

        for (SoftwareVersion testVersion : versions) {
            if (isVersion(testVersion, config.getTestVersionFilters())) {
                Console.traceln(Level.INFO,
                                String.format("[%s] [%02d/%02d] %s: starting",
                                              config.getExperimentName(), versionCount,
                                              testVersionCount, testVersion.getVersion()));
                int numResultsAvailable = resultsAvailable(testVersion);
                if (numResultsAvailable >= numTrainers * config.getRepetitions()) {
                    Console.traceln(Level.INFO,
                                    String.format(
                                                  "[%s] [%02d/%02d] %s: results already available; skipped",
                                                  config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion()));
                    versionCount++;
                    continue;
                }

                // Setup testdata and training data
                Instances testdata = testVersion.getInstances();
                List<Double> efforts = testVersion.getEfforts();

                // now split data into parts
                double percentage = 0.5; // 0.5 as default value
                String param = config.getExecutionStrategyParameters();
                if (config.getExecutionStrategyParameters() != null) {
                    try {
                        percentage = Double.parseDouble(param);
                    }
                    catch (NumberFormatException e) {
                        throw new RuntimeException("invalid execution strategy parameter, must be numeric: " +
                            param);
                    }
                }
                int initialTestSize = testdata.size();
                Instances traindata = new Instances(testdata);
                for (int i = initialTestSize - 1; i >= 0; i--) {
                    if ((((double) i) / initialTestSize) < percentage) {
                        testdata.delete(i);
                        if (efforts != null) {
                            efforts.remove(i);
                        }
                    }
                    else {
                        traindata.delete(i);
                    }
                }

                for (IProcessesingStrategy processor : config.getPreProcessors()) {
                    Console.traceln(Level.FINE,
                                    String.format("[%s] [%02d/%02d] %s: applying preprocessor %s",
                                                  config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion(),
                                                  processor.getClass().getName()));
                    processor.apply(testdata, traindata);
                }
                for (IPointWiseDataselectionStrategy dataselector : config
                    .getPointWiseSelectors())
                {
                    Console.traceln(Level.FINE,
                                    String.format(
                                                  "[%s] [%02d/%02d] %s: applying pointwise selection %s",
                                                  config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion(),
                                                  dataselector.getClass().getName()));
                    traindata = dataselector.apply(testdata, traindata);
                }
                for (IProcessesingStrategy processor : config.getPostProcessors()) {
                    Console.traceln(Level.FINE,
                                    String.format(
                                                  "[%s] [%02d/%02d] %s: applying setwise postprocessor %s",
                                                  config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion(),
                                                  processor.getClass().getName()));
                    processor.apply(testdata, traindata);
                }
                for (ITrainingStrategy trainer : config.getTrainers()) {
                    Console.traceln(Level.FINE,
                                    String.format("[%s] [%02d/%02d] %s: applying trainer %s",
                                                  config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion(),
                                                  trainer.getName()));
                    trainer.apply(traindata);
                }
                for (ITestAwareTrainingStrategy trainer : config.getTestAwareTrainers()) {
                    Console.traceln(Level.FINE,
                                    String.format("[%s] [%02d/%02d] %s: applying trainer %s",
                                                  config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion(),
                                                  trainer.getName()));
                    trainer.apply(testdata, traindata);
                }
                File resultsDir = new File(config.getResultsPath());
                if (!resultsDir.exists()) {
                    resultsDir.mkdir();
                }
                for (IEvaluationStrategy evaluator : config.getEvaluators()) {
                    Console.traceln(Level.FINE,
                                    String.format("[%s] [%02d/%02d] %s: applying evaluator %s",
                                                  config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion(),
                                                  evaluator.getClass().getName()));
                    List<ITrainer> allTrainers = new LinkedList<>();
                    for (ITrainingStrategy trainer : config.getTrainers()) {
                        allTrainers.add(trainer);
                    }
                    for (ITestAwareTrainingStrategy trainer : config.getTestAwareTrainers()) {
                        allTrainers.add(trainer);
                    }
                    if (writeHeader) {
                        evaluator.setParameter(config.getResultsPath() + "/" +
                            config.getExperimentName() + ".csv");
                    }
                    evaluator.apply(null,testdata, traindata, allTrainers, efforts, writeHeader,
                                    config.getResultStorages());
                    writeHeader = false;
                }
                Console.traceln(Level.INFO,
                                String.format("[%s] [%02d/%02d] %s: finished",
                                              config.getExperimentName(), versionCount,
                                              testVersionCount, testVersion.getVersion()));
                versionCount++;
            }
        }
    }

    /**
     * Helper method that checks if a version passes all filters.
     * 
     * @param version
     *            version that is checked
     * @param filters
     *            list of the filters
     * @return true, if the version passes all filters, false otherwise
     */
    private boolean isVersion(SoftwareVersion version, List<IVersionFilter> filters) {
        boolean result = true;
        for (IVersionFilter filter : filters) {
            result &= !filter.apply(version);
        }
        return result;
    }

    /**
     * <p>
     * helper function that checks if the results are already in the data store
     * </p>
     *
     * @param version
     *            version for which the results are checked
     * @return
     */
    private int resultsAvailable(SoftwareVersion version) {
        if (config.getResultStorages().isEmpty()) {
            return 0;
        }

        List<ITrainer> allTrainers = new LinkedList<>();
        for (ISetWiseTrainingStrategy setwiseTrainer : config.getSetWiseTrainers()) {
            allTrainers.add(setwiseTrainer);
        }
        for (ISetWiseTestdataAwareTrainingStrategy setwiseTestdataAwareTrainer : config
            .getSetWiseTestdataAwareTrainers())
        {
            allTrainers.add(setwiseTestdataAwareTrainer);
        }
        for (ITrainingStrategy trainer : config.getTrainers()) {
            allTrainers.add(trainer);
        }
        for (ITestAwareTrainingStrategy trainer : config.getTestAwareTrainers()) {
            allTrainers.add(trainer);
        }

        int available = Integer.MAX_VALUE;
        for (IResultStorage storage : config.getResultStorages()) {
            String classifierName = ((IWekaCompatibleTrainer) allTrainers.get(0)).getName();
            int curAvailable = storage.containsResult(config.getExperimentName(),
                                                      version.getVersion(), classifierName);
            if (curAvailable < available) {
                available = curAvailable;
            }
        }
        return available;
    }
}
