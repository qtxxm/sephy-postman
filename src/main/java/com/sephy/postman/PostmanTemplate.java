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

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.methods.RequestBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Sephy
 * @since: 2015-08-07
 */
public class PostmanTemplate {

	private ConcurrentMap<Class, ResponseHandler> handlerMap = new ConcurrentHashMap<>();

	private Executor executor = Executor.newInstance();

	public void setExecutor(Executor executor) {
		if (executor != null) {
			this.executor = executor;
		}
	}

	public void setHttpClient(HttpClient httpClient) {
		if (httpClient != null) {
			executor = Executor.newInstance(httpClient);
		}
	}

	public <T> T get(String url, Map<String, Object> params, Map<String, Object> headers,
			ResponseHandler<T> responseHandler) {
		try {
            RequestBuilder builder = RequestBuilder.get(url);
			PostmanUtils.setParameter(builder, params);
            Request get = Request.Get(builder.build().getURI());
            PostmanUtils.setHeader(get, headers);
            Response response = executor.execute(get);
			return response.handleResponse(responseHandler);
		} catch (Exception ex) {
			throw new PostmanException(ex);
		}
	}

	public <T> T post(String url, Map<String, Object> params, Map<String, Object> headers,
			ResponseHandler<T> responseHandler) {
		try {
			Request request = Request.Get(url);
			PostmanUtils.setParameter(request, params);
			PostmanUtils.setHeader(request, headers);
			Response response = executor.execute(request);
			return response.handleResponse(responseHandler);
		} catch (Exception ex) {
			throw new PostmanException(ex);
		}
	}

	public <T> T getJSON(String url, Map<String, Object> params, Map<String, Object> headers, Class<? extends T> klass) {
		ResponseHandler<? extends T> responseHandler = getResponseHandler(klass);
		return get(url, params, headers, responseHandler);
	}

	public <T> T postJSON(String url, Map<String, Object> params, Map<String, Object> headers, Class<? extends T> klass) {
		ResponseHandler<? extends T> responseHandler = getResponseHandler(klass);
		return post(url, params, headers, responseHandler);
	}

	private <T> ResponseHandler<? extends T> getResponseHandler(Class<? extends T> klass) {
		ResponseHandler responseHandler = handlerMap.get(klass);
		if (responseHandler == null) {
			responseHandler = new JsonResponseHandler(klass);
			handlerMap.put(klass, responseHandler);
		}
		return responseHandler;
	}
}
