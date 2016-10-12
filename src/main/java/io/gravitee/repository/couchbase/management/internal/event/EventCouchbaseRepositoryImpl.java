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
package io.gravitee.repository.couchbase.management.internal.event;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.Statement;
import com.couchbase.client.java.query.dsl.Expression;
import com.couchbase.client.java.query.dsl.Sort;
import com.couchbase.client.java.query.dsl.path.LimitPath;
import com.couchbase.client.java.query.dsl.path.WherePath;
import io.gravitee.common.data.domain.Page;
import io.gravitee.repository.couchbase.management.internal.model.EventCouchbase;
import io.gravitee.repository.management.api.search.EventCriteria;
import io.gravitee.repository.management.api.search.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.repository.query.support.N1qlUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.couchbase.client.java.query.dsl.Expression.x;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class EventCouchbaseRepositoryImpl implements EventCouchbaseRepositoryCustom {

	private final static Logger logger = LoggerFactory.getLogger(EventCouchbaseRepositoryImpl.class);

	private final static String EVENT_ID_PATTERN = "event::%d";
	private final static String EVENT_COUNTER_ID = "event_counter";
	private final static String CLASS_FIELD_VALUE = EventCouchbase.class.getName();
	private final static String CLASS_FIELD = "_class";

	private final static String PROPERTIES_PREFIX_FIELD= "properties.";

	@Autowired
	private CouchbaseTemplate cbTemplate;

	@Override
	public Page<EventCouchbase> search(EventCriteria filter, Pageable pageable) {
		JsonObject parameters = JsonObject.create().put("class", CLASS_FIELD_VALUE);
		Statement finalStmt;

		WherePath baseStatement = N1qlUtils.createSelectFromForEntity(cbTemplate.getCouchbaseBucket().name());
		Expression baseExpression = x(CLASS_FIELD).eq(x("$class"));

		if (filter.getTypes() != null && ! filter.getTypes().isEmpty()) {
			if (filter.getTypes().size() == 1) {
				baseExpression = baseExpression.and(x("type").eq('\'' + filter.getTypes().iterator().next().name() + '\''));
			} else {
				JsonArray values = JsonArray.create();
				filter.getTypes().forEach(type -> values.add(type.name()));
				baseExpression = baseExpression.and(Expression.x("type").in(values));
			}
		}

		if (filter.getProperties() != null) {
			for(Map.Entry<String, Object> property : filter.getProperties().entrySet()) {
				if (property.getValue() instanceof Collection) {
					Collection col = (Collection) (property.getValue());
					JsonArray values = JsonArray.create();
					col.forEach(values::add);
					baseExpression = baseExpression.and(Expression.x(PROPERTIES_PREFIX_FIELD + property.getKey()).in(values));
				} else {
					baseExpression = baseExpression.and(x(PROPERTIES_PREFIX_FIELD + property.getKey()).eq('\'' + (String) property.getValue() + '\''));
				}
			}
		}

		LimitPath queryStmt = baseStatement
				.where(baseExpression)
				.orderBy(Sort.desc(x("updatedAt")));
		finalStmt = queryStmt;

		if (pageable != null) {
			finalStmt = queryStmt.limit(pageable.pageSize()).offset(pageable.pageNumber() * pageable.pageSize());
		}

		N1qlQuery query = N1qlQuery.parameterized(finalStmt, parameters);
		List<EventCouchbase> events = cbTemplate.findByN1QL(query, EventCouchbase.class);

		return new Page(
				events,
				(pageable != null) ? pageable.pageNumber() : 0,
				(pageable != null) ? pageable.pageSize() : 0,
				events.size());
	}
}
