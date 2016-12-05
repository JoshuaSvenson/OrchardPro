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

