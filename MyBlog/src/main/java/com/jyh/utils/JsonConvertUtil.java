package com.jyh.utils;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * json数据转换工具类
 * @author OverrideRe
 *
 */
public class JsonConvertUtil {
	static String jsonStr;

	public static String returnJson(Object object) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		StringWriter stringWriter = new StringWriter();
		objectMapper.writeValue(stringWriter, object);
		jsonStr = stringWriter.toString();
		return jsonStr;
	}
}