package com.joshuasvenson.insectmanager;

/**
 * Created by anaso_000 on 9/21/2016.
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

public class JSONParser {

    // N4n8Rr8T0MmshJsPpTftAFlmgjGEp1fJuQzjsnADtsEG9RFJRR
    // Get you own API Key here: http://www.wunderground.com/weather/api
    static final String API_KEY = "61ed0556abc8b758";

    public JSONParser() {}

    public String getWeatherData(String date, String lat, String lon) {

        String url = "http://api.wunderground.com/api/" + API_KEY
                + "/history_"+date+"/q/"+lat+","+lon+".xml";

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
    
    public String getConditions(String weatherData) {

        String conditions = null;

        Pattern p = Pattern.compile("<weather>(.*?)</weather>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            conditions = m.toMatchResult().group(1);
        }
        return conditions;
    }

    public String getMaxTemp(String weatherData) {

        String maxTemp = null;

        Pattern p = Pattern.compile("<maxtempi>(.*?)</maxtempi>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            maxTemp = m.toMatchResult().group(1);
        }
        return maxTemp;
    }

    public String getMinTemp(String weatherData) {

        String minTemp = null;

        Pattern p = Pattern.compile("<mintempi>(.*?)</mintempi>");
        Matcher m = p.matcher(weatherData);
        if (m.find()) {
            minTemp = m.toMatchResult().group(1);
        }
        return minTemp;
    }

    /////////////////////////////////////////////////////////////
    // Add methods as needed for addition data retrieval from XML
    /////////////////////////////////////////////////////////////
}

