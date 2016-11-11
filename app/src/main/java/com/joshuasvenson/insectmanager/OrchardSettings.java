package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    Button SubmitButton;

    String orchardKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orchard_settings);

        orchardName = (TextView) findViewById((R.id.orchardName));
        orchardLat = (TextView) findViewById(R.id.orchardLatitude);
        orchardLong = (TextView) findViewById(R.id.orchardLongitude);
        orchardWeatherStation = (TextView) findViewById(R.id.orchardWeatherStation);
        orchardTRS = (TextView) findViewById(R.id.orchardTRS);
        orchardCRS = (TextView) findViewById(R.id.orchardCRS);
        orchardPH = (TextView) findViewById(R.id.orchardPlantHeight);
        orchardDensity = (TextView) findViewById(R.id.orchardDensityFactor);

        SubmitButton = (Button) findViewById(R.id.orchardSettingsSubmitButton);

        orchardKey = getIntent().getExtras().get("orchard_key").toString();

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

        addListenerOnButton();
    }

    public void addListenerOnButton() {
        final Context context = this;

        SubmitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                myDb.updateData(orchardKey,
                        orchardName.getText().toString(),
                        orchardLat.getText().toString(),
                        orchardLong.getText().toString(),
                        orchardWeatherStation.getText().toString(),
                        orchardTRS.getText().toString(),
                        orchardCRS.getText().toString(),
                        orchardPH.getText().toString(),
                        orchardDensity.getText().toString());

                finish();
            }
        });
    }
}

