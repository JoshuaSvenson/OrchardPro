package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import static com.joshuasvenson.insectmanager.Orchard.myDb;

public class AllensOrchard extends AppCompatActivity {
    Button deleteButton;

    Button SprayScheduleButton;
    Button CalculationsButton;
    Button BiofixDataButton;
    Button WeatherButton;
    Button SettingsButton;

    TextView orchardName;

    String orchard_key;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allens_orchard);

        orchardName = (TextView)findViewById(R.id.ScreenTitle);

        name = getIntent().getExtras().get("index").toString();
        Cursor cursor = myDb.query_row(name);
        cursor.moveToFirst();

        orchard_key = cursor.getString(0);
        orchardName.setText(String.valueOf(cursor.getString(1)));

        cursor.close();

        addListenerOnButton();
    }

    public void addListenerOnButton() {
        final Context context = this;

        SprayScheduleButton = (Button) findViewById(R.id.SprayScheduleButton);
        CalculationsButton = (Button) findViewById(R.id.OrchardCalculationsButton);
        BiofixDataButton = (Button) findViewById(R.id.BiofixDataButton);
        WeatherButton = (Button) findViewById(R.id.OrchardWeatherButton);
        SettingsButton = (Button) findViewById(R.id.OrchardSettingsButton);
        deleteButton = (Button) findViewById(R.id.DeleteButton);

        SprayScheduleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, SpraySchedule.class);
                intent.putExtra("orchard_key", orchard_key);
                startActivity(intent);
            }

        });

        CalculationsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Calculations.class);
                intent.putExtra("orchard_key", orchard_key);
                startActivity(intent);
            }

        });

        BiofixDataButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, BiofixList.class);
                intent.putExtra("orchard_key", orchard_key);
                startActivity(intent);
            }

        });

        WeatherButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, OrchardWeather.class);
                intent.putExtra("orchard_key", orchard_key);
                startActivity(intent);
            }

        });

        SettingsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, OrchardSettings.class);
                intent.putExtra("orchard_key", orchard_key);
                startActivity(intent);
            }

        });

        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                myDb.deleteOrchardData(orchard_key);

                Intent intent = new Intent(context, Orchard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        });
    }
}

