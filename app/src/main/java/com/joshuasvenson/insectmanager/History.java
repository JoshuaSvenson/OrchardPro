package com.joshuasvenson.insectmanager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class History extends AppCompatActivity {

    JSONParser gw = new JSONParser();

    String lat = "41.66";
    String lon = "-91.53";

    String biofix_year = "2016";
    String biofix_month = "10";
    String biofix_day = "31";

    double base_temperature = 50;
    double average;
    double degree_day;

    DateFormat formatter;

    TextView MaxTemp, MinTemp;

    List<Date> dates = new ArrayList<Date>();
    ArrayList<Double> myArray = new ArrayList<>();

    String str_date = biofix_year + biofix_month + biofix_day;

    double sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        formatter = new SimpleDateFormat("yyyyMMdd");

        Date startDate = null;

        try {
            startDate = (Date) formatter.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-1);

        String date = formatter.format(cal.getTime());

        Date endDate = null;

        try {
            endDate = (Date) formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long interval = 24 * 1000 * 60 * 60; // 1 hour in millis
        long endTime = endDate.getTime(); // create your endtime here, possibly using Calendar or Date
        long curTime = startDate.getTime();

        while (curTime <= endTime) {
            dates.add(new Date(curTime));
            curTime += interval;
        }

        String[] array_dates = new String[dates.size()];

        for (int i = 0; i < dates.size(); i++) {
            Date lDate = (Date) dates.get(i);
            array_dates[i] = formatter.format(lDate);
        }

        new letsGetWeather().execute(array_dates);
    }

    // //////////////////////////////
    // AsyncTask - Load Weather data
    // //////////////////////////////
    private class letsGetWeather extends AsyncTask<String[], Void, String[]> {

        protected String[] doInBackground(String[]... array) {


            String[] weatherData = new String[dates.size()];

            int count = 0;
            for (int n = 0; n < dates.size(); n++) {
                weatherData[n] = gw.getWeatherData(array[0][n], lat, lon);
                count++;
                if(count == 9){
                    try {
                        Thread.sleep(65000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count = 0;
                }
                /*if(n % 9 == 0){
                    try {
                        //Thread.sleep(65000);
                        int x = 0;
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }*/
            }

            return weatherData;
        }

        protected void onPostExecute(String[] weatherData) {

            for (int m = 0; m < dates.size(); m++) {
                String max = gw.getMaxTemp(weatherData[m]);
                String min = gw.getMinTemp(weatherData[m]);

                average = 0;
                degree_day = 0;

                average = ((Double.parseDouble(max)) + (Double.parseDouble(min))) / 2;
                if (average > base_temperature) {
                    degree_day = average - base_temperature;
                    myArray.add(degree_day);
                }
                else{
                    myArray.add(degree_day);
                }
            }

            for (int x = 0; x < dates.size(); x++) {
                sum = sum + myArray.get(x);
            }

            MaxTemp = (TextView) findViewById(R.id.max_temp);
            MinTemp = (TextView) findViewById(R.id.min_temp);
            MaxTemp.setText(Double.toString(sum));
            MinTemp.setText(Double.toString(myArray.get(0)));
        }

    }

}

