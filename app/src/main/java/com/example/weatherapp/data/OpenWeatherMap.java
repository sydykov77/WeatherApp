package com.example.weatherapp.data;

import android.util.Log;

import com.example.weatherapp.model.WeatherModel;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class OpenWeatherMap {
    private OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClientBuilder.build())
            .baseUrl("http://api.openweathermap.org/")
            .build();

    private WeatherApi service = retrofit.create(WeatherApi.class);

    public void getWeatherData(final WeatherCallback callback){
        service.getWeatherDataByCity("Бишкек", "4bbf5a1ed98cd9f688ebb3651474cdd2","ру", "metric")
                .enqueue(new Callback<WeatherModel>() {
                    @Override
                    public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                        if (response.isSuccessful() && response.body() != null){
                            Log.e("tag", "onResponse: " + response.body().getMain().getTemp().toString());
                            callback.onSuccess(response.body());
                        }
                    }
                    @Override
                    public void onFailure(Call<WeatherModel> call, Throwable t) {
                        Log.e("tag", "onFailure: ", t);
                        callback.onFailure(new Exception());
                    }
                });
    }

    public interface WeatherCallback{
        void onSuccess(WeatherModel weatherModel);

        void onFailure(Exception e);
    }

    public interface WeatherApi{
        @GET("data/2.5/weather")
        Call<WeatherModel> getWeatherDataByCity(@Query("q") String city,
                                                @Query("appid") String appId,
                                                @Query("lang") String lang,
                                                @Query("units") String units);


    }
}
