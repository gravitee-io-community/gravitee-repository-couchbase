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

import io.gravitee.repository.couchbase.management.internal.model.ViewCouchbase;
import io.gravitee.repository.couchbase.management.internal.view.ViewCouchbaseRepository;
import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.management.api.ViewRepository;
import io.gravitee.repository.management.model.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
@Component
public class CouchbaseViewRepository extends CouchbaseAbstractRepository<View, ViewCouchbase> implements ViewRepository {

    @Autowired
    private ViewCouchbaseRepository internalViewRepository;

    public CouchbaseViewRepository() {
        super(View.class);
    }

    @Override
    public Set<View> findAll() throws TechnicalException {
        logger.debug("Find all views");

        Iterable<ViewCouchbase> views = internalViewRepository.getAll();
        return map(views);
    }

    @Override
    public Optional<View> findById(String id) throws TechnicalException {
        logger.debug("Find view by ID [{}]", id);

        ViewCouchbase view = internalViewRepository.findOne(id);

        logger.debug("Find view by ID [{}] - Done", id);
        return Optional.ofNullable(map(view));
    }

    @Override
    public View create(View view) throws TechnicalException {
        logger.debug("Create view [{}]", view.getName());

        ViewCouchbase viewCouchbase = mapper.map(view, ViewCouchbase.class);
        viewCouchbase.setId(viewCouchbase.getId());
        internalViewRepository.save(viewCouchbase);

        logger.debug("Create view [{}] - Done", view.getName());

        return view;
    }

    @Override
    public View update(View view) throws TechnicalException {
        ViewCouchbase viewCouchbase = mapper.map(view, ViewCouchbase.class);
        internalViewRepository.save(viewCouchbase);

        return view;
    }

    @Override
    public void delete(String id) throws TechnicalException {
        try {
            internalViewRepository.delete(id);
        } catch(Exception ex) {
            logger.error("An error occurs when deleting view [{}]", id, ex);
            throw new TechnicalException("An error occurs when deleting view");
        }
    }
}
