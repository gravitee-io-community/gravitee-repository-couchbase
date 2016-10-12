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

import io.gravitee.repository.couchbase.management.internal.model.PageCouchbase;
import io.gravitee.repository.couchbase.management.internal.page.PageCouchbaseRepository;
import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.management.api.PageRepository;
import io.gravitee.repository.management.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
@Component
public class CouchbasePageRepository extends CouchbaseAbstractRepository<Page, PageCouchbase> implements PageRepository {

	@Autowired
	private PageCouchbaseRepository internalPageRepo;

	public CouchbasePageRepository() {
		super(Page.class);
	}

	@Override
	public Collection<Page> findByApi(String apiId) throws TechnicalException {
		logger.debug("Find pages by api {}", apiId);

		return internalPageRepo.findByApi(apiId)
				.stream()
				.map(this::map)
				.collect(Collectors.toSet());
	}

	@Override
	public Optional<Page> findById(String pageId) throws TechnicalException {
		logger.debug("Find page by ID [{}]", pageId);

		PageCouchbase page = internalPageRepo.findOne(pageId);
		return Optional.ofNullable(map(page));
	}

	@Override
	public Page create(Page page) throws TechnicalException {
		logger.debug("Create page [{}]", page.getName());
		
		PageCouchbase pageCb = mapper.map(page, PageCouchbase.class);
		internalPageRepo.save(pageCb);

		logger.debug("Create page [{}] - Done", page.getName());
		
		return page;
	}

	@Override
	public Page update(Page page) throws TechnicalException {
		logger.debug("Update page [{}]", page.getName());

		PageCouchbase pageCb = mapper.map(page, PageCouchbase.class);
		internalPageRepo.save(pageCb);

		logger.debug("Update page [{}] - Done", page.getName());

		return page;
	}

	@Override
	public void delete(String pageId) throws TechnicalException {
		try{
			internalPageRepo.delete(pageId);
		}catch(Exception e){
			logger.error("An error occured when deleting page [{}]", pageId, e);
			throw new TechnicalException("An error occured when deleting page");
		}
	}

	@Override
	public Integer findMaxPageOrderByApi(String apiId) throws TechnicalException {
		try{
			List<PageCouchbase> pages = internalPageRepo.findByApiOrderByOrderDesc(apiId);
			return CollectionUtils.isEmpty(pages) ? 0 : pages.get(0).getOrder();
		}catch(Exception e){
			logger.error("An error occured when searching max order page for api name [{}]", apiId, e);
			throw new TechnicalException("An error occured when searching max order page for api name");
		}
	}
}
