package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Hourly implements Serializable {

    public long dt;
    public double temp;
    public double feels_like;
    public int pressure;
    public int humidity;
    public double dew_point;
    public int clouds;
    public double wind_speed;
    public int wind_deg;
    public double wind_gust;
    public ArrayList<Wheather> hourlyWeather;

    public static Hourly parseHourly(JSONObject jsonObject){
        Hourly hourly = new Hourly();

        hourly.dt = jsonObject.optLong("dt");
        hourly.temp = jsonObject.optDouble("temp");
        hourly.feels_like = jsonObject.optInt("feels_like");
        hourly.pressure = jsonObject.optInt("pressure");
        hourly.humidity = jsonObject.optInt("humidity");
        hourly.dew_point = jsonObject.optDouble("dew_point");
        hourly.clouds = jsonObject.optInt("clouds");
        hourly.wind_speed = jsonObject.optDouble("wind_speed");
        hourly.wind_deg = jsonObject.optInt("wind_deg");
        hourly.wind_gust = jsonObject.optDouble("wind_gust");

        hourly.hourlyWeather = new ArrayList<>();

        JSONArray weatherArray = jsonObject.optJSONArray("weather");

        if(weatherArray.length() > 0){
            for(int i = 0; i < weatherArray.length(); i++){
                JSONObject weatherObject = weatherArray.optJSONObject(i);

                Wheather weather = Wheather.parseWheather(weatherObject);
                hourly.hourlyWeather.add(weather);
            }
        }

        return hourly;
    }

}
