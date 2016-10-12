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

import io.gravitee.repository.couchbase.management.internal.membership.MembershipCouchbaseRepository;
import io.gravitee.repository.couchbase.management.internal.model.MembershipCouchbase;
import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.management.api.MembershipRepository;
import io.gravitee.repository.management.model.Membership;
import io.gravitee.repository.management.model.MembershipReferenceType;
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
public class CouchbaseMembershipRepository extends CouchbaseAbstractRepository<Membership, MembershipCouchbase>
        implements MembershipRepository {

    @Autowired
    private MembershipCouchbaseRepository membershipCouchbaseRepository;

    public CouchbaseMembershipRepository() {
        super(Membership.class);
    }

    @Override
    public Membership create(Membership membership) throws TechnicalException {
        MembershipCouchbase membershipCb = mapper.map(membership, MembershipCouchbase.class);
        generateIdentifier(membershipCb);
        membershipCouchbaseRepository.save(membershipCb);
        return membership;
    }

    @Override
    public Membership update(Membership membership) throws TechnicalException {
        MembershipCouchbase membershipCb = mapper.map(membership, MembershipCouchbase.class);
        generateIdentifier(membershipCb);
        membershipCouchbaseRepository.save(membershipCb);
        return membership;
    }

    @Override
    public void delete(Membership membership) throws TechnicalException {
        String id = generateIdentifier(membership);
        membershipCouchbaseRepository.delete(id);
    }

    @Override
    public Optional<Membership> findById(String userId, MembershipReferenceType referenceType, String referenceId) throws TechnicalException {
        String id = userId + ':' + referenceType.name() + ':' + referenceId;
        MembershipCouchbase membershipCouchbase = membershipCouchbaseRepository.findOne(id);
        return Optional.ofNullable(map(membershipCouchbase));
    }

    @Override
    public Set<Membership> findByReferenceAndMembershipType(MembershipReferenceType referenceType, String referenceId, String membershipType) throws TechnicalException {
        return null;
    }

    @Override
    public Set<Membership> findByReferencesAndMembershipType(MembershipReferenceType referenceType, List<String> referenceIds, String membershipType) throws TechnicalException {
        return null;
    }

    @Override
    public Set<Membership> findByUserAndReferenceType(String userId, MembershipReferenceType referenceType) throws TechnicalException {
        return null;
    }

    @Override
    public Set<Membership> findByUserAndReferenceTypeAndMembershipType(String userId, MembershipReferenceType referenceType, String membershipType) throws TechnicalException {
        return null;
    }

    private void generateIdentifier(MembershipCouchbase membership) {
        membership.setId(membership.getUserId() + ':' + membership.getReferenceType() + ':' + membership.getReferenceId());
    }

    private String generateIdentifier(Membership membership) {
        return membership.getUserId() + ':' + membership.getReferenceType() + ':' + membership.getReferenceId();
    }
}
