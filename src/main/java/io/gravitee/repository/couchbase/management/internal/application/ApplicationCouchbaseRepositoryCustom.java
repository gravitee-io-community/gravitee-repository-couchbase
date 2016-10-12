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
package io.gravitee.repository.couchbase.management.internal.application;

import io.gravitee.repository.couchbase.management.internal.model.ApplicationCouchbase;
import io.gravitee.repository.management.model.MembershipType;

import java.util.Collection;

public interface ApplicationCouchbaseRepositoryCustom {
    
	/**
	 * Find applications by user
	 * @param username
	 * @return
	 */
	Collection<ApplicationCouchbase> findByUser(String username, MembershipType membershipType);
    
    /**
     * Count api by username (owner)
     * @param username
     * @return
     */
	int countByUser(String username, MembershipType membershipType);

	Iterable<ApplicationCouchbase> findAll();
}
