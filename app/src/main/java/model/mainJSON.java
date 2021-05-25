package model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class mainJSON {
    public double lat;
    public double lon;
    public String timeZone;
    public long timezone_offset;
    public ArrayList<Current> current;
    public ArrayList<Hourly> hourly;

    public static mainJSON parseMain(String response){
        mainJSON mainJSON = new mainJSON();

        try {
            JSONObject jsonObject = new JSONObject(response);

            mainJSON.lat = jsonObject.optDouble("lat");
            mainJSON.lon = jsonObject.optDouble("lon");
            mainJSON.timeZone = jsonObject.optString("timezone");
            mainJSON.timezone_offset = jsonObject.optLong("timezone_offset");

            mainJSON.current = new ArrayList<>();
            mainJSON.hourly = new ArrayList<>();

            JSONArray currentArray = jsonObject.optJSONArray("current");
            JSONArray hourArray = jsonObject.optJSONArray("hourly");

            if(currentArray.length() > 0 && hourArray.length() > 0){
                for(int i = 0; i < currentArray.length(); i++){
                    JSONObject currentObject = currentArray.optJSONObject(i);

                    Current current = Current.parseCurrent(currentObject);
                    mainJSON.current.add(current);
                }

                for(int i = 0; i < currentArray.length(); i++){
                    JSONObject hourObject = hourArray.optJSONObject(i);

                    Hourly hour = Hourly.parseHourly(hourObject);
                    mainJSON.hourly.add(hour);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mainJSON;
    }

}
