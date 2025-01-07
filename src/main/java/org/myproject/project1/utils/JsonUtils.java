package org.myproject.project1.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;


import java.util.List;

/**
 * @author nguyenle
 * @since 1:19 PM Mon 1/6/2025
 */
@UtilityClass
public class JsonUtils {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	static {
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static String toJson(Object obj) {
		try {
			return OBJECT_MAPPER.writeValueAsString(obj);
		} catch (Exception e) {
			return "";
		}
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		try {
			return OBJECT_MAPPER.readValue(json, clazz);
		} catch (Exception ex) {
			return null;
		}
	}

	public static List<List<Integer>> convertJsonToList(String jsonString) throws Exception {
		try {
			// Chuyển đổi JSON string thành List<List<Integer>>
			return OBJECT_MAPPER.readValue(jsonString, new TypeReference<List<List<Integer>>>() {});
		} catch (Exception e) {
			throw new Exception("Lỗi khi chuyển đổi JSON: " + e.getMessage());
		}
	}

}
