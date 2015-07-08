/******************************************************************************
 ** Copyright (c) 2014-2015 Basis Technology Corporation.
 **
 ** Licensed under the Apache License, Version 2.0 (the "License");
 ** you may not use this file except in compliance with the License.
 ** You may obtain a copy of the License at
 **
 **     http://www.apache.org/licenses/LICENSE-2.0
 **
 ** Unless required by applicable law or agreed to in writing, software
 ** distributed under the License is distributed on an "AS IS" BASIS,
 ** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ** See the License for the specific language governing permissions and
 ** limitations under the License.
 ******************************************************************************/

package com.basistech.rosette.apimodel;

import java.util.List;

/**
 * Simple api response data model for entity resolver 
 */
public final class LinkedEntitiesResponse extends Response {

    private final List<LinkedEntity> entities;

    /**
     * constructor for {@code LinkedEntityResponse}
     * @param requestId request id
     * @param entities list of resolved entities
     */
    public LinkedEntitiesResponse(
            String requestId,
            List<LinkedEntity> entities) {
        super(requestId);
        this.entities = entities;
    }

    /**
     * get the list of resolved (against the knowledgebase) entites
     * @return the list of resolved entities
     */
    public List<LinkedEntity> getEntities() {
        return entities;
    }

    @Override
    public int hashCode() {
        return entities != null ? entities.hashCode() : 0;
    }

    /**
     * if the param is a {@code LinkedEntityResponse}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LinkedEntitiesResponse)) {
            return false;
        }

        LinkedEntitiesResponse that = (LinkedEntitiesResponse) o;
        return super.equals(o)
                && entities != null ? entities.equals(that.getEntities()) : entities == that.getEntities();
    }
}