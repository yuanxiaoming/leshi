package com.ch.leyu.http.work;

import org.codehaus.jackson.map.ObjectMapper;

import com.ch.leyu.http.parserinterface.BaseParser;

public class JacksonParser<T> implements BaseParser<T> {

	private static ObjectMapper sObjectMapper;
	/** 解析类型对象 **/
	private Class<T> mClazz;

	/** jackson解析对象 **/

	public JacksonParser(Class<T> clazz) {
		this.mClazz = clazz;
	}

	public static void clear() {
		sObjectMapper = null;
	}

	public T parse(String rsp) throws Exception {
		if (sObjectMapper == null) {
			sObjectMapper = new ObjectMapper();
			sObjectMapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			sObjectMapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
			// objectMapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_NULL_PROPERTIES,
			// false);
		}
		return sObjectMapper.readValue(rsp, mClazz);
	}

	public Class<T> getResponseClass() {
		return mClazz;
	}
}
