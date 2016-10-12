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
package io.gravitee.repository.couchbase.management;

import io.gravitee.repository.couchbase.management.internal.model.PlanCouchbase;
import io.gravitee.repository.couchbase.management.internal.plan.PlanCouchbaseRepository;
import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.management.api.PlanRepository;
import io.gravitee.repository.management.model.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
@Component
public class CouchbasePlanRepository extends CouchbaseAbstractRepository<Plan, PlanCouchbase> implements PlanRepository {

    @Autowired
    private PlanCouchbaseRepository planCouchbaseRepository;

    public CouchbasePlanRepository() {
        super(Plan.class);
    }

    @Override
    public Set<Plan> findByApi(String apiId) throws TechnicalException {
        return null;
    }

    @Override
    public Optional<Plan> findById(String plan) throws TechnicalException {
        PlanCouchbase planCb = planCouchbaseRepository.findOne(plan);
        return Optional.ofNullable(map(planCb));
    }

    @Override
    public Plan create(Plan plan) throws TechnicalException {
        PlanCouchbase planCb = mapper.map(plan, PlanCouchbase.class);
        planCouchbaseRepository.save(planCb);
        return plan;
    }

    @Override
    public Plan update(Plan plan) throws TechnicalException {
        PlanCouchbase planCb = mapper.map(plan, PlanCouchbase.class);
        planCouchbaseRepository.save(planCb);
        return plan;
    }

    @Override
    public void delete(String plan) throws TechnicalException {
        planCouchbaseRepository.delete(plan);
    }
}
