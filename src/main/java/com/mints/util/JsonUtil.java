package com.mints.util;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import link.jfire.codejson.JsonTool;

/**
 * Json工具类，主要使用Jackson进行解析，并加入codeJson解析方法
 * 
 * @author Justin
 * @date 2017年1月9日
 */

public class JsonUtil {
	private static final Logger log = LogManager.getLogger(JsonUtil.class.getName());

	public static ObjectMapper getMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	/**
	 * 对象转JSON
	 * 
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		ObjectMapper mapper = getMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.error("JsonUtil.toJson(Object object):" + e);
			return null;
		}

	}

	/**
	 * JSON转对象,如果输入class为List.class，则转换成List<Map>格式
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T toObject(String json, Class<T> clazz) {
		ObjectMapper mapper = getMapper();
		try {
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			log.error("JsonUtil.toObject(String json, Class<T> clazz):" + e);
			return null;
		}

	}

	/**
	 * JSON转对象，如果需要转为List或复杂泛型等格式可使用此种方式
	 * 
	 * @param json
	 * @param typeReference
	 * @return
	 * 
	 */
	public static <T> T toObject(String json, TypeReference<Object> typeReference) {
		ObjectMapper mapper = getMapper();
		try {
			return mapper.readValue(json, typeReference);
		} catch (IOException e) {
			log.error("JsonUtil.toObject(String json, TypeReference<Object> typeReference)):" + e);
			return null;
		}
	}

	/**
	 * 对象转JSON，使用codeJson，性能极佳，正确性待测
	 * 
	 * @param object
	 * @return
	 */
	public static String toJsonByCodeJson(Object object) {
		try {
			return JsonTool.write(object);
		} catch (Exception e) {
			log.error("JsonUtil.toJsonByCodeJson(Object object):" + e);
			return null;
		}
	}

	/**
	 * JSON转对象，使用codeJson，性能极佳，正确性待测
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T toObjectByCodeJson(String json, Class<T> clazz) {
		try {
			return JsonTool.read(clazz, json);
		} catch (Exception e) {
			log.error("JtoObjectByCodeJson(String json, Class<T> clazz):" + e);
			return null;
		}
	}

}