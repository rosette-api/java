/*
* Copyright 2014 Basis Technology Corp.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.basistech.rosette.apimodel;

import java.util.Objects;

public class Dependency {
    private final String dependencyType;
    private final int governorTokenIndex;
    private final int dependentTokenIndex;

    public Dependency(String dependencyType,
                      Integer governorTokenIndex,
                      Integer dependentTokenIndex) {
        this.dependencyType = dependencyType;
        this.governorTokenIndex = governorTokenIndex;
        this.dependentTokenIndex = dependentTokenIndex;
    }

    public Integer getGovernorTokenIndex() {
        return governorTokenIndex;
    }

    public Integer getDependentTokenIndex() {
        return dependentTokenIndex;
    }

    public String getDependencyType() {
        return dependencyType;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Dependency that = (Dependency)o;
            return Objects.equals(this.governorTokenIndex, that.governorTokenIndex)
                    && Objects.equals(this.dependentTokenIndex, that.dependentTokenIndex)
                    && Objects.equals(this.dependencyType, that.dependencyType);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.governorTokenIndex, this.dependentTokenIndex, this.dependencyType);
    }
}
