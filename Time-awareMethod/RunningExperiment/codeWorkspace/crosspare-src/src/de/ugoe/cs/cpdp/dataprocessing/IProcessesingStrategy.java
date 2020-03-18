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

import weka.core.Instances;
import de.ugoe.cs.cpdp.IParameterizable;

/**
 * A data processing strategy that is applied to the test data and a single set of training data.
 * 
 * @author Steffen Herbold
 */
public interface IProcessesingStrategy extends IParameterizable {

    /**
     * Applies the processing strategy.
     * 
     * @param testdata
     *            test data
     * @param traindata
     *            training data
     */
    void apply(Instances testdata, Instances traindata);
}
