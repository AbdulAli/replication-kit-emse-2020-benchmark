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

package de.ugoe.cs.cpdp.dataprocessing;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Logarithm transformation after Carmargo Cruz and Ochimizu: Towards Logistic Regression Models for
 * Predicting Fault-prone Code across Software Projects. <br>
 * <br>
 * Transform each attribute value x into log(x+1).
 * 
 * @author Steffen Herbold
 */
public class LogarithmTransform implements ISetWiseProcessingStrategy, IProcessesingStrategy {

    /**
     * Does not have parameters. String is ignored.
     * 
     * @param parameters
     *            ignored
     */
    @Override
    public void setParameter(String parameters) {
        // dummy
    }

    /**
     * @see de.ugoe.cs.cpdp.dataprocessing.SetWiseProcessingStrategy#apply(weka.core.Instances,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
        final Attribute classAttribute = testdata.classAttribute();

        // preprocess testdata
        for (int i = 0; i < testdata.numInstances(); i++) {
            Instance instance = testdata.instance(i);
            for (int j = 0; j < testdata.numAttributes(); j++) {
                if (testdata.attribute(j) != classAttribute && testdata.attribute(j).isNumeric()) {
                    if (instance.value(j) < 0) {
                        instance.setValue(j, (-1 * (Math.log(-1 * instance.value(j)))));
                    }
                    else {
                        instance.setValue(j, Math.log(1 + instance.value(j)));
                    }
                }
            }
        }

        // preprocess training data
        for (Instances traindata : traindataSet) {
            for (int i = 0; i < traindata.numInstances(); i++) {
                Instance instance = traindata.instance(i);
                for (int j = 0; j < testdata.numAttributes(); j++) {
                    if (traindata.attribute(j) != classAttribute &&
                        traindata.attribute(j).isNumeric())
                    {
                        if (instance.value(j) < 0) {
                            instance.setValue(j, (-1 * (Math.log(-1 * instance.value(j)))));
                        }
                        else {
                            instance.setValue(j, Math.log(1 + instance.value(j)));
                        }
                    }
                }
            }
        }
    }

    /**
     * @see de.ugoe.cs.cpdp.dataprocessing.ProcessesingStrategy#apply(weka.core.Instances,
     *      weka.core.Instances)
     */
    @Override
    public void apply(Instances testdata, Instances traindata) {
        final Attribute classAttribute = testdata.classAttribute();

        // preprocess testdata
        for (int i = 0; i < testdata.numInstances(); i++) {
            Instance instance = testdata.instance(i);
            for (int j = 0; j < testdata.numAttributes(); j++) {
                if (testdata.attribute(j) != classAttribute && testdata.attribute(j).isNumeric()) {
                    if (instance.value(j) < 0) {
                        instance.setValue(j, (-1 * (Math.log(-1 * instance.value(j)))));
                    }
                    else {
                        instance.setValue(j, Math.log(1 + instance.value(j)));
                    }
                }
            }
        }

        // preprocess training data
        for (int i = 0; i < traindata.numInstances(); i++) {
            Instance instance = traindata.instance(i);
            for (int j = 0; j < testdata.numAttributes(); j++) {
                if (traindata.attribute(j) != classAttribute &&
                    traindata.attribute(j).isNumeric())
                {
                    if (instance.value(j) < 0) {
                        instance.setValue(j, (-1 * (Math.log(-1 * instance.value(j)))));
                    }
                    else {
                        instance.setValue(j, Math.log(1 + instance.value(j)));
                    }
                }
            }
        }
    }
}
