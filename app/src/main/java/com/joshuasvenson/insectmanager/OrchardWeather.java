package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.joshuasvenson.insectmanager.Home.myDb;

/*
Name: OrchardWeather
Description: This class provides the code for the page where users can see the current weather at their orchard
Layout File: orchard_weather.xml
 */
public class OrchardWeather extends AppCompatActivity {

    XMLParser1 gw = new XMLParser1();

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





    /*
    Name: onCreateOptionsMenu
    Description: Initialize the contents of the Activity's standard options menu.
    Parameters: Menu menu - The options menu in which you place your items.
    Returns: boolean - You must return true for the menu to be displayed; if you return false it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. This adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //return true to display menu
        return true;
    }

    /*
    Name: onOptionsItemSelected
    Description: This hook is called whenever an item in your options menu is selected. The default implementation
                simply returns false to have the normal processing happen (calling the item's Runnable or sending
                a message to its Handler as appropriate). You can use this method for any items for which you would
                like to do processing without those other facilities.
    Parameters: MenuItem item - The menu item that was selected.
    Returns: boolean - Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        final Context context = this;

        //noinspection SimplifiableIfStatement
        if (id == R.id.Home_bar) {
            Intent intent = new Intent(context, Home.class);
            startActivity(intent);
            return true;
        }
        else if(id ==R.id.Insects_bar) {
            Intent intent = new Intent(context, Insects.class);
            startActivity(intent);
            return true;
        }
        else if(id ==R.id.Diseases_bar) {
            Intent intent = new Intent(context, Diseases.class);
            startActivity(intent);
            return true;
        }
        else if(id ==R.id.LocalWeather_bar) {
            Intent intent = new Intent(context, Weather1.class);
            startActivity(intent);
            return true;
        }
        else if(id ==R.id.Settings_bar) {
            Intent intent = new Intent(context, SettingsToolbar.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
