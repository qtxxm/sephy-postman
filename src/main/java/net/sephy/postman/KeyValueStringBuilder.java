package net.sephy.postman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

/**
 * @author Sephy
 * @since: 2015-10-28
 */
public class KeyValueStringBuilder {

	private Map<String, String> paramMap;

	public KeyValueStringBuilder() {
		paramMap = new HashMap<String, String>();
	}

	public KeyValueStringBuilder addNumber(String key, Number val) {
		return addParam(key, val.toString());
	}

	public KeyValueStringBuilder addString(String key, String val) {
		return addParam(key, val);
	}

	public KeyValueStringBuilder addObject(String key, Object object) {
		return addParam(key, object.toString());
	}

	public KeyValueStringBuilder addBoolean(String key, boolean val) {
		return addParam(key, String.valueOf(val));
	}

	public KeyValueStringBuilder addInt(String key, int val) {
		return addParam(key, String.valueOf(val));
	}

	public KeyValueStringBuilder addLong(String key, long val) {
		return addParam(key, String.valueOf(val));
	}

	public KeyValueStringBuilder addFloat(String key, float val) {
		return addParam(key, String.valueOf(val));
	}

	public KeyValueStringBuilder addDouble(String key, double val) {
		return addParam(key, String.valueOf(val));
	}

	public KeyValueStringBuilder addShort(String key, short val) {
		return addParam(key, String.valueOf(val));
	}

	public KeyValueStringBuilder addChar(String key, char val) {
		return addParam(key, String.valueOf(val));
	}

	public KeyValueStringBuilder addChars(String key, char[] chars) {
		return addParam(key, String.valueOf(chars));
	}

	/**
	 * Add parameters, the value object must implement toString() method.
	 * @param key
	 * @param value
	 * @return
	 */
	public KeyValueStringBuilder add(String key, Object value) {
		addParam(key, value.toString());
		return this;
	}

	/**
	 * Remove parameters.
	 * @param key
	 */
	public KeyValueStringBuilder remove(String key) {
		paramMap.remove(key);
		return this;
	}

	public List<NameValuePair> toNameValuePairList() {
		if (paramMap.isEmpty()) {
			return Collections.emptyList();
		}
		List<NameValuePair> list = new ArrayList<NameValuePair>(paramMap.size());
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return list;
	}

	public List<Header> toHeaderList() {
		if (paramMap.isEmpty()) {
			return Collections.emptyList();
		}
		List<Header> list = new ArrayList<Header>(paramMap.size());
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			list.add(new BasicHeader(entry.getKey(), entry.getValue()));
		}
		return list;
	}

	public Map<String, String> build() {
		return new HashMap<String, String>(this.paramMap);
	}

	public Map<String, String> paramsMap() {
		return build();
	}

	private KeyValueStringBuilder addParam(String key, String value) {
		paramMap.put(key, value);
		return this;
	}
}
