package com.joshuasvenson.insectmanager;

/**
 * Created by anaso_000 on 10/18/2016.
 */

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONParser1 {

    // Get you own API Key here: http://www.wunderground.com/weather/api
    static final String API_KEY = "61ed0556abc8b758";

    public JSONParser1() {}

    public String getWeatherData(String lat, String lon) {

        String url = "http://api.wunderground.com/api/" + API_KEY
                + "/conditions/q/"+lat+","+lon+".xml";

        InputStream is = null;
        String weatherData = null;

        // Get the XML stream from the web
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

        } catch (Exception e) {
            Log.e("getWeather", "Error in http connection " + e.toString());
        }

        // Let's convert stream to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            weatherData= sb.toString();
        } catch (Exception e) {
            Log.e("getWeather", "Error converting result " + e.toString());
        }
        return weatherData;
    }

    public String getTemperature(String weatherData) {

        String temperature = null;
        DecimalFormat df = new DecimalFormat("#");

        Pattern p = Pattern.compile("<temperature_string>(.*?)</temperature_string>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            temperature = m.toMatchResult().group(1);
        }
        //Double current_temp_dbl = Double.parseDouble(temperature);
        //temperature = df.format(current_temp_dbl) + "\u00B0" + "F";

        return temperature;
    }

    public String getConditions(String weatherData) {

        String conditions = null;

        Pattern p = Pattern.compile("<weather>(.*?)</weather>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            conditions = m.toMatchResult().group(1);
        }
        return conditions;
    }

    public String getName(String weatherData) {

        String name = null;

        Pattern p = Pattern.compile("<full>(.*?)</full>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            name = m.toMatchResult().group(1);
        }
        return name;
    }

    public String getStation(String weatherData) {

        String name = null;

        Pattern p = Pattern.compile("<station_id>(.*?)</station_id>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            name = m.toMatchResult().group(1);
        }
        return name;
    }

    public String getHumidity(String weatherData) {

        String humidity = null;

        Pattern p = Pattern.compile("<relative_humidity>(.*?)</relative_humidity>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            humidity = m.toMatchResult().group(1);
        }
        return humidity;
    }

    public String getPressure(String weatherData) {

        String pressure = null;

        Pattern p = Pattern.compile("<pressure_mb>(.*?)</pressure_mb>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            pressure = m.toMatchResult().group(1);
        }
        return pressure+"hPa";
    }

    public String getPrecipitation1hr (String weatherData) {

        String precipitation_1hr = null;

        Pattern p = Pattern.compile("<precip_1hr_string>(.*?)</precip_1hr_string>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            precipitation_1hr = m.toMatchResult().group(1);
        }
        return precipitation_1hr;
    }

    public String getPrecipitationToday (String weatherData) {

        String precipitation_Today = null;

        Pattern p = Pattern.compile("<precip_today_string>(.*?)</precip_today_string>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            precipitation_Today = m.toMatchResult().group(1);
        }
        return precipitation_Today;
    }

    public String getFeelsLike (String weatherData) {

        String feelsLike = null;

        Pattern p = Pattern.compile("<feelslike_string>(.*?)</feelslike_string>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            feelsLike = m.toMatchResult().group(1);
        }
        return feelsLike;
    }



    /////////////////////////////////////////////////////////////
    // Add methods as needed for addition data retrieval from XML
    /////////////////////////////////////////////////////////////
}

