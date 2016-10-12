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

import io.gravitee.repository.couchbase.management.internal.application.ApplicationCouchbaseRepository;
import io.gravitee.repository.couchbase.management.internal.model.ApplicationCouchbase;
import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.management.api.ApplicationRepository;
import io.gravitee.repository.management.model.Application;
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
public class CouchbaseApplicationRepository extends CouchbaseAbstractRepository<Application, ApplicationCouchbase>
		implements ApplicationRepository {

	@Autowired
	private ApplicationCouchbaseRepository internalApplicationRepo;
	
	public CouchbaseApplicationRepository() {
		super(Application.class);
	}

	@Override
	public Set<Application> findAll() throws TechnicalException {
		return map(internalApplicationRepo.findAll());
	}

	@Override
	public Set<Application> findByIds(List<String> ids) throws TechnicalException {
		Iterable<ApplicationCouchbase> applications = internalApplicationRepo.findAll(ids);
		return map(applications);
	}

	@Override
	public Application create(Application application) throws TechnicalException {
		ApplicationCouchbase applicationCb = mapper.map(application, ApplicationCouchbase.class);
		ApplicationCouchbase applicationCbCreated = internalApplicationRepo.save(applicationCb);
		return map(applicationCbCreated);
	}

	@Override
	public Application update(Application application) throws TechnicalException {
		ApplicationCouchbase applicationCouchbase = internalApplicationRepo.findOne(application.getId());

		// Update, but don't change invariant other creation information
		applicationCouchbase.setName(application.getName());
		applicationCouchbase.setDescription(application.getDescription());
		applicationCouchbase.setUpdatedAt(application.getUpdatedAt());
		applicationCouchbase.setType(application.getType());

		ApplicationCouchbase applicationMongoUpdated = internalApplicationRepo.save(applicationCouchbase);
		return map(applicationMongoUpdated);
	}

	@Override
	public Optional<Application> findById(String applicationId) throws TechnicalException {
		ApplicationCouchbase application = internalApplicationRepo.findOne(applicationId);
		return Optional.ofNullable(map(application));
	}

	@Override
	public void delete(String applicationId) throws TechnicalException {
		internalApplicationRepo.delete(applicationId);
	}
}
