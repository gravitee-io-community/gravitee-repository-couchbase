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

import io.gravitee.repository.couchbase.management.internal.model.UserCouchbase;
import io.gravitee.repository.couchbase.management.internal.user.UserCouchbaseRepository;
import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.management.api.UserRepository;
import io.gravitee.repository.management.model.User;
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
public class CouchbaseUserRepository extends CouchbaseAbstractRepository<User, UserCouchbase> implements UserRepository {

	@Autowired
	private UserCouchbaseRepository internalUserRepo;

	public CouchbaseUserRepository() {
		super(User.class);
	}

	@Override
	public Optional<User> findByUsername(String username) throws TechnicalException {
		logger.debug("Find user by name user [{}]", username);

		UserCouchbase user = internalUserRepo.findByUsername(username);

		logger.debug("Find user by name user [{}] - Done", username);
		return Optional.ofNullable(map(user));
	}

	@Override
	public Set<User> findByUsernames(List<String> usernames) throws TechnicalException {
		return null;
	}

	@Override
	public Set<User> findAll() throws TechnicalException {
		logger.debug("Find all users");
		
		Iterable<UserCouchbase> users = internalUserRepo.findAll();
		return map(users);
	}

	@Override
	public User create(User user) throws TechnicalException {
		logger.debug("Create user [{}]", user.getUsername());
		
		UserCouchbase userCouchbase = mapper.map(user, UserCouchbase.class);
		internalUserRepo.save(userCouchbase);
		
		logger.debug("Create user [{}] - Done", user.getUsername());
		
		return user;
	}

	@Override
	public User update(User user) throws TechnicalException {
		UserCouchbase userCb = internalUserRepo.findOne(user.getUsername());
		internalUserRepo.save(userCb);

		return user;
	}

}
