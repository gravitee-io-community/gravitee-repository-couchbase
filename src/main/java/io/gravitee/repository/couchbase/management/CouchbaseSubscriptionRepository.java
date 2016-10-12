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

import io.gravitee.repository.couchbase.management.internal.model.SubscriptionCouchbase;
import io.gravitee.repository.couchbase.management.internal.subscription.SubscriptionCouchbaseRepository;
import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.management.api.SubscriptionRepository;
import io.gravitee.repository.management.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
@Component
public class CouchbaseSubscriptionRepository extends CouchbaseAbstractRepository<Subscription, SubscriptionCouchbase>
        implements SubscriptionRepository {

    @Autowired
    private SubscriptionCouchbaseRepository subscriptionCouchbaseRepository;

    public CouchbaseSubscriptionRepository() {
        super(Subscription.class);
    }

    @Override
    public Set<Subscription> findByPlan(String planId) throws TechnicalException {
        return null;
    }

    @Override
    public Set<Subscription> findByApplication(String application) throws TechnicalException {
        return null;
    }

    @Override
    public Optional<Subscription> findById(String subscription) throws TechnicalException {
        SubscriptionCouchbase subscriptionCb = subscriptionCouchbaseRepository.findOne(subscription);
        return Optional.ofNullable(map(subscriptionCb));
    }

    @Override
    public Subscription create(Subscription subscription) throws TechnicalException {
        SubscriptionCouchbase subscriptionCb = mapper.map(subscription, SubscriptionCouchbase.class);
        subscriptionCouchbaseRepository.save(subscriptionCb);
        return subscription;
    }

    @Override
    public Subscription update(Subscription subscription) throws TechnicalException {
        SubscriptionCouchbase subscriptionCb = mapper.map(subscription, SubscriptionCouchbase.class);
        subscriptionCouchbaseRepository.save(subscriptionCb);
        return subscription;
    }

    @Override
    public void delete(String subscription) throws TechnicalException {
        subscriptionCouchbaseRepository.delete(subscription);
    }
}
