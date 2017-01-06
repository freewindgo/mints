package com.lingnet.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class ReflectionUtil {
	
	/**
	 * 调用Getter方法
	 * 
	 * @param object
	 *            对象
	 *            
	 * @param propertyName
	 *            属性名称
	 */
	public static Object invokeGetterMethod(Object object, String propertyName) {
		String getterMethodName = "get" + StringUtils.capitalize(propertyName);
		try {
			Method getterMethod = object.getClass().getMethod(getterMethodName);
			return getterMethod.invoke(object);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 调用Setter方法
	 * 
	 * @param object
	 *            对象
	 *            
	 * @param propertyName
	 *            属性名称
	 *            
	 * @param propertyValue
	 *            属性值
	 */
	public static void invokeSetterMethod(Object object, String propertyName, Object propertyValue) {
		Class<?> setterMethodClass = propertyValue.getClass();
		invokeSetterMethod(object, propertyName, propertyValue, setterMethodClass);
	}
	
	/**
	 * 调用Setter方法
	 * 
	 * @param object
	 *            对象
	 *            
	 * @param propertyName
	 *            属性名称
	 *            
	 * @param propertyValue
	 *            属性值
	 *            
	 * @param setterMethodClass
	 *            参数类型
	 */
	public static void invokeSetterMethod(Object object, String propertyName, Object propertyValue, Class<?> setterMethodClass) {
        //返回对象中的set方法字符串
        String setterMethodName = "set" + StringUtils.capitalize(propertyName);
        try {
            //获取对象的方法信息
            Method setterMethod = object.getClass().getMethod(setterMethodName, setterMethodClass);
            //调用对象中方法
            setterMethod.invoke(object, propertyValue);
            return;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        
        HashMap<Class<?>, Class<?>> mapClass = new HashMap<Class<?>, Class<?>>();
        mapClass.put(Integer.class, int.class);
        mapClass.put(Double.class, double.class);
        mapClass.put(Float.class, float.class);
        mapClass.put(Boolean.class, boolean.class);
        mapClass.put(Byte.class, byte.class);

        try {
            //获取对象的方法信息
            Method setterMethod = object.getClass().getMethod(setterMethodName, mapClass.get(setterMethodClass));
            //调用对象中方法
            setterMethod.invoke(object, propertyValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 获取对象属性值,无视private/protected/getter
	 * 
	 * @param object
	 *            对象
	 *            
	 * @param fieldName
	 *            属性名称
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
			
		}
		return result;
	}

	/**
	 * 设置对象属性值,无视private/protected/setter
	 * 
	 * @param object
	 *            对象
	 *            
	 * @param fieldName
	 *            属性名称
	 */
	public static void setFieldValue(Object object, String fieldName, Object value) {
		Field field = getAccessibleField(object, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Could not find field " + fieldName);
		}
		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			
		}
	}

	private static Field getAccessibleField(final Object object, final String fieldName) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {
				
			}
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> HashMap getMap(T t)
			throws Exception {
		
		HashMap map = new HashMap();
		if(t==null) {
			return map;
		}
		
		Class<?> clazz = t.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			if("serialVersionUID".equals(f.getName())) {
				continue;
			}
			map.put(f.getName(), invokeGetterMethod(t, f.getName()));
		}
		if(!map.containsKey("id")) {
			map.put("id", invokeGetterMethod(t, "id"));
		}
		
		return map;
	}
	
	@SuppressWarnings("rawtypes")
    public static <T> List<HashMap> getMapList(T t)
			throws Exception {
		List<HashMap> mapList = new ArrayList<HashMap>();
		if(t==null) {
			return mapList;
		}
		mapList.add(getMap(t));
		return mapList;
	}
	/**
	 * 增加获取基类时间createdate&modifydate语句，方便页面展示
	 * map.put("createdate", invokeGetterMethod(t, "createDate"));
	   map.put("modifydate",invokeGetterMethod(t, "modifyDate") );
	 * @author 段晶晶
	 * @date   2013-10-12
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> List<HashMap> getMapList(List<T> tList) {
		List<HashMap> mapList = new ArrayList<HashMap>();
		if (tList == null || tList.size() == 0) {
			return mapList;
		}

		Class<?> clazz = tList.get(0).getClass();
		Field[] fields = clazz.getDeclaredFields();

		for (T t : tList) {
			HashMap map = new HashMap();
			for (Field f : fields) {
				if("serialVersionUID,qxRoleResources,qxRoles,".indexOf(f.getName() + ",")>=0) {
					continue;
				}
				map.put(f.getName(), invokeGetterMethod(t, f.getName()));
				map.put("createdate", invokeGetterMethod(t, "createDate"));
				map.put("modifydate",invokeGetterMethod(t, "modifyDate") );
			}
				
			if(!map.containsKey("id")) {
				map.put("id", invokeGetterMethod(t, "id"));
			}
			mapList.add(map);
		}
		return mapList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> HashMap getPaperMap(Pager pager) throws Exception {
		HashMap result= new HashMap();
		List<HashMap> mapList = getMapList(pager.getResult());
		result.put("data", mapList);
		result.put("total", pager.getTotalCount());
		return result;
	}

}