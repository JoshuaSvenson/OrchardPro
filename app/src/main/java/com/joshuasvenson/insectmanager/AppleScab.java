package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/*
Name: AppleScab
Description: This class provides the code for the Apple Scab tab ins the Diseases page
Layout File: apple_scab.xml
 */
public class AppleScab extends AppCompatActivity {

    TextView description;
    TextView biofix_info;
    TextView traps;
    TextView spray_timing;

    String diseaseKey = "2";

    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apple_scab);

        viewPager = (ViewPager)findViewById(R.id.apple_scab_view_pager);
        adapter = new CustomSwipeAdapter(this, diseaseKey, "disease");
        viewPager.setAdapter(adapter);

        description = (TextView) findViewById(R.id.appleScabDescription);
        biofix_info = (TextView) findViewById(R.id.appleScabBiofix);
        spray_timing = (TextView) findViewById(R.id.appleScabSprayTiming);

        description.setText("A very common disease and one of the most aesthetically damaging " +
                "diseases, its main symptoms includes leaf and fruit spots. Very susceptible trees" +
                " become defoliated by mid-summer, which weakens the trees over time." +
                " \n\nThe disease manifests as dull black or grey-brown lesions on the surface of tree leaves," +
                "buds or fruits. Lesions may also appear less frequently on the woody tissues of the tree. " +
                "Fruits and the undersides of leaves are especially susceptible. " +
                "The disease rarely kills its host, but can significantly reduce fruit yields and fruit quality.");

        biofix_info.setText("Management of apple scab on susceptible trees is focused on the prevention" +
                "of primary infection by ascospores in the spring. Early infection of trees may result " +
                "in poor fruit set, and will result in more secondary inoculum being produced throughout " +
                "the season. \n\nThe initial fungicide sprays are therefore timed to coincide with the spring " +
                "release of primary inoculum. Later sprays are often targeted at other fungal diseases, " +
                "in addition to scab, but also are effective against apple scab secondary inoculum. ");

        spray_timing.setText("Fungicides must be applied preventively to successfully manage apple scab. " +
                "Because spores are released so early in the growing season, fungicide sprays must begin when " +
                "the first green leaf tips emerge in spring. Sprays should be repeated until petal drop for " +
                "crabapple. If the tree is healthy and free of leaf spots at this point, further treatments " +
                "are unnecessary." +
                "\n\nIn mid-June, examine the leaves on your trees for scab lesions. Be very thorough," +
                "checking upper and lower leaf surfaces, leaves on the interior and exterior of the " +
                "canopy, leaves close to the ground and those higher in the tree. If you find no or " +
                "very few apple scab leaf spots, you need not spray fungicide again. " +
                "\n\nIf you find scab lesions, or if there are unsprayed trees in your neighborhood" +
                " with scab lesions, you should continue to spray, because the lesions on the leaves " +
                "will release more scab spores all summer long.");

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
