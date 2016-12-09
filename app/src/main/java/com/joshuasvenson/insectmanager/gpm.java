package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by anaso_000 on 11/22/2016.
 */

/*
Name: gpm
Description: This class provides the code for the gpm screen and activity of the app and
manages the button clicks and info to be displayed.
Layout File: gpm.xml
 */
public class gpm extends AppCompatActivity {

    //Initializes edittexts in the xml file
    EditText gpa;
    EditText W;
    EditText mph;

    EditText GPA;
    EditText W2;
    EditText MPH;

    //Initializes the textview in screen
    TextView gpm;

    TextView GPM;

    //Initializes the button in screen
    Button Save;

    double gallons_acre;
    double spacing;
    double speed;

    double gallons_acre2;
    double spacing2;
    double speed2;

    static String gallons_minute2 = "0";

    static String  gallons_minute ="0";

    /*
    Name: onCreate
    Description: Creates activity
    Parameters: Bundle savedInstanceState
    Returns: void
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gpm);

        //Set the Edittexts  and textviews to their references in the corresponding xml file (content_main.xml)
        gpa = (EditText) findViewById(R.id.addOrchardForm_gpa);
        mph = (EditText) findViewById(R.id.addOrchardForm_mph);
        W = (EditText) findViewById(R.id.addOrchardForm_W);
        Save = (Button) findViewById(R.id.addOrchardForm_Submit);
        gpm = (TextView) findViewById(R.id.addOrchardForm_gpm);

        GPA = (EditText) findViewById(R.id.addOrchardForm_GPA);
        MPH = (EditText) findViewById(R.id.addOrchardForm_MPH);
        W2 = (EditText) findViewById(R.id.addOrchardForm_W2);
        GPM = (TextView) findViewById(R.id.addOrchardForm_GPM);

        //Add listener to the button
        addListenerOnButton();

    }

    /*
    Name: addListenerOnButton
    Description: Adds listeners on the button SAVE on the page and provides functionality when the button is clicked
    Parameters: None
    Returns: void
     */
    private void addListenerOnButton() {
        final Context context = this;

        //Set listener for the Save button
        Save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                gallons_acre = Double.parseDouble(gpa.getText().toString());
                spacing = Double.parseDouble(W.getText().toString());
                speed = Double.parseDouble(mph.getText().toString());
                gallons_minute = String.valueOf((spacing * gallons_acre * speed)/1000);

                gpm.setText(gallons_minute);

                gallons_acre2 = Double.parseDouble(GPA.getText().toString());
                spacing2 = Double.parseDouble(W2.getText().toString());
                speed2 = Double.parseDouble(MPH.getText().toString());
                gallons_minute2 = String.valueOf((spacing2 * gallons_acre2 * speed2)/5940);

                GPM.setText(gallons_minute2);

            }

        });
    }

    /*
    Name: getGpm
    Description: get Gpm value
    Parameters: None
    Returns: String, gpm value
     */
    public static String getGpm(){
        return gallons_minute;
    }

    /*
    Name: getGpm2
    Description: get Gpm value
    Parameters: None
    Returns: String, gpm value
     */
    public static String getGpm2(){
        return gallons_minute2;
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
