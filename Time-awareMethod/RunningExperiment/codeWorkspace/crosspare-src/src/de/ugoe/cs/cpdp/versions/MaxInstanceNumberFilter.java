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

/**
 * Applies to large data sets. All data sets that have more than the required maximum number of
 * instances are removed.
 * 
 * @author Steffen Herbold
 */
public class MaxInstanceNumberFilter extends AbstractVersionFilter {

    /**
     * maximum number of instances required
     */
    private int maxInstances = 0;

    /**
     * @see de.ugoe.cs.cpdp.versions.IVersionFilter#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @Override
    public boolean apply(SoftwareVersion version) {
        return version.getInstances().numInstances() > maxInstances;
    }

    /**
     * Sets the minimal number of instances.
     * 
     * @param parameters
     *            number of instances
     */
    @Override
    public void setParameter(String parameters) {
        maxInstances = Integer.parseInt(parameters);
    }

}
