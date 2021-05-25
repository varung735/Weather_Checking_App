package model;

import org.json.JSONObject;

public class Wheather {
    public int id;
    public String main;
    public String description;
    public String icon;

    public static Wheather parseWheather(JSONObject jsonObject){
        Wheather wheather = new Wheather();

        wheather.id = jsonObject.optInt("id");
        wheather.main = jsonObject.optString("main");
        wheather.description = jsonObject.optString("description");
        wheather.icon = jsonObject.optString("icon");

        return wheather;
    }
}
