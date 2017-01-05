package com.mints.util.weather;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 百度天气util 从json转为天气类
 * 
 * @author Justin
 * @date 2016年12月13日
 */

public class WeatherUtilOfBaidu {
	
	//百度API获取天气
	public static String getWeatherFromBaidu(String cityName) throws Exception{
		ClassLoader cld = Thread.currentThread().getContextClassLoader();
		InputStream io = null;
    	io =  cld.getResourceAsStream("weather.properties");
		Properties pro = new Properties();
		pro.load(io);
		String httpUrl = pro.getProperty("httpUrl");
		String ak = pro.getProperty("ak");
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl+"?location="+URLEncoder.encode(cityName, "UTF-8")+
	            "&output=json&ak="+ak;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	      //  connection.setRequestProperty("apikey",  "83644eb5a6c4f15d58182c488d374c0d");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	//解析json字符串成天气类
	@SuppressWarnings("deprecation")
	public static WeatherInf resolveWeatherInf(String strPar) {

		JSONObject dataOfJson = JSONObject.fromObject(strPar);

		if (dataOfJson.getInt("error") != 0) {
			return null;
		}

		// 保存全部的天气信息。
		WeatherInf weatherInf = new WeatherInf();

		// 从json数据中取得的时间�?
		String date = dataOfJson.getString("date");
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(5, 7));
		int day = Integer.parseInt(date.substring(8, 10));
		Date today = new Date(year - 1900, month - 1, day);

		JSONArray results = dataOfJson.getJSONArray("results");
		JSONObject results0 = results.getJSONObject(0);

		String location = results0.getString("currentCity");
		int pmTwoPointFive;

		if (results0.getString("pm25").isEmpty()) {
			pmTwoPointFive = 0;
		} else {
			pmTwoPointFive = results0.getInt("pm25");
		}
		// System.out.println(results0.get("pm25").toString()+"11");

		try {

			JSONArray index = results0.getJSONArray("index");
			JSONObject index0 = index.getJSONObject(0);// 穿衣
			JSONObject index1 = index.getJSONObject(1);// 洗车
			JSONObject index2 = index.getJSONObject(2);// 感冒
			JSONObject index3 = index.getJSONObject(3);// 运动
			JSONObject index4 = index.getJSONObject(4);// 紫外线强度
			String dressAdvise = index0.getString("des");// 穿衣建议
			String washCarAdvise = index1.getString("des");// 洗车建议
			String coldAdvise = index2.getString("des");// 感冒建议
			String sportsAdvise = index3.getString("des");// 运动建议
			String ultravioletRaysAdvise = index4.getString("des");// 紫外线建议
			weatherInf.setDressAdvise(dressAdvise);
			weatherInf.setWashCarAdvise(washCarAdvise);
			weatherInf.setColdAdvise(coldAdvise);
			weatherInf.setSportsAdvise(sportsAdvise);
			weatherInf.setUltravioletRaysAdvise(ultravioletRaysAdvise);

		} catch (JSONException jsonExp) {

			weatherInf.setDressAdvise("要温度，也要风度。天热缓减衣，天凉及添衣！");
			weatherInf.setWashCarAdvise("你洗还是不洗，灰尘都在哪里，不增不减。");
			weatherInf.setColdAdvise("一天一个苹果，感冒不来找我！多吃水果和蔬菜。");
			weatherInf.setSportsAdvise("生命在于运动！不要总宅在家里哦！");
			weatherInf.setUltravioletRaysAdvise("心灵可以永远年轻，皮肤也一样可以！");
		}

		JSONArray weather_data = results0.getJSONArray("weather_data");

		WeatherOfOneDay[] oneDayWeatherInfS = new WeatherOfOneDay[4];
		for (int i = 0; i < 4; i++) {
			oneDayWeatherInfS[i] = new WeatherOfOneDay();
		}

		for (int i = 0; i < weather_data.size(); i++) {

			JSONObject OneDayWeatherinfo = weather_data.getJSONObject(i);
			String dayData = OneDayWeatherinfo.getString("date");
			WeatherOfOneDay oneDayWeatherInf = new WeatherOfOneDay();

			oneDayWeatherInf.setDate((today.getYear() + 1900) + "."
					+ (today.getMonth() + 1) + "." + today.getDate());
			today.setDate(today.getDate() + 1);// 增加一天

			oneDayWeatherInf.setLocation(location);
			oneDayWeatherInf.setPmTwoPointFive(pmTwoPointFive);

			if (i == 0) {// 第一个，也就是当天的天气，在date字段中最后包含了实时天气
				int beginIndex = dayData.indexOf("：");
				int endIndex = dayData.indexOf(")");
				if (beginIndex > -1) {
					oneDayWeatherInf.setTempertureNow(dayData.substring(
							beginIndex + 1, endIndex));
					oneDayWeatherInf.setWeek(OneDayWeatherinfo
							.getString("date").substring(0, 2));
				} else {
					oneDayWeatherInf.setTempertureNow(" ");
					oneDayWeatherInf.setWeek(OneDayWeatherinfo
							.getString("date").substring(0, 2));
				}

			} else {
				oneDayWeatherInf.setWeek(OneDayWeatherinfo.getString("date"));
			}

			oneDayWeatherInf.setTempertureOfDay(OneDayWeatherinfo
					.getString("temperature"));
			oneDayWeatherInf.setWeather(OneDayWeatherinfo.getString("weather"));
			oneDayWeatherInf.setWind(OneDayWeatherinfo.getString("wind"));

			oneDayWeatherInfS[i] = oneDayWeatherInf;
		}

		weatherInf.setWeatherInfs(oneDayWeatherInfS);

		return weatherInf;
	}
	
	//此处根据具体情况进行输出：下面示例为将天气类转为相应的微信输出字符串格式,只需要三天的天气情况
	public static String toWechatMsg(WeatherInf weatherInf){
		StringBuffer sb = new StringBuffer();
		WeatherOfOneDay [] day = weatherInf.getWeatherInfs();
		for(int i = 0 ; i < day.length; i++){
			WeatherOfOneDay temp = day[i];
			if(i == 0){
				sb.append(temp.getDate()+"("+temp.getWeek()+"):");
				sb.append(temp.getWeather());
				sb.append("\r温度:");
				sb.append(temp.getTempertureOfDay());
				sb.append("  风力:");
				sb.append(temp.getWind());
				sb.append("\r当前温度:");
				sb.append(temp.getTempertureNow());
				sb.append("  PM2.5:");
				sb.append(temp.getPmTwoPointFive()+"\r");
				sb.append("------------------------------\r");
			}else{
				sb.append(temp.getDate()+"("+temp.getWeek()+"):");
				sb.append(temp.getWeather());
				sb.append("\r温度:");
				sb.append(temp.getTempertureOfDay());
				sb.append("  风力:");
				sb.append(temp.getWind()+"\r");
			}
		}
		
		return sb.toString();
		
	}
	
}
