package com.example.weatherapp;

import android.accounts.NetworkErrorException;
import android.util.Log;

import com.example.weatherapp.WeatherService;
import com.example.weatherapp.model.WeatherModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class WeatherService {

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://ghibliapi.herokuapp.com/")
            .build();

    WeatherApi service = retrofit.create(WeatherApi.class);

    public void getWeather(String cityName,WeatherCallback callback) {
        final Call<WeatherModel> call = service.getWeather(cityName,);
        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d("tag", response.body().toString());
                        callback.onSuccess(response.body());
                    } else {
                        Log.e("tag", "response body is null");
                        callback.onFailure(new NetworkErrorException());
                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                Log.d("tag", "Error");
                callback.onFailure(new Exception());

            }
        });
    }

    public interface WeatherCallback {
        void onSuccess(WeatherModel weatherModel);

        void onFailure(Exception exception);
    }

    public interface WeatherApi {
        @GET("data/2.5/weather")
        Call<WeatherModel> getWeather(
                @Query("q") String cityName,
                @Query("appid") String apiKey
        );


    }

}

