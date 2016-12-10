package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by anaso_000 on 10/9/2016.
 */

/*
Name: PowderyMildew
Description: This class provides the code for the Powdery Mildew page in the Diseases page
Layout File: powdery_mildew.xml
 */
public class PowderyMildew extends AppCompatActivity {

    TextView description;
    TextView biofix_info;
    TextView traps;
    TextView spray_timing;

    String diseaseKey = "5";

    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.powdery_mildew);

        viewPager = (ViewPager)findViewById(R.id.powdery_mildew_view_pager);
        adapter = new CustomSwipeAdapter(this, diseaseKey, "disease");
        viewPager.setAdapter(adapter);

        description = (TextView) findViewById(R.id.pMildewDescription);
        biofix_info = (TextView) findViewById(R.id.pMildewBiofix);
        spray_timing = (TextView) findViewById(R.id.pMildewSprayTiming);

        description.setText("Powdery mildew of apple occurs in all apple-producing regions of the world.  " +
                "The disease causes economic damage by reducing tree vigor, flower bud production, and fruit " +
                "quality.");

        biofix_info.setText("If infected, white-appearing shoots are noticed early in the season, during early " +
                "spring, gardeners should prune them. Disinfecting your shears after pruning and destroying " +
                "all infected plant parts helps prevent spreading the disease to other plants. Cultural " +
                "conditions can also enhance the chance of powdery mildew; dampness, poor circulation, " +
                "excessive shade and high humidity all lead to higher infection rates. Planting trees in " +
                "sunny areas and avoiding excessive use of fertilizer helps avoid infection. Young trees are " +
                "least resistant to the disease. ");

        spray_timing.setText("Secondary infections and fruit infections can be controlled by foliar fungicide " +
                "applications.  In commercial orchards, fungicides are almost always used to control mildew, as " +
                "well as other apple diseases.  Fungicides are usually applied at 7- to 10-day intervals from " +
                "the tight-cluster stage until terminal shoot growth ends (about midsummer).  This ensures that " +
                "fungicide application coincides with rapid leaf development and the post-bloom period, and that " +
                "the new growth does not remain unprotected for long.  For highly susceptible cultivars, this " +
                "could mean as many as 18 sprays. \n\nFailure to include pre-bloom sprays is one of the most common " +
                "mistakes growers make in mildew management.  When P. leucotricha resumes growth in spring, large " +
                "numbers of conidia are produced in uncontrolled secondary cycles.  These asexual spores infect " +
                "healthy flower and shoot buds, which serve as the primary inoculum source next year.  Control is " +
                "difficult to achieve during the growing season if it has been neglected early on.  Growers may be " +
                "tempted to relax spray programs during dry conditions when other apple diseases cannot develop, but " +
                "mildew thrives in dry weather and protection needs to be maintained.");

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
