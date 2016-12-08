package com.joshuasvenson.insectmanager;

/**
 * Created by anaso_000 on 11/8/2016.
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLParser_Station {

    // Our group's API Key here: http://www.wunderground.com/weather/api
    static final String API_KEY = "61ed0556abc8b758";

    //constructor
    public XMLParser_Station() {}

    //method that will get the XML string from the web after the API call
    //Parameter: weather station given by the user
    //Returns a string which is all the XML string from the API call
    public String getWeatherData(String station) {

        String url = "http://api.wunderground.com/api/" + API_KEY
                + "/conditions/q/psw:"+station+".xml";

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
    Name: getLatitude
    Description: This method will parse an XML string into Java objects and get the value of the
    latitude value from the XML string
    Paremeters: A string in XML format
    Returns: String with the latitude value from the XML string
     */
    public String getLatitude(String weatherData) {

        String name = null;

        Pattern p = Pattern.compile("<latitude>(.*?)</latitude>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            name = m.toMatchResult().group(1);
        }
        return name;
    }

    /*
    Name: getLongitude
    Description: This method will parse an XML string into Java objects and get the value of the
    longitude value from the XML string
    Paremeters: A string in XML format
    Returns: String with the longitude value from the XML string
     */
    public String getLongitude(String weatherData) {

        String name = null;

        Pattern p = Pattern.compile("<longitude>(.*?)</longitude>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            name = m.toMatchResult().group(1);
        }
        return name;
    }

    /*
    Name: getStation
    Description: This method will parse an XML string into Java objects and get the value of the
    station value from the XML string
    Paremeters: A string in XML format
    Returns: String with the station id value from the XML string given the coordinates of the location
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

    /////////////////////////////////////////////////////////////
    // Add methods as needed for addition data retrieval from XML
    /////////////////////////////////////////////////////////////
}
