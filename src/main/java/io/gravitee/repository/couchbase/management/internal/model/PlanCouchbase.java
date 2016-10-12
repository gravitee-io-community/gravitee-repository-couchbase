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

import com.couchbase.client.java.repository.annotation.Field;
import io.gravitee.repository.management.model.Plan;
import com.couchbase.client.java.repository.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.List;
import java.util.Set;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
@Document
public class PlanCouchbase extends Auditable {

    @Id
    private String id;

    @Field
    private String name;

    @Field
    private String description;

    /**
     * The way to validate subscriptions
     */
    @Field
    private Plan.PlanValidationType validation;

    @Field
    private Plan.PlanType type;

    /**
     * The position of the plan against the other Plans.
     */
    @Field
    private int order;

    /**
     * List of API used by this plan.
     */
    @Field
    private Set<String> apis;

    /**
     * The JSON payload of all policies to apply for this plan
     */
    @Field
    private String policies;

    @Field
    private List<String> characteristics;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Plan.PlanValidationType getValidation() {
        return validation;
    }

    public void setValidation(Plan.PlanValidationType validation) {
        this.validation = validation;
    }

    public Plan.PlanType getType() {
        return type;
    }

    public void setType(Plan.PlanType type) {
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Set<String> getApis() {
        return apis;
    }

    public void setApis(Set<String> apis) {
        this.apis = apis;
    }

    public String getPolicies() {
        return policies;
    }

    public void setPolicies(String policies) {
        this.policies = policies;
    }

    public List<String> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<String> characteristics) {
        this.characteristics = characteristics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanCouchbase that = (PlanCouchbase) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
