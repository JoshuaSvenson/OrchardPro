package com.joshuasvenson.insectmanager;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.joshuasvenson.insectmanager.Home.myDb;


public class History extends AppCompatActivity{

    HashMap<String, List<String>> Risk_category;
    List<String> Risk_list;
    ExpandableListView Exp_list;
    MyExListAdapter RiskAdapter;

    String orchardKey;

    JSONParser gw = new JSONParser();

    String lat = "41.66";
    String lon = "-91.53";

    String biofix_year = "2016";
    String biofix_month = "11";
    String biofix_day = "20";

    double base_temperature = 50;
    double average;
    double degree_day;

    String str_date;

    DateFormat formatter;

    TextView MaxTemp, MinTemp;

    List<Date> dates = new ArrayList<Date>();
    ArrayList<Double> myArray = new ArrayList<>();

    double sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.spray_schedule);

        orchardKey = getIntent().getExtras().get("orchard_key").toString();

        Exp_list = (ExpandableListView) findViewById(R.id.exp_list);
        Risk_category = ExpListDataProvider.getInfo(orchardKey);
        Risk_list = new ArrayList<String>(Risk_category.keySet());
        RiskAdapter = new MyExListAdapter(this, Risk_category, Risk_list);
        Exp_list.setAdapter(RiskAdapter);

        lat = myDb.GetOrchardLatitude(Integer.parseInt(orchardKey));
        lon = myDb.GetOrchardLongitude(Integer.parseInt(orchardKey));

        Exp_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getBaseContext(),
                        Risk_category.get(Risk_list.get(groupPosition)).get(childPosition)+" from category " + Risk_list.get(groupPosition) + " is selected",
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });

        Cursor cursor = myDb.GetOrchardBiofix(orchardKey);

        cursor.moveToFirst();

        biofix_day = String.valueOf(cursor.getString(5));
        biofix_month = String.valueOf(cursor.getString(6));
        biofix_year = String.valueOf(cursor.getString(7));

        DecimalFormat two_digit_formatter = new DecimalFormat("00");
        biofix_day = two_digit_formatter.format(Integer.parseInt(biofix_day));
        biofix_month = two_digit_formatter.format(Integer.parseInt(biofix_month));

        DecimalFormat four_digit_formatter = new DecimalFormat("0000");
        biofix_year = two_digit_formatter.format(Integer.parseInt(biofix_year));

        String str_date = biofix_year + biofix_month + biofix_day;

        formatter = new SimpleDateFormat("yyyyMMdd");

        Date startDate = null;

        try {
            startDate = (Date) formatter.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String date = getSetDate();

        Date endDate = null;

        try {
            endDate = (Date) formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long interval = 24 * 1000 * 60 * 60; // 1 hour in millis
        long endTime = endDate.getTime(); // create your endtime here, possibly using Calendar or Date
        long curTime = startDate.getTime();

        while (curTime < endTime) {
            dates.add(new Date(curTime));
            curTime += interval;
        }

        String[] array_dates = new String[dates.size()];

        for (int i = 0; i < dates.size(); i++) {
            Date lDate = (Date) dates.get(i);
            array_dates[i] = formatter.format(lDate);
        }

        new letsGetWeather().execute(array_dates);

        cursor.close();
    }

    public static String getSetDate(){
        String setDate;

        Cursor result = myDb.GetSettingsDate();
        result.moveToFirst();

        String setYear = String.valueOf(result.getString(2));
        String setMonth = String.valueOf(result.getString(1));
        String setDay = String.valueOf(result.getString(0));

        DecimalFormat two_digit_formatter = new DecimalFormat("00");
        String setDayFormatted = two_digit_formatter.format(Integer.parseInt(setDay));
        String setMonthFormatted = two_digit_formatter.format(Integer.parseInt(setMonth));

        DecimalFormat four_digit_formatter = new DecimalFormat("0000");
        String setYearFormatted = four_digit_formatter.format(Integer.parseInt(setYear));

        setDate = setYearFormatted + setMonthFormatted + setDayFormatted;

        return setDate;
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
                if(count == 9) {
                    try {
                        Thread.sleep(65000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count = 0;
                }
            }

            return weatherData;
        }

        protected void onPostExecute(String[] weatherData) {

            Cursor cursor = myDb.GetOrchardBiofix(orchardKey);

            cursor.moveToFirst();

            for(int count = 0; count < cursor.getCount(); count++) {
                sum = 0;
                base_temperature = myDb.GetInsectLowerThreshTemp(String.valueOf(cursor.getString(8)));
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

                Toast.makeText(History.this, "Latitude: " +lat +
                                " Longitude: " +lon +
                                " Biofix Date: " +biofix_month + "/" + biofix_day + "/" + biofix_year + ".........DEGREE DAYS: " +
                                sum + ".........",
                        Toast.LENGTH_LONG).show();

                myDb.SetDegreeDay(cursor.getString(0), sum);
                cursor.moveToNext();
            }

            cursor.close();
        }
    }
}



