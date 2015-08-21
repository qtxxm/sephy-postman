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

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Sephy
 * @since: 2015-08-21
 */
public abstract class PostmanUtils {

	public static void setParameter(RequestBuilder builder, Map<String, Object> params) {
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				builder.addParameter(entryToNameAndValuePair(entry));
			}
		}
	}

	public static void setParameter(Request request, Map<String, Object> params) {
		if (params != null && params.size() > 0) {
			List<NameValuePair> list = new ArrayList<>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				list.add(entryToNameAndValuePair(entry));
			}
			request.bodyForm(list);
		}
	}

	public static void setParameter(RequestBuilder builder, List<NameValuePair> params) {
		for (NameValuePair param : params) {
			builder.addParameter(param);
		}
	}

	public static void setHeader(RequestBuilder builder, Map<String, Object> header) {
		if (header != null && header.size() > 0) {
			for (Map.Entry<String, Object> entry : header.entrySet()) {
				builder.addHeader(entryToHeader(entry));
			}
		}
	}

	public static void setHeader(Request request, Map<String, Object> header) {
		if (header != null && header.size() > 0) {
			for (Map.Entry<String, Object> entry : header.entrySet()) {
				request.addHeader(entryToHeader(entry));
			}
		}
	}

	public static NameValuePair entryToNameAndValuePair(Map.Entry<String, Object> entry) {
		String name = entry.getKey();
		Object object = entry.getValue();
		String value = object == null ? StringUtils.EMPTY : StringUtils.trimToEmpty(object.toString());
		return new BasicNameValuePair(name, value);
	}

	public static Header entryToHeader(Map.Entry<String, Object> entry) {
		String name = entry.getKey();
		Object object = entry.getValue();
		String value = object == null ? StringUtils.EMPTY : StringUtils.trimToEmpty(object.toString());
		return new BasicHeader(name, value);
	}
}
