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

import io.gravitee.repository.couchbase.management.internal.api.ApiCouchbaseRepository;
import io.gravitee.repository.couchbase.management.internal.model.ApiCouchbase;
import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.management.api.ApiRepository;
import io.gravitee.repository.management.model.Api;
import io.gravitee.repository.management.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
@Component
public class CouchbaseApiRepository extends CouchbaseAbstractRepository<Api, ApiCouchbase> implements ApiRepository {

	@Autowired
	private ApiCouchbaseRepository internalApiRepo;

	public CouchbaseApiRepository() {
		super(Api.class);
	}

	@Override
	public Optional<Api> findById(String apiId) throws TechnicalException {
		ApiCouchbase api = internalApiRepo.findOne(apiId);
		return Optional.ofNullable(map(api));
	}

	@Override
	public Set<Api> findAll() throws TechnicalException {
		return map(internalApiRepo.findAll());
	}

	@Override
	public Set<Api> findByVisibility(Visibility visibility) throws TechnicalException {
		return null;
	}

	@Override
	public Set<Api> findByIds(List<String> ids) throws TechnicalException {
		Iterable<ApiCouchbase> apis = internalApiRepo.findAll(ids);
		return map(apis);
	}

	@Override
	public Api create(Api api) throws TechnicalException {
		ApiCouchbase apiCb = mapper.map(api, ApiCouchbase.class);
		internalApiRepo.save(apiCb);

		return api;
	}

	@Override
	public Api update(Api api) throws TechnicalException {
		ApiCouchbase apiCb = mapper.map(api, ApiCouchbase.class);
		internalApiRepo.save(apiCb);

		return api;
	}

	@Override
	public void delete(String apiId) throws TechnicalException {
		internalApiRepo.delete(apiId);
	}
}
