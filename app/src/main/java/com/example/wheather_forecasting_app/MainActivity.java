package com.example.wheather_forecasting_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import model.Current;
import model.Hourly;
import model.mainJSON;
import model.Wheather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;

    double latitude;
    double longitude;

    ImageView iv_weather_img;
    TextView tv_temp;
    TextView tv_weather;
    TextView tv_weather_desc;

    RecyclerView rc_hourly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_weather_img = findViewById(R.id.main_weather_img);
        tv_temp = findViewById(R.id.tv_temp);
        tv_weather = findViewById(R.id.tv_weather);
        tv_weather_desc = findViewById(R.id.tv_weather_desc);

        rc_hourly = findViewById(R.id.rc_hourly);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        getLocation();
    }

    private void getLocation(){
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();

                    if(location != null){
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

                        try {
                            List<Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(),location.getLongitude(), 1);

                            latitude = (double) addresses.get(0).getLatitude();
                            longitude = (double) addresses.get(0).getLongitude();

                            getWeather(latitude, longitude);

                            Log.i("LATITUDE", String.valueOf(latitude));
                            Log.i("LONGITUDE", String.valueOf(longitude));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }else{
            ActivityCompat.requestPermissions(MainActivity.this
                    ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void getWeather(double lat, double lon){

        apiInterface ApiInterface = apiClient.getClient().create(apiInterface.class);

        HashMap<String, Object> queries = new HashMap<>();
        queries.put("lat", lat);
        queries.put("lon", lon);
        queries.put("exclude", "minutely,dialy,alerts");
        queries.put("appid", "1f3f706a08e8ab4cb030d825393fcbe6");

        Call<String> getForecast = ApiInterface.getWeather(queries);

        getForecast.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("API_RESPONSE", "Success");

//                ArrayList<Hourly> Hourly =  mainJSON.parseMain(response.body()).hourly;
                Current current = new Current();

                Wheather icon = current.currentWeather.get(3);
                Log.i("Inside-ge-weather", String.valueOf(icon));

                Glide.with(MainActivity.this).load("http://openweathermap.org/img/wn/" + icon).into(iv_weather_img);
                tv_temp.setText(String.valueOf(current.temp));
                tv_weather.setText(String.valueOf(current.currentWeather.get(1)));
                tv_weather_desc.setText(String.valueOf(current.currentWeather.get(2)));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("API_RESPONSE", "Failed");
                Toast.makeText(MainActivity.this, "Response Not Recieved", Toast.LENGTH_LONG).show();
            }
        });
    }
}