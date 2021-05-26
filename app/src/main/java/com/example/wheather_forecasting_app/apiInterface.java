package com.example.wheather_forecasting_app;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface apiInterface {

    @GET("data/2.5/onecall")
    Call<String> getWeather(@QueryMap Map<String, Object> queries);

}
