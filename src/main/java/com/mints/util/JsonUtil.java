package com.lingnet.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;


public class JsonUtil {
	// jackson ObjectMapper Bean名称
	private static final String JACKSON_OBJECT_MAPPER_BEAN_NAME = "jacksonObjectMapper";

	public static ObjectMapper getMapper() {
        ObjectMapper mapper = (ObjectMapper) SpringUtil
                .getBean(JACKSON_OBJECT_MAPPER_BEAN_NAME);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
	}

	// 将对象转换为JSON字符串
	public static String toJson(Object object) {
		ObjectMapper mapper = getMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 将JSON字符串转换为对象
	public static <T> T toObject(String json, Class<T> clazz) {
		ObjectMapper mapper = getMapper();
		try {
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 将JSON字符串转换为对象
	public static <T> T toObject(String json, TypeReference<Object> typeReference)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = getMapper();
		return mapper.readValue(json, typeReference);
	}
	/**
     *@description:  根据给定json串解析出内部属性
     *@param json串
     *@return 属性Map
     */
    @SuppressWarnings("static-access")
    public static Map<String,String> parseProperties(String jsonStr){
        if(jsonStr ==null || jsonStr.length()<0){
            return null;
        }
        JSONObject jsonObj = null;
        Map<String, String> pros = new HashMap<String, String>();
        try {
            jsonObj = new JSONObject().fromObject(jsonStr);
            
            @SuppressWarnings ( "rawtypes" )
            Iterator it = jsonObj.keys();
            while(it.hasNext()){
                String key = it.next().toString();
                String value = jsonObj.getString(key);
                pros.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pros;
    }
    
    
    public static String Encode(Object obj) {
        if(obj == null || obj.toString().equals("null")) return null;
        if(obj != null && obj.getClass() == String.class){
            return obj.toString();
        }
        JSONSerializer serializer = new JSONSerializer();
        //serializer.transform(new DateTransformer("yyyy-MM-dd'T'HH:mm:ss"), Date.class);
        //serializer.transform(new DateTransformer("yyyy-MM-dd'T'HH:mm:ss"), Timestamp.class);
        return serializer.deepSerialize(obj);
    }
    
    public static String Encode(Object obj, String format) {
        if(obj == null || obj.toString().equals("null")) return null;
        if(obj != null && obj.getClass() == String.class){
            return obj.toString();
        }
        
        JSONSerializer serializer = new JSONSerializer();
        if (format != null && format.trim().length() > 0) {
        	//serializer.transform(new DateTransformer("yyyy-MM-dd'T'HH:mm:ss"), Date.class);
            serializer.transform(new DateTransformer(format), Timestamp.class);
        }
        
        return serializer.deepSerialize(obj);
    }
    
	public static List<Map<String, String>> getMapList(String jsonStr) {
		if (jsonStr == null || jsonStr.length() < 0) {
			return null;
		}
		JSONArray array = JSONArray.fromObject(jsonStr);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (Iterator iter = array.iterator(); iter.hasNext();) {
			Map<String, String> pros = new HashMap<String, String>();
			JSONObject jsonObj = (JSONObject) iter.next();

			Iterator it = jsonObj.keys();
			while (it.hasNext()) {
				String key = it.next().toString();
				String value = jsonObj.getString(key);
				if(value != null && !"null".equals(value.toLowerCase())) {
					pros.put(key, value);
				}
			}
			list.add(pros);
		}
		return list;
	}
    
	public static Map<String, Map<String, String>> getMapByField(String jsonStr, String field) {
		if (jsonStr == null || jsonStr.length() < 0) {
			return null;
		}
		JSONArray array = JSONArray.fromObject(jsonStr);
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		for (Iterator iter = array.iterator(); iter.hasNext();) {
			String fieldValue = "";
			Map<String, String> pros = new HashMap<String, String>();
			JSONObject jsonObj = (JSONObject) iter.next();

			Iterator it = jsonObj.keys();
			while (it.hasNext()) {
				String key = it.next().toString();
				String value = jsonObj.getString(key);
				if(value != null && !"null".equals(value.toLowerCase())) {
					pros.put(key, value);
				}
				if(key.equals(field)) {
					fieldValue = value;
				}
			}
			map.put(fieldValue, pros);
		}
		return map;
	}
	/*日期转换
	 * 马晓鹏增加 */
	 public static Date dateEncode(String obj,String type) {
		 Date date=new Date();
	        if(obj == null || obj.toString().equals("null") ||obj.toString().equals("")) return null;
	        if(obj != null && obj.getClass() == String.class){
	        	SimpleDateFormat sdf=new SimpleDateFormat(type);
	        	try {
					date=sdf.parse(obj);
				} catch (ParseException e) { 
					e.printStackTrace();
				}
	            return date;
	        }else{
	        	return null;
	        } 
	       
	    }
	 
	 
	 
	
     
}