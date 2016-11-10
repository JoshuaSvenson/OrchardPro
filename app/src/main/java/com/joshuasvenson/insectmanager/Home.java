package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/*
Name: Home
Description: This class provides the code for the homepage of the app and manages the button clicks
Layout File: content_main.xml
 */
public class Home extends AppCompatActivity {

    //Initialize the four buttons on the homepage
    Button OrchardsButton;
    Button InsectsButton;
    Button DiseasesButton;
    Button WeatherButton;

    /*
    Name: onCreate
    Description:
    Parameters:
    Returns:
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //This method adds listeners on all of the buttons
        addListenerOnButton();
    }

    /*
    Name: onCreateOptionsMenu
    Description:
    Parameters:
    Returns:
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
    Name: onOptionsItemSelected
    Description:
    Parameters:
    Returns:
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

        return super.onOptionsItemSelected(item);
    }

    /*
    Name: addListenerOnButton
    Description: Adds listeners on all of the buttons on the page and provides functionality when each button is clicked
    Parameters: None
    Returns: void
     */
    public void addListenerOnButton(){
        final Context context = this;

        //Set the buttons to their references in the corresponding xml file (content_main.xml)
        OrchardsButton = (Button) findViewById(R.id.OrchardButton);
        InsectsButton = (Button) findViewById(R.id.InsectsButton);
        DiseasesButton = (Button) findViewById(R.id.DiseasesButton);
        WeatherButton = (Button) findViewById(R.id.Weather_button);

        //Set listener for the Orchards button
        OrchardsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Create new activity from the Orchard class
                Intent intent = new Intent(context, Orchard.class);
                //Starts activity and opens up the Orchards page
                startActivity(intent);
            }

        });

        //Set the listener for the Insects button
        InsectsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new activity from the Insects class
                Intent intent = new Intent(context, Insects.class);
                //Starts activity and opens up the Insects page
                startActivity(intent);
            }
        });

        //Set the listener for the Diseases button
        DiseasesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new activity from the Diseases class
                Intent intent = new Intent(context,Diseases.class);
                //Starts activity and opens up the Diseases page
                startActivity(intent);
            }
        });

        //Set the listener for the Weather button
        WeatherButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new activity from the Weather1 class
                Intent intent = new Intent(context,History.class);
                //Starts activity and opens up the Weather page
                startActivity(intent);
            }
        });
    }
}
