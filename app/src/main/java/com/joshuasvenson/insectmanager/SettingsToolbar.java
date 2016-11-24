package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import static com.joshuasvenson.insectmanager.Home.myDb;

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
}
