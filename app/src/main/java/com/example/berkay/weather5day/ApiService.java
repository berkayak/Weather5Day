package com.example.berkay.weather5day;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Berkay on 1.01.2017.
 */

public interface ApiService {

    @GET("data/2.5/forecast")
    Call<WeatherModel> getWeatherService(@Query("q") String city, @Query("appid") String appid);
}
