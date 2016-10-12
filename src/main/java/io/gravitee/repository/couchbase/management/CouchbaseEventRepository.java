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

import io.gravitee.common.data.domain.Page;
import io.gravitee.repository.couchbase.management.internal.event.EventCouchbaseRepository;
import io.gravitee.repository.couchbase.management.internal.model.EventCouchbase;
import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.management.api.EventRepository;
import io.gravitee.repository.management.api.search.EventCriteria;
import io.gravitee.repository.management.api.search.Pageable;
import io.gravitee.repository.management.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
@Component
public class CouchbaseEventRepository extends CouchbaseAbstractRepository<Event, EventCouchbase> implements EventRepository {

    @Autowired
    private EventCouchbaseRepository eventCouchbaseRepository;

    public CouchbaseEventRepository() {
        super(Event.class);
    }

    @Override
    public Optional<Event> findById(String id) throws TechnicalException {
        logger.debug("Find event by ID [{}]", id);

        EventCouchbase event = eventCouchbaseRepository.findOne(id);

        logger.debug("Find event by ID [{}] - Done", id);
        return Optional.ofNullable(map(event));
    }

    @Override
    public Event create(Event event) throws TechnicalException {
        logger.debug("Create event [{}]", event.getId());

        EventCouchbase eventCb = mapper.map(event, EventCouchbase.class);
        eventCouchbaseRepository.save(eventCb);

        logger.debug("Create event [{}] - Done", event.getId());

        return event;
    }

    @Override
    public Event update(Event event) throws TechnicalException {
        EventCouchbase eventCb = mapper.map(event, EventCouchbase.class);
        eventCouchbaseRepository.save(eventCb);

        return event;
    }

    @Override
    public void delete(String id) throws TechnicalException {
        try {
            eventCouchbaseRepository.delete(id);
        } catch (Exception e) {
            logger.error("An error occurs when deleting event [{}]", id, e);
            throw new TechnicalException("An error occurs when deleting event");
        }
    }

    @Override
    public Page<Event> search(EventCriteria filter, Pageable pageable) {
        Page<EventCouchbase> pagedEvents = eventCouchbaseRepository.search(filter, pageable);

        return new Page<>(
                pagedEvents.getContent().stream().map(this::map).collect(Collectors.toList()),
                pagedEvents.getPageNumber(), (int) pagedEvents.getTotalElements(),
                pagedEvents.getTotalElements());
    }

    @Override
    public List<Event> search(EventCriteria filter) {
        Page<EventCouchbase> pagedEvents = eventCouchbaseRepository.search(filter, null);

        return pagedEvents.getContent()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
