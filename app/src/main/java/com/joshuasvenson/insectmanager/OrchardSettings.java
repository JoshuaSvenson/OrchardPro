package com.joshuasvenson.insectmanager;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.joshuasvenson.insectmanager.Home.myDb;

public class OrchardSettings extends AppCompatActivity {

    TextView orchardName;
    TextView orchardLat;
    TextView orchardLong;
    TextView orchardWeatherStation;
    TextView orchardTRS;
    TextView orchardCRS;
    TextView orchardPH;
    TextView orchardDensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orchard_settings);

        orchardName = (TextView)findViewById((R.id.orchardName));
        orchardLat = (TextView)findViewById(R.id.orchardLatitude);
        orchardLong = (TextView)findViewById(R.id.orchardLongitude);
        orchardWeatherStation = (TextView)findViewById(R.id.orchardWeatherStation);
        orchardTRS = (TextView)findViewById(R.id.orchardTRS);
        orchardCRS = (TextView)findViewById(R.id.orchardCRS);
        orchardPH = (TextView)findViewById(R.id.orchardPlantHeight);
        orchardDensity = (TextView)findViewById(R.id.orchardDensityFactor);

        String orchard_key = getIntent().getExtras().get("orchard_key").toString();
        Cursor cursor = myDb.GetOrchardSettings(orchard_key);

        cursor.moveToFirst();

        orchardName.setText(cursor.getString(1));
        orchardLat.setText(String.valueOf(cursor.getString(4)));
        orchardLong.setText(String.valueOf(cursor.getString(5)));
        orchardWeatherStation.setText(cursor.getString(8));
        orchardTRS.setText(String.valueOf(cursor.getString(7)));
        orchardCRS.setText(String.valueOf(cursor.getString(2)));
        orchardPH.setText(String.valueOf(cursor.getString(6)));
        orchardDensity.setText(String.valueOf(cursor.getString(3)));
    }
}
