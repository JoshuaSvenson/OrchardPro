package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import static com.joshuasvenson.insectmanager.Home.myDb;

/*
Name: SettingsToolbar
Description: This class provides the code for the Settings page where users can update the date the application thinks it is
Layout File: settings_toolbar.xml
 */
public class SettingsToolbar extends AppCompatActivity {

    TextView setDate;
    DatePicker setDatePicker;
    Button setDateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_toolbar);

        setDate = (TextView) findViewById(R.id.settingsCurrentSetDate);
        setDatePicker = (DatePicker) findViewById(R.id.settingsDatePicker);
        setDateButton = (Button) findViewById(R.id.settingsSetDateButton);

        Cursor date = myDb.GetSettingsDate();
        date.moveToFirst();
        String day = String.valueOf(date.getString(0));
        String month = String.valueOf(date.getString(1));
        String year = String.valueOf(date.getString(2));
        setDate.setText("Current Set Date (MM/DD/YYYY): " + month + "/" + day + "/" + year);

        addListenerOnButton();
    }

    public void addListenerOnButton(){
        final Context context = this;

        setDateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                /*Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, setDatePicker.getYear());
                cal.set(Calendar.MONTH, setDatePicker.getMonth());
                cal.set(Calendar.DAY_OF_MONTH, setDatePicker.getDayOfMonth());
                Date date = cal.getTime();*/

                int day = setDatePicker.getDayOfMonth();
                int month = setDatePicker.getMonth() + 1;
                int year = setDatePicker.getYear();

                DecimalFormat two_digit_formatter = new DecimalFormat("00");
                String dayFormatted = two_digit_formatter.format(day);
                String monthFormatted = two_digit_formatter.format(month);
                DecimalFormat four_digit_formatter = new DecimalFormat("0000");
                String yearFormatted = four_digit_formatter.format(year);

                boolean isInserted = myDb.SetSettingsDate(dayFormatted, monthFormatted, yearFormatted);
                if(isInserted == true){
                    Toast.makeText(SettingsToolbar.this, "Data Inserted " +monthFormatted
                            + "/" + dayFormatted + "/" + yearFormatted
                            ,Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(SettingsToolbar.this, "Error Inserting",Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(context, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        });
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
