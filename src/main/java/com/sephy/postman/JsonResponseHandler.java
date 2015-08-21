/*
 * Copyright 2015 MING XIA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sephy.postman;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractResponseHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Sephy
 * @since: 2015-08-07
 */
public class JsonResponseHandler<T> extends AbstractResponseHandler<T> {

	private Class<T> klass;

	private static ObjectMapper objectMapper = new ObjectMapper();

	public JsonResponseHandler() {
	}

	public JsonResponseHandler(Class<T> klass) {
		this.klass = klass;
	}

	public void setKlass(Class<T> klass) {
		this.klass = klass;
	}

	public static void setObjectMapper(ObjectMapper objectMapper) {
		JsonResponseHandler.objectMapper = objectMapper;
	}

	@Override
	public T handleEntity(HttpEntity entity) throws IOException {
		return objectMapper.readValue(entity.getContent(), klass);
	}
}
