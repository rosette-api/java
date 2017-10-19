/*
* Copyright 2017 Basis Technology Corp.
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

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.util.Objects;

public final class TopicsOptions extends Options {
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double conceptSalienceThreshold;
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double keyphraseSalienceThreshold;

    public TopicsOptions(Double conceptSalienceThreshold, Double keyphraseSalienceThreshold) {
        this.conceptSalienceThreshold = conceptSalienceThreshold;
        this.keyphraseSalienceThreshold = keyphraseSalienceThreshold;
    }

    public Double getconceptSalienceThreshold() {
        return conceptSalienceThreshold;
    }

    public Double getkeyphraseSalienceThreshold() {
        return keyphraseSalienceThreshold;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final TopicsOptions that = (TopicsOptions) obj;
        return Objects.equals(conceptSalienceThreshold, that.conceptSalienceThreshold)
                && Objects.equals(keyphraseSalienceThreshold, that.keyphraseSalienceThreshold);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conceptSalienceThreshold, keyphraseSalienceThreshold);
    }

    public static class Builder {
        private Double conceptSalienceThreshold;
        private Double keyphraseSalienceThreshold;

        public Builder() {
            //
        }

        public Builder conceptSalienceThreshold(Double conceptSalienceThreshold) {
            this.conceptSalienceThreshold = conceptSalienceThreshold;
            return this;
        }


        public Builder keyphraseSalienceThreshold(Double keyphraseSalienceThreshold) {
            this.keyphraseSalienceThreshold = keyphraseSalienceThreshold;
            return this;
        }

        public TopicsOptions build() {
            return new TopicsOptions(conceptSalienceThreshold, keyphraseSalienceThreshold);
        }
    }

}
