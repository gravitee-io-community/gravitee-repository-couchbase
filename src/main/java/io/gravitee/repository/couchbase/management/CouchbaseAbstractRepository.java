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

import io.gravitee.repository.couchbase.management.mapper.GraviteeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
abstract class CouchbaseAbstractRepository<T, U> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Class<T> targetClass;

    @Autowired
    protected GraviteeMapper mapper;

    protected CouchbaseAbstractRepository(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    protected Set<T> map(Collection<U> inputs){
        if (inputs == null){
            return Collections.emptySet();
        }

        return inputs.stream().map(this::map).collect(Collectors.toSet());
    }

    protected Set<T> map(Iterable<U> iter) {
        return map(iter.iterator());
    }

    protected Set<T> map(Iterator<U> iter) {
        Set<T> result = new HashSet<>();

        while(iter.hasNext()) {
            result.add(map(iter.next()));
        }

        return result;
    }

    protected T map(U input) {
        return (input == null) ? null : mapper.map(input, targetClass);
    }
}
