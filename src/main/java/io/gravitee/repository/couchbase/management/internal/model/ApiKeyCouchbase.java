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
package io.gravitee.repository.couchbase.management.internal.model;

import java.util.Date;
import java.util.Objects;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Id;

/**
 * @author Ludovic DUSSART
 */
@Document
public class ApiKeyCouchbase {
	/**
	 * The API for which the key is defined.
	 */
	private String api;


	/**
	 * The application for which the key is defined.
	 */
	private String application;
	
	/**
	 * Api Key
	 */
	@Id
	private String key;

	/**
	 * Is the key revoked ?
	 */
	private boolean revoked;
	
	/**
	 * Token expiration date
	 */
	private Date expiration;

	private Date  createdAt;

	private Date  revokeAt;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	
	public boolean isRevoked() {
		return revoked;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

	
	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiKeyCouchbase key = (ApiKeyCouchbase) o;
        return Objects.equals(this.key, key.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ApiKey{");
        sb.append("name='").append(key).append('\'');
        sb.append(", expiration=").append(expiration );
        sb.append('}');
        return sb.toString();
    }

	public Date  getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date  createdAt) {
		this.createdAt = createdAt;
	}

	public Date  getRevokeAt() {
		return revokeAt;
	}

	public void setRevokeAt(Date  revokeAt) {
		this.revokeAt = revokeAt;
	}
}
