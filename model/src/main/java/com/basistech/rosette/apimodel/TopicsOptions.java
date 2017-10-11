/******************************************************************************
 ** This data and information is proprietary to, and a valuable trade secret
 ** of, Basis Technology Corp.  It is given in confidence by Basis Technology
 ** and may only be used as permitted under the license agreement under which
 ** it has been distributed, and in no other way.
 **
 ** Copyright (c) 2017 Basis Technology Corporation All rights reserved.
 **
 ** The technical data and information provided herein are provided with
 ** `limited rights', and the computer software provided herein is provided
 ** with `restricted rights' as those terms are defined in DAR and ASPR
 ** 7-104.9(a).
 ******************************************************************************/
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
