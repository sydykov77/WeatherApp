package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.data.OpenWeatherMap;
import com.example.weatherapp.model.WeatherModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OpenWeatherMap.WeatherCallback {
    private ImageView vector_sunny;
    private Button btn_search;
    private TextView date, text_sunny, gradus, temp_up, temp_down, number_humidity, number_pressure,
            number_wind, number_sunset, number_sunrise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.openWeatherMap.getWeatherData(this);
        vector_sunny = findViewById(R.id.vector_sunny);
        btn_search = findViewById(R.id.btn_search);
        date = findViewById(R.id.date);
        text_sunny = findViewById(R.id.text_sunny);
        gradus = findViewById(R.id.gradus);
        temp_up = findViewById(R.id.temp_up);
        temp_down = findViewById(R.id.temp_down);
        number_humidity = findViewById(R.id.number_humidity);
        number_pressure = findViewById(R.id.number_pressure);
        number_wind = findViewById(R.id.number_wind);
        number_sunset = findViewById(R.id.number_sunset);
        number_sunrise = findViewById(R.id.number_sunrise);
    }

    @Override
    public void onSuccess(WeatherModel body) {
        Log.e("tag", "MainActivity onSuccess");
        text_sunny.setText(body.getWeather().get(0).getMain());
        Glide.with((this)).load("http://openweathermap.org/img/wn/" + body.
                getWeather().get(0).getIcon() + "@2x.png").centerCrop().into(vector_sunny);
        gradus.setText(body.getMain().getTemp().intValue() + "\u2103");
        temp_up.setText(body.getMain().getTempMax().intValue() + "\u2103" + "↑");
        temp_down.setText(body.getMain().getTempMin().intValue() + "\u2103" + "↓");
        number_humidity.setText(body.getMain().getHumidity().intValue() + "%");
        number_pressure.setText(body.getMain().getPressure() + "mBar");
        number_wind.setText(body.getWind().getSpeed() + "km/h");
        long am = Long.valueOf(body.getSys().getSunrise()) * 1000;
        Date sunrise = new java.util.Date(am);
        String sunr = new SimpleDateFormat(" hh:mma").format(sunrise);
        number_sunrise.setText(sunr);
        long pm = Long.valueOf(body.getSys().getSunset()) * 1000;
        Date sunset = new java.util.Date(pm);
        String suns = new SimpleDateFormat(" hh:mma").format(sunset);
        number_sunset.setText(suns);
        String currentDate = new SimpleDateFormat("EEEE dd MM yyyy HH:mm", Locale.getDefault()).format(new Date());
        date.setText(currentDate);
        btn_search.setText(body.getName());
    }


    @Override
    public void onFailure(Exception e) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
