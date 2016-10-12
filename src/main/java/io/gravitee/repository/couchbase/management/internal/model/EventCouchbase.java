/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.repository.couchbase.management.internal.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import io.gravitee.repository.management.model.EventType;


/**
 * @author Ludovic DUSSART
 */
@Document
public class EventCouchbase extends Auditable{

    /**
     * The event ID
     */
	@Id
    private String id;

    /**
     * The event Type
     */
    private EventType type;

    /**
     * The event payload
     */
    private String payload;

    /**
     * The event parent
     */
    private String parentId;

    /**
     * The event properties
     */
    private Map<String, String> properties;

    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

   
    public enum EventProperties {
        API_ID("api_id"),
        ORIGIN("origin"),
        USERNAME("username");

        private String value;

        EventProperties(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
