package net.sephy.postman.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Sephy
 * @since: 2015-10-28
 */
public abstract class ObjectMapperUtils {

	private static final ObjectMapper OBJECT_MAPPER;

	static {
		OBJECT_MAPPER = new ObjectMapper();
		OBJECT_MAPPER.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 忽略未映射的字段
	}

	/**
	 * 获取默认的 ObjectMapper 对象
	 * @return
	 */
	public static final ObjectMapper getDefaultObjectMapper() {
		return OBJECT_MAPPER;
	}
}
