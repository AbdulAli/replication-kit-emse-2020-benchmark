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

package de.ugoe.cs.cpdp.versions;

import weka.core.Instances;

/**
 * Applies to small data sets. All data sets that do not have the required minimal number of
 * instances in each class (i.e., positive, negative) are removed.
 * 
 * @author Steffen Herbold
 */
public class MinClassNumberFilter extends AbstractVersionFilter {

    /**
     * minimal number of instances required
     */
    private int minInstances = 0;

    /*
     * @see de.ugoe.cs.cpdp.versions.IVersionFilter#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @Override
    public boolean apply(SoftwareVersion version) {
        Instances instances = version.getInstances();
        int[] counts = instances.attributeStats(instances.classIndex()).nominalCounts;
        boolean toSmall = false;
        for (int count : counts) {
            toSmall |= count < minInstances;
        }
        return toSmall;
    }

    /**
     * Sets the minimal number of instances for each class.
     * 
     * @param parameters
     *            number of instances
     */
    @Override
    public void setParameter(String parameters) {
        minInstances = Integer.parseInt(parameters);
    }

}
