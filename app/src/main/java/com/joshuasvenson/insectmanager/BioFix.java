package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.joshuasvenson.insectmanager.Home.myDb;

/*
Name: BioFix
Description: This class provides the code for page where users add a biofix for an insect
Layout File: activity_bio_fix.xml
 */
public class BioFix extends AppCompatActivity {

    Spinner insectSpinner;
    Button submitButton;
    DatePicker datePicker;

    String orchardKey;

    XMLParser gw = new XMLParser();
    String lat;
    String lon;
    List<Date> dates = new ArrayList<Date>();
    ArrayList<Double> myArray = new ArrayList<>();
    double sum = 0;
    double base_temperature = 50;
    double average;
    double degree_day;

    long insertedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_fix);

        //addItemsOnOrchardSpinner();

        addItemsOnInsectSpinner();

        addListenerOnButton();
    }

    /*
    Name: addItemsOnInsectSpinner
    Description: adds insect to the spinner for when users try to add a biofix
    Parameters: None
    Returns: void
     */
    public void addItemsOnInsectSpinner() {
        final Context context = this;

        insectSpinner = (Spinner) findViewById(R.id.biofix_insect_spinner);

        List<String> insect_list = new ArrayList<String>();

        //Get all insect names in insect table
        Cursor cursor = myDb.GetInsectNames();
        cursor.moveToFirst();
        for(int i=0; i<cursor.getCount();i++){
            //add insect name to the list
            insect_list.add(String.valueOf(cursor.getString(0)));

            if(i!=cursor.getCount()-1)
                cursor.moveToNext();
        }

        cursor.close();

        //Set adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, insect_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        insectSpinner.setAdapter(dataAdapter);
    }

    /*
    Name: addListenerOnButton
    Description: Adds listeners on all of the buttons on the page and provides functionality when each button is clicked
    Parameters: None
    Returns: void
     */
    public void addListenerOnButton(){
        final Context context = this;

        submitButton = (Button) findViewById(R.id.biofix_submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //Get the orchard key from value passed
                orchardKey = getIntent().getExtras().getString("orchard_key");

                //Get insect id
                int insectID = insectSpinner.getSelectedItemPosition() + 1;

                datePicker = (DatePicker) findViewById(R.id.biofix_datePicker);
                Calendar cal = Calendar.getInstance();

                int biofix_day = datePicker.getDayOfMonth();
                int biofix_month = datePicker.getMonth() + 1;
                int biofix_year = datePicker.getYear();

                //Not needed, last update should = biofix at this point
                Cursor result = myDb.GetSettingsDate();
                result.moveToFirst();

                int current_day = Integer.parseInt(String.valueOf(result.getString(0)));
                int current_month = Integer.parseInt(String.valueOf(result.getString(1)));
                int current_year = Integer.parseInt(String.valueOf(result.getString(2)));

                DecimalFormat two_digit_formatter = new DecimalFormat("00");
                String biofixDayFormatted = two_digit_formatter.format(biofix_day);
                String currentDayFormatted = two_digit_formatter.format(current_day);

                String biofixMonthFormatted = two_digit_formatter.format(biofix_month);
                String currentMonthFormatted = two_digit_formatter.format(current_month);

                DecimalFormat four_digit_formatter = new DecimalFormat("0000");
                String biofixYearFormatted = four_digit_formatter.format(biofix_year);
                String currentYearFormatted = two_digit_formatter.format(current_year);

                insertedID = myDb.createBiofix(biofixDayFormatted, biofixMonthFormatted,
                        biofixYearFormatted, 0, biofixDayFormatted, biofixMonthFormatted,
                        biofixYearFormatted, Integer.parseInt(orchardKey), insectID);
                if(insertedID != -1){
                    Toast.makeText(BioFix.this, "Data Inserted "
                            + biofixMonthFormatted+ " " +biofixDayFormatted
                            + " " + biofixYearFormatted +" | " + biofixMonthFormatted + " " +
                            biofixDayFormatted + " " + biofixYearFormatted,Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(BioFix.this, "Error Inserting",Toast.LENGTH_LONG).show();
                }
                Log.d("insertedID1", String.valueOf(insertedID));
                lat = myDb.GetOrchardLatitude(Integer.parseInt(orchardKey));
                lon = myDb.GetOrchardLongitude(Integer.parseInt(orchardKey));
                updateDegreeDays(biofixDayFormatted, biofixMonthFormatted, biofixYearFormatted);
            }

        });
    }

    /*
    Name: updateDegreeDays
    Description: updates the degree days for newly inserted biofix if needed
    Parameters: String biofix_day:
                String biofix_month:
                String biofix_year:
    Returns: void
     */
    protected void updateDegreeDays(String biofix_day, String biofix_month, String biofix_year){

        DecimalFormat two_digit_formatter = new DecimalFormat("00");
        biofix_day = two_digit_formatter.format(Integer.parseInt(biofix_day));
        biofix_month = two_digit_formatter.format(Integer.parseInt(biofix_month));

        DecimalFormat four_digit_formatter = new DecimalFormat("0000");
        biofix_year = two_digit_formatter.format(Integer.parseInt(biofix_year));

        String str_date = biofix_year + biofix_month + biofix_day;

        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        Date startDate = null;

        try {
            startDate = (Date) formatter.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String date = History.getSetDate();

        Date endDate = null;

        try {
            endDate = (Date) formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long interval = 24 * 1000 * 60 * 60; // 1 hour in millis
        long endTime = endDate.getTime(); // create your endtime here, possibly using Calendar or Date
        long curTime = startDate.getTime();

        Log.d("end", String.valueOf(endDate));
        Log.d("cur", String.valueOf(startDate));

        while (curTime < endTime) {
            dates.add(new Date(curTime));
            curTime += interval;
        }

        String[] array_dates = new String[dates.size()];

        for (int i = 0; i < dates.size(); i++) {
            Date lDate = (Date) dates.get(i);
            array_dates[i] = formatter.format(lDate);
        }

        Log.d("Dates count", String.valueOf(dates.size()));
        Log.d("lat", lat);
        Log.d("lon", lon);

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

            sum = 0;
            Log.d("insertedID2", String.valueOf(insertedID));
            Cursor result = myDb.GetBiofix(String.valueOf(insertedID));
            Log.d("insectID", String.valueOf(result.getString(8)));
            base_temperature = myDb.GetInsectLowerThreshTemp(String.valueOf(result.getString(8)));
            for (int m = 0; m < dates.size(); m++) {
                String max = gw.getMaxTemp(weatherData[m]);
                String min = gw.getMinTemp(weatherData[m]);

                Log.d("weatherData Size", String.valueOf(weatherData.length));
                Log.d("weatherData[0]", String.valueOf(weatherData[0]));
                Log.d("Max", String.valueOf(max));
                Log.d("Min", String.valueOf(min));

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

            Log.d("Sum", String.valueOf(sum));
            myDb.SetDegreeDay(String.valueOf(insertedID), sum);

            Intent intent = new Intent(BioFix.this, BiofixList.class);
            intent.putExtra("orchard_key", orchardKey);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
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
