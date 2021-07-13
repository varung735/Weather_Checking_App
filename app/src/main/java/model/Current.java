package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Current implements Serializable {
    public long dt;
    public long sunrise;
    public long sunset;
    public double temp;
    public double feels_like;
    public int pressure;
    public int humidity;
    public int clouds;
    public int visibility;
    public int wind_speed;
    public int wind_deg;
    public ArrayList<Wheather> currentWeather;

    public static Current parseCurrent(JSONObject jsonObject){
        Current current = new Current();

        current.dt = jsonObject.optLong("dt");
        current.sunrise = jsonObject.optLong("sunrise");
        current.sunset = jsonObject.optLong("sunset");
        current.temp = jsonObject.optDouble("temp");
        current.feels_like = jsonObject.optDouble("feels_like");
        current.pressure = jsonObject.optInt("pressure");
        current.humidity = jsonObject.optInt("humidity");
        current.clouds = jsonObject.optInt("clouds");
        current.visibility = jsonObject.optInt("visibility");
        current.wind_speed = jsonObject.optInt("wind_speed");
        current.wind_deg = jsonObject.optInt("wind_deg");

        current.currentWeather = new ArrayList<>();

        JSONArray weatherArray = jsonObject.optJSONArray("weather");

        if(weatherArray.length() > 0){
            for(int i = 0; i < weatherArray.length(); i++){
                JSONObject weatherObject = weatherArray.optJSONObject(i);

                Wheather weather = Wheather.parseWheather(weatherObject);
                current.currentWeather.add(weather);
            }
        }

        return current;
    }
}
