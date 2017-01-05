package com.mints.util.weather;

/**
 * 存储查询出的天气信息
 * @author Justin
 * @date 2016年12月13日
 */
public class WeatherInf {  
	  
    private WeatherOfOneDay[] weatherInfs; //五天的天气情况
    private String dressAdvise;//穿衣建议  
    private String washCarAdvise;//洗车建议  
    private String coldAdvise;//感冒建议  
    private String sportsAdvise;//运动建议  
    private String ultravioletRaysAdvise;//紫外线建议  
      
      
    public WeatherInf(){  
        dressAdvise = "";  
        washCarAdvise = "";  
        coldAdvise = "";  
        sportsAdvise = "";  
        ultravioletRaysAdvise = "";  
    }  
      
    public void printInf(){  
          
        System.out.println(dressAdvise);  
        System.out.println(washCarAdvise);  
        System.out.println(coldAdvise);  
        System.out.println(sportsAdvise);  
        System.out.println(ultravioletRaysAdvise);  
        for(int i=0;i<weatherInfs.length;i++){  
            System.out.println(weatherInfs[i]);  
        }  
          
    }  
      
  
    public WeatherOfOneDay[] getWeatherInfs() {  
        return weatherInfs;  
    }  
  
  
    public void setWeatherInfs(WeatherOfOneDay[] weatherInfs) {  
        this.weatherInfs = weatherInfs;  
    }  
  
  
    public String getDressAdvise() {  
        return dressAdvise;  
    }  
  
  
    public void setDressAdvise(String dressAdvise) {  
        this.dressAdvise = dressAdvise;  
    }  
  
  
    public String getWashCarAdvise() {  
        return washCarAdvise;  
    }  
  
  
    public void setWashCarAdvise(String washCarAdvise) {  
        this.washCarAdvise = washCarAdvise;  
    }  
  
  
    public String getColdAdvise() {  
        return coldAdvise;  
    }  
  
  
    public void setColdAdvise(String coldAdvise) {  
        this.coldAdvise = coldAdvise;  
    }  
  
  
    public String getSportsAdvise() {  
        return sportsAdvise;  
    }  
  
  
    public void setSportsAdvise(String sportsAdvise) {  
        this.sportsAdvise = sportsAdvise;  
    }  
  
  
    public String getUltravioletRaysAdvise() {  
        return ultravioletRaysAdvise;  
    }  
  
  
    public void setUltravioletRaysAdvise(String ultravioletRaysAdvise) {  
        this.ultravioletRaysAdvise = ultravioletRaysAdvise;  
    }  
      
}  
