package com.joshuasvenson.insectmanager;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.joshuasvenson.insectmanager.Home.myDb;

public class OrchardSettings extends AppCompatActivity {

    TextView testLat;
    TextView testLong;
    TextView testTRS;
    TextView testCRS;
    TextView testPH;
    TextView testDensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orchard_settings);

        testLat = (TextView)findViewById(R.id.orchardLatitude);
        testLong = (TextView)findViewById(R.id.orchardLongitude);
        testTRS = (TextView)findViewById(R.id.orchardTRS);
        testCRS = (TextView)findViewById(R.id.orchardCRS);
        testPH = (TextView)findViewById(R.id.orchardPlantHeight);
        testDensity = (TextView)findViewById(R.id.orchardDensityFactor);

        String orchard_key = getIntent().getExtras().get("orchard_key").toString();
        Cursor cursor = myDb.GetOrchardSettings(orchard_key);

        cursor.moveToFirst();

        testLat.setText(String.valueOf(cursor.getString(4)));
        testLong.setText(String.valueOf(cursor.getString(5)));
        testTRS.setText(String.valueOf(cursor.getString(7)));
        testCRS.setText(String.valueOf(cursor.getString(2)));
        testPH.setText(String.valueOf(cursor.getString(6)));
        testDensity.setText(String.valueOf(cursor.getString(3)));
    }
}
