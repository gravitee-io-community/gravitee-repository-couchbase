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

import io.gravitee.repository.couchbase.management.internal.key.ApiKeyCouchbaseRepository;
import io.gravitee.repository.couchbase.management.internal.model.ApiKeyCouchbase;
import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.management.api.ApiKeyRepository;
import io.gravitee.repository.management.model.ApiKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
@Component
public class CouchbaseApiKeyRepository extends CouchbaseAbstractRepository<ApiKey, ApiKeyCouchbase> implements ApiKeyRepository {

	@Autowired
	private ApiKeyCouchbaseRepository apiKeyCouchbaseRepository;

	public CouchbaseApiKeyRepository() {
		super(ApiKey.class);
	}

	@Override
	public Optional<ApiKey> findById(String apiKey) throws TechnicalException {
		ApiKeyCouchbase apiKeyCb = apiKeyCouchbaseRepository.findOne(apiKey);
		return Optional.ofNullable(map(apiKeyCb));
	}

	@Override
	public ApiKey create(ApiKey apiKey) throws TechnicalException {
		ApiKeyCouchbase apiKeysCb = mapper.map(apiKey, ApiKeyCouchbase.class);
		apiKeyCouchbaseRepository.save(apiKeysCb);

		return apiKey;
	}

	@Override
	public ApiKey update(ApiKey apiKey) throws TechnicalException {
		ApiKeyCouchbase apiKeysCb = mapper.map(apiKey, ApiKeyCouchbase.class);
		apiKeyCouchbaseRepository.save(apiKeysCb);

		return apiKey;
	}

	@Override
	public Set<ApiKey> findBySubscription(String subscription) throws TechnicalException {
		return apiKeyCouchbaseRepository.findBySubscription(subscription)
				.stream()
				.map(this::map)
				.collect(Collectors.toSet());
	}

	@Override
	public Set<ApiKey> findByPlan(String plan) throws TechnicalException {
		return apiKeyCouchbaseRepository.findByPlan(plan)
				.stream()
				.map(this::map)
				.collect(Collectors.toSet());
	}
}
