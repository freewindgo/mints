package com.mints.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReflectionUtil {
	private static final Logger log = LogManager.getLogger(JsonUtil.class.getName());

	/**
	 * 调用Getter方法
	 * 
	 * @param object
	 * @param propertyName
	 * @return
	 */
	public static Object invokeGetterMethod(Object object, String propertyName) {
		String getterMethodName = "get" + StringUtils.capitalize(propertyName);
		try {
			Method getterMethod = object.getClass().getMethod(getterMethodName);
			return getterMethod.invoke(object);
		} catch (Exception e) {
			log.error("ReflectionUtil.invokeGetterMethod(Object object, String propertyName):" + e);
			return null;
		}
	}

	/**
	 * 调用Setter方法
	 * 
	 * @param object
	 * @param propertyName
	 * @param propertyValue
	 */
	public static void invokeSetterMethod(Object object, String propertyName, Object propertyValue) {
		Class<?> setterMethodClass = propertyValue.getClass();
		invokeSetterMethod(object, propertyName, propertyValue, setterMethodClass);
	}

	/**
	 * 调用Setter方法
	 * 
	 * @param object
	 * @param propertyName
	 * @param propertyValue
	 * @param setterMethodClass
	 */
	public static void invokeSetterMethod(Object object, String propertyName, Object propertyValue,
			Class<?> setterMethodClass) {
		// 返回对象中的set方法字符串
		String setterMethodName = "set" + StringUtils.capitalize(propertyName);
		try {
			// 获取对象的方法信息
			Method setterMethod = object.getClass().getMethod(setterMethodName, setterMethodClass);
			// 调用对象中方法
			setterMethod.invoke(object, propertyValue);
			return;
		} catch (Exception e) {
			log.error(
					"ReflectionUtil.invokeSetterMethod(Object object, String propertyName, Object propertyValue, Class<?> setterMethodClass):"
							+ e);
		}

		HashMap<Class<?>, Class<?>> mapClass = new HashMap<Class<?>, Class<?>>();
		mapClass.put(Integer.class, int.class);
		mapClass.put(Double.class, double.class);
		mapClass.put(Float.class, float.class);
		mapClass.put(Boolean.class, boolean.class);
		mapClass.put(Byte.class, byte.class);

		try {
			// 获取对象的方法信息
			Method setterMethod = object.getClass().getMethod(setterMethodName, mapClass.get(setterMethodClass));
			// 调用对象中方法
			setterMethod.invoke(object, propertyValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得对象属性值
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(Object object, String fieldName) {
		Field field = getAccessibleField(object, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Could not find field " + fieldName);
		}
		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			log.error("ReflectionUtil.getFieldValue(Object object, String fieldName):" + e);
		}
		return result;
	}

	/**
	 * 设置对象属性值,无视private/protected/setter
	 * 
	 * @param object
	 * @param fieldName
	 * @param value
	 */
	public static void setFieldValue(Object object, String fieldName, Object value) {
		Field field = getAccessibleField(object, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Could not find field " + fieldName);
		}
		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			log.error("ReflectionUtil.setFieldValue(Object object, String fieldName, Object value):" + e);
		}
	}

	/**
	 * 获取可访问到的字段值
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	private static Field getAccessibleField(final Object object, final String fieldName) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {
				log.error("ReflectionUtil.getAccessibleField(final Object object, final String fieldName):" + e);
			}
		}
		return null;
	}

	/**
	 * 将对象转为Map
	 * 
	 * @param t
	 * @return
	 */
	public static <T> Map<String, Object> getMap(T t) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (t == null) {
			return map;
		}

		Class<?> clazz = t.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			if ("serialVersionUID".equals(f.getName())) {
				continue;
			}
			map.put(f.getName(), invokeGetterMethod(t, f.getName()));
		}
		if (!map.containsKey("id")) {
			map.put("id", invokeGetterMethod(t, "id"));
		}

		return map;
	}

	/**
	 * List中的对象转换为List中的Map
	 * 
	 * @param t
	 * @return
	 */
	public static <T> List<Map<String, Object>> getMapList(T t) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		if (t == null) {
			return mapList;
		}
		mapList.add(getMap(t));
		return mapList;
	}

}