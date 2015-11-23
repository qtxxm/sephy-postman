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

package net.sephy.postman.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

/**
 * Http 请求辅助工具类
 * @author Sephy
 * @since: 2015-08-21
 */
public abstract class PostmanUtils {

    /**
     * 设置请求参数
     * @param builder
     * @param params
     */
	public static void setParameter(RequestBuilder builder,
			Map<String, Object> params) {
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				builder.addParameter(entryToNameAndValuePair(entry));
			}
		}
	}

    /**
     * 设置请求参数
     * @param builder
     * @param params
     */
	public static void setParameter(RequestBuilder builder,
			List<NameValuePair> params) {
		for (NameValuePair param : params) {
			builder.addParameter(param);
		}
	}

    /**
     * 设置请求头参数
     * @param builder
     * @param header
     */
	public static void setHeader(RequestBuilder builder,
			Map<String, Object> header) {
		if (header != null && header.size() > 0) {
			for (Map.Entry<String, Object> entry : header.entrySet()) {
				builder.addHeader(entryToHeader(entry));
			}
		}
	}

    /**
     * 设置请求头参数
     * @param builder
     * @param headers
     */
	public static void setHeader(RequestBuilder builder, List<Header> headers) {
		if (headers != null) {
			for (Header header : headers) {
				builder.addHeader(header);
			}
		}
	}

    /**
     * 将 Map 形式的键值参数对转换为 List<NameValuePair> 的形式
     * @param map
     * @return
     */
	public static List<NameValuePair> mapToNameValuePairList(
			Map<String, Object> map) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			Object value = entry.getValue();
			if (value != null) {
				list.add(new BasicNameValuePair(entry.getKey(), value
						.toString()));
			}
		}
		return list;
	}

    /**
     * 将 Map 形式的键值参数对转换为 List<Header> 的形式
     * @param map
     * @return
     */
	public static List<Header> mapToHeaderList(Map<String, Object> map) {
		List<Header> list = new ArrayList<Header>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			Object value = entry.getValue();
			if (value != null) {
				list.add(new BasicHeader(entry.getKey(), value.toString()));
			}
		}
		return list;
	}

	public static NameValuePair entryToNameAndValuePair(
			Map.Entry<String, Object> entry) {
		Object value = entry.getValue();
		if (value == null) {
			return null;
		}
		return new BasicNameValuePair(entry.getKey(), value.toString());
	}

	public static Header entryToHeader(Map.Entry<String, Object> entry) {
		Object value = entry.getValue();
		if (value == null) {
			return null;
		}
		return new BasicHeader(entry.getKey(), value.toString());
	}
}
