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

package net.sephy.postman;

import java.io.File;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.sephy.postman.util.ObjectMapperUtils;
import net.sephy.postman.util.PostmanUtils;

/**
 * @author Sephy
 * @since: 2015-08-07
 */
public class Postman {

	private ObjectMapper objectMapper;

	// json 处理器缓存
	private ConcurrentMap<Class, ResponseHandler> jsonHandlerCache = new ConcurrentHashMap<Class, ResponseHandler>();

	private HttpClient httpClient;

	public Postman() {
		objectMapper = ObjectMapperUtils.getDefaultObjectMapper();
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public <T> T get(String url, List<NameValuePair> params,
			List<Header> headers, ResponseHandler<T> responseHandler) {
		RequestBuilder builder = RequestBuilder.get(url);
		PostmanUtils.setParameter(builder, params);
		PostmanUtils.setHeader(builder, headers);
		return execute(builder.build(), responseHandler);
	}

	public <T> T post(String url, List<NameValuePair> params,
			List<Header> headers, ResponseHandler<T> responseHandler) {
		RequestBuilder builder = RequestBuilder.post(url);
		PostmanUtils.setParameter(builder, params);
		PostmanUtils.setHeader(builder, headers);
		return execute(builder.build(), responseHandler);
	}

	public <T> T getJSON(String url, List<NameValuePair> params,
			List<Header> headers, Class<? extends T> klass) {
		ResponseHandler<? extends T> responseHandler = getResponseHandler(klass);
		return get(url, params, headers, responseHandler);
	}

	public <T> T postJSON(String url, List<NameValuePair> params,
			List<Header> headers, Class<? extends T> klass) {
		ResponseHandler<? extends T> responseHandler = getResponseHandler(klass);
		return post(url, params, headers, responseHandler);
	}

	public void download(String url, List<NameValuePair> params,
			List<Header> headers, File fileToSave) {
		get(url, params, headers, new FileStreamResponseHandler(fileToSave));
	}

	private <T> T execute(HttpUriRequest request,
			ResponseHandler<T> responseHandler) {
		try {
			return httpClient.execute(request, responseHandler);
		}
		catch (Exception ex) {
			throw new PostmanException(ex);
		}
	}

	private <T> ResponseHandler<? extends T> getResponseHandler(
			Class<? extends T> klass) {
		ResponseHandler responseHandler = jsonHandlerCache.get(klass);
		if (responseHandler == null) {
			responseHandler = new JSONResponseHandler(klass, this.objectMapper);
			jsonHandlerCache.put(klass, responseHandler);
		}
		return responseHandler;
	}
}
