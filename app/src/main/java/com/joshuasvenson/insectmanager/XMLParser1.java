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

/*
Name: XMLParser1
Description: This class provides the code for fetching data from the API server Weather underground correponding to
current conditions of the weather according to the coordinates values, latitude and longitude. Moreover this class
do the parsing from the XML string returned from the API call, and then turn this string into JAVA object
to be used and get specific values like temperature, precipitation level etc.
Layout File: No activity
 */
public class XMLParser1 {

    // Get you own API Key here: http://www.wunderground.com/weather/api
    static final String API_KEY = "61ed0556abc8b758";

    //constructor
    public XMLParser1() {}

    /*
    Name: getWeatherData
    Description: Fecth data from web server though API call according to coordinates values
    Parameters: String lat, Strin lon
    Returns: String, XML string from web server
    */
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

    /*
    Name: getTemperature
    Description: This method will parse an XML string into Java objects and get the value of the
    temperature from the XML string
    Paremeters: A string in XML format
    Returns: String with the temperature value from the XML string
     */
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

    /*
    Name: getConditions
    Description: This method will parse an XML string into Java objects and get the decription of the
     conditions from the XML string
    Paremeters: A string in XML format
    Returns: String with the desription of the conditions from the XML string
     */
    public String getConditions(String weatherData) {

        String conditions = null;

        Pattern p = Pattern.compile("<weather>(.*?)</weather>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            conditions = m.toMatchResult().group(1);
        }
        return conditions;
    }

    /*
    Name: getName
    Description: This method will parse an XML string into Java objects and get the name of the city
    from the XML string
    Paremeters: A string in XML format
    Returns: String of the name of the city from the XML string
     */
    public String getName(String weatherData) {

        String name = null;

        Pattern p = Pattern.compile("<full>(.*?)</full>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            name = m.toMatchResult().group(1);
        }
        return name;
    }

    /*
    Name: getStation
    Description: This method will parse an XML string into Java objects and get the value of the
    station from the XML string
    Paremeters: A string in XML format
    Returns: String of the station value from the XML string
     */
    public String getStation(String weatherData) {

        String name = null;

        Pattern p = Pattern.compile("<station_id>(.*?)</station_id>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            name = m.toMatchResult().group(1);
        }
        return name;
    }

    /*
    Name: getHumidity
    Description: This method will parse an XML string into Java objects and get the value of the
    humidity from the XML string
    Paremeters: A string in XML format
    Returns: String with the humidity value from the XML string
     */
    public String getHumidity(String weatherData) {

        String humidity = null;

        Pattern p = Pattern.compile("<relative_humidity>(.*?)</relative_humidity>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            humidity = m.toMatchResult().group(1);
        }
        return humidity;
    }

    /*
    Name: getPressure
    Description: This method will parse an XML string into Java objects and get the value of the
    pressure from the XML string
    Paremeters: A string in XML format
    Returns: String with the pressure value from the XML string
     */
    public String getPressure(String weatherData) {

        String pressure = null;

        Pattern p = Pattern.compile("<pressure_mb>(.*?)</pressure_mb>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            pressure = m.toMatchResult().group(1);
        }
        return pressure+"hPa";
    }

    /*
    Name: getPrecipitation1hr
    Description: This method will parse an XML string into Java objects and get the value of the
    precipitation 1 hour from the XML string
    Paremeters: A string in XML format
    Returns: String with the precipitation 1 hour value from the XML string
     */
    public String getPrecipitation1hr (String weatherData) {

        String precipitation_1hr = null;

        Pattern p = Pattern.compile("<precip_1hr_string>(.*?)</precip_1hr_string>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            precipitation_1hr = m.toMatchResult().group(1);
        }
        return precipitation_1hr;
    }

    /*
    Name: getPrecipitationToday
    Description: This method will parse an XML string into Java objects and get the value of the
    precipitation today from the XML string
    Paremeters: A string in XML format
    Returns: String with the precipitation today value from the XML string
     */
    public String getPrecipitationToday (String weatherData) {

        String precipitation_Today = null;

        Pattern p = Pattern.compile("<precip_today_string>(.*?)</precip_today_string>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            precipitation_Today = m.toMatchResult().group(1);
        }
        return precipitation_Today;
    }

    /*
    Name: getFeelsLike
    Description: This method will parse an XML string into Java objects and get the value of the
    feels like temperature from the XML string
    Paremeters: A string in XML format
    Returns: String with the temperature feels like value from the XML string
     */
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

