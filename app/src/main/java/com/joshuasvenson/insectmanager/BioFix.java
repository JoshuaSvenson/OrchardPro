package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.joshuasvenson.insectmanager.Orchard.list_adapter;
import static com.joshuasvenson.insectmanager.Orchard.mChildRef;
import static com.joshuasvenson.insectmanager.Home.myDb;


public class BioFix extends AppCompatActivity {

    Spinner insectSpinner;
    Button submitButton;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_fix);

        //addItemsOnOrchardSpinner();

        addItemsOnInsectSpinner();

        addListenerOnButton();
    }

    /*public void addItemsOnOrchardSpinner() {
        final Context context = this;

        orchardSpinner = (Spinner) findViewById(R.id.biofix_orchard_spinner);

        List<String> list = new ArrayList<String>();

        Cursor cursor = myDb.query_names();
        cursor.moveToFirst();

        for(int i=0; i<cursor.getCount();i++){
            list.add(String.valueOf(cursor.getString(0)));

            if(i!=cursor.getCount()-1)
                cursor.moveToNext();
        }

        cursor.close();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orchardSpinner.setAdapter(dataAdapter);
    }*/

    public void addItemsOnInsectSpinner() {
        final Context context = this;

        insectSpinner = (Spinner) findViewById(R.id.biofix_insect_spinner);

        List<String> insect_list = new ArrayList<String>();

        Cursor cursor = myDb.GetInsectNames();
        cursor.moveToFirst();
        //insect_list.add(String.valueOf(cursor.getCount()));
        for(int i=0; i<cursor.getCount();i++){
            insect_list.add(String.valueOf(cursor.getString(0)));

            if(i!=cursor.getCount()-1)
                cursor.moveToNext();
        }

        cursor.close();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, insect_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        insectSpinner.setAdapter(dataAdapter);
    }

    public void addListenerOnButton(){
        final Context context = this;

        submitButton = (Button) findViewById(R.id.biofix_submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String orchardKey = getIntent().getExtras().getString("orchard_key");

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

                boolean isInserted = myDb.createBiofix(biofixDayFormatted, biofixMonthFormatted,
                        biofixYearFormatted, 0, biofixDayFormatted, biofixMonthFormatted,
                        biofixYearFormatted, Integer.parseInt(orchardKey), insectID);
                if(isInserted == true){
                    Toast.makeText(BioFix.this, "Data Inserted "
                            + biofixMonthFormatted+ " " +biofixDayFormatted
                            + " " + biofixYearFormatted +" | " + biofixMonthFormatted + " " +
                            biofixDayFormatted + " " + biofixYearFormatted,Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(BioFix.this, "Error Inserting",Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(context, BiofixList.class);
                intent.putExtra("orchard_key", orchardKey);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        });
    }
}
