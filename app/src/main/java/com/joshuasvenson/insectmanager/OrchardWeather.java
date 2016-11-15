package com.joshuasvenson.insectmanager;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.joshuasvenson.insectmanager.Home.myDb;

public class OrchardWeather extends AppCompatActivity {

    JSONParser1 gw = new JSONParser1();

    public static String lat;
    public static String lon;

    public static String Station;

    TextView city, date, temperature, details, humidity, pressure, feelslike, precip1hr, precipToday, station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.orchard_weather);

        String orchard_key = getIntent().getExtras().get("orchard_key").toString();
        Cursor cursor = myDb.GetOrchardSettings(orchard_key);

        cursor.moveToFirst();

        lat = String.valueOf(cursor.getString(4));
        lon = String.valueOf(cursor.getString(5));


        new letsGetWeather().execute();

    }

    // //////////////////////////////
    // AsyncTask - Load Weather data
    // //////////////////////////////
    private class letsGetWeather extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... params) {


            String weatherData = gw.getWeatherData(lat, lon);

            return weatherData;
        }

        protected void onPostExecute(String weatherData) {

            city =(TextView)findViewById(R.id.city_field);
            date=(TextView)findViewById(R.id.updated_field);
            details = (TextView) findViewById(R.id.details_field);
            humidity = (TextView) findViewById(R.id.humidity_field);
            pressure = (TextView) findViewById(R.id.pressure_field);
            temperature = (TextView) findViewById(R.id.current_temperature_field);
            feelslike = (TextView) findViewById(R.id.feelslike_field);
            precip1hr = (TextView) findViewById(R.id.precip1hr_field);
            precipToday = (TextView)  findViewById(R.id.preciptoday_field);
            station = (TextView) findViewById(R.id.weather_station);

            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();

            String name = gw.getName(weatherData);
            String conditions = gw.getConditions(weatherData);
            String humidity_string = gw.getHumidity(weatherData);
            String pressure_string = gw.getPressure(weatherData);
            String temperature_string = gw.getTemperature(weatherData);
            String feelsLike = gw.getFeelsLike(weatherData);
            String precipitation_1hr = gw.getPrecipitation1hr(weatherData);
            String precipitation_Today = gw.getPrecipitationToday(weatherData);
            String w_station = gw.getStation(weatherData);


            city.setText(name);
            date.setText(df.format(dateobj));
            details.setText(conditions);
            humidity.setText("Humidity: " + humidity_string);
            pressure.setText("Pressure: " + pressure_string);
            temperature.setText(temperature_string);
            feelslike.setText("Feels Like: " + feelsLike);
            precip1hr.setText("Precipitation 1hr: " + precipitation_1hr);
            precipToday.setText("Precipitation Today: " + precipitation_Today);
            station.setText("Weather Station ID: " + w_station);

        }
    }

}
