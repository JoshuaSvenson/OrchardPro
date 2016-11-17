package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.joshuasvenson.insectmanager.Home.myDb;

public class SpraySchedule extends AppCompatActivity {;

    HashMap<String, List<String>> Risk_category;
    List<String> Risk_list;
    ExpandableListView Exp_list;
    MyExListAdapter RiskAdapter;

    String orchardKey;

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

        //calculateDegreeDays();

        Exp_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
             @Override
             public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                 Toast.makeText(getBaseContext(),
                         Risk_category.get(Risk_list.get(groupPosition)).get(childPosition)+" from category " + Risk_list.get(groupPosition) + " is selected",
                         Toast.LENGTH_LONG).show();
                 return false;
             }
         });

    }

    /*private void calculateDegreeDays(){
        String latitude;
        String longitude;

        String biofix_year;
        String biofix_month;
        String biofix_day;

        String baseTemp;
        double average;
        double degree_day;

        DateFormat formatter;

        latitude = myDb.GetOrchardLatitude(Integer.parseInt(orchardKey));
        longitude = myDb.GetOrchardLongitude(Integer.parseInt(orchardKey));

        String currentDate = myDb.GetSettingsDate();

        formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

        Date startDate = null;

        try {
            startDate = (Date) formatter.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        biofix_day = (String) android.text.format.DateFormat.format("dd", startDate);
        biofix_month = (String) android.text.format.DateFormat.format("MMM", startDate);
        biofix_year = (String) android.text.format.DateFormat.format("yyyy", startDate);

        Toast.makeText(SpraySchedule.this, "Latitude: " +latitude +
                " Longitude: " +longitude +
                " Current Day: " +biofix_year +
                " startDate: " +startDate +
                " currentDate: " +currentDate,Toast.LENGTH_LONG).show();
    }*/
}
