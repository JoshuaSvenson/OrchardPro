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

public class FireBlight extends AppCompatActivity {

    TextView description;
    TextView biofix_info;
    TextView traps;
    TextView spray_timing;

    String diseaseKey = "1";

    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fire_blight);

        viewPager = (ViewPager)findViewById(R.id.fire_blight_view_pager);
        adapter = new CustomSwipeAdapter(this, diseaseKey, "disease");
        viewPager.setAdapter(adapter);

        description = (TextView) findViewById(R.id.fireBlightDescription);
        biofix_info = (TextView) findViewById(R.id.fireBlightBiofix);
        spray_timing = (TextView) findViewById(R.id.fireBlightSprayTiming);

        description.setText("One of the more devastating of the apple tree diseases, fire blight is a " +
                "bacterial disease that affects all parts of the tree and can lead to death. " +
                "\n\nSymptoms of fire blight include die back of branches, leaves and blossoms and " +
                "depressed areas on the bark that will be discolored and are, in fact, areas of the " +
                "branches that are dying.");

        biofix_info.setText("The Fire Blight bacterium is considered a complete epiphyte meaning it relies " +
                "on the apple blossom for growth only. Therefore, biofix is set at first blossom open. ");

        spray_timing.setText("Fire blight has a temperature base of 65" + (char) 0x00B0 +"F. After roughly 198 degree " +
                "hours have accumulated since biofix the first spray should be applied. Optimal bacteria development conditions " +
                "occur when temperatures are between 75 and 85" + (char) 0x00B0 +"F with sporadic rain or high humidity levels. " +
                "During these optimal conditions sprays should be applied in four or five day intervals. Spraying should continue " +
                "until late bloom is over.");

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
