package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import static com.joshuasvenson.insectmanager.Home.myDb;

/**
 * Created by anaso_000 on 10/9/2016.
 */

/*
Name: RosyAppleAphid
Description: This class provides the code for the Rosy Apple Aphids page in Insects page
Layout File: rosy_apple_aphid.xml
 */
public class RosyAppleAphid extends AppCompatActivity {

    TextView description;
    TextView biofix_info;
    TextView traps;
    TextView spray_timing;

    String insectKey = "3";

    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rosy_apple_aphid);

        viewPager = (ViewPager)findViewById(R.id.rosy_apple_aphid_view_pager);
        adapter = new CustomSwipeAdapter(this, insectKey, "insect");
        viewPager.setAdapter(adapter);

        TableLayout table = (TableLayout) findViewById(R.id.rosyAppleAphidTable);

        Cursor biofix_row_cursor = myDb.GetInsectBiofix(insectKey);
        Cursor degree_day_cursor = myDb.GetAllDegreeDaysForInsects(insectKey);
        int biofixCount = biofix_row_cursor.getCount();

        biofix_row_cursor.moveToFirst();
        degree_day_cursor.moveToFirst();

        for(int i =0; i < biofixCount; i++){
            TableRow row = new TableRow(findViewById(R.id.rosyAppleAphidTableRow).getContext());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            TextView orchard = new TextView(findViewById(R.id.rosyAppleAphidDescription).getContext());
            TextView risk = new TextView(findViewById(R.id.rosyAppleAphidDescription).getContext());

            double sprayDegreeDay = myDb.GetInsectSprayDay(Integer.parseInt(biofix_row_cursor.getString(8)));
            double accumulatedDegreeDay = Double.parseDouble(degree_day_cursor.getString(1));

            orchard.setText(myDb.GetOrchardName(Integer.parseInt(biofix_row_cursor.getString(9))) + " ");
            orchard.setTextColor(Color.parseColor("#000000"));

            if(accumulatedDegreeDay >= sprayDegreeDay){
                risk.setText("High" + " ");
                risk.setTextColor(Color.parseColor("#BB0000"));
            }
            else if(accumulatedDegreeDay >= sprayDegreeDay - 100){
                risk.setText("Medium" + " ");
                risk.setTextColor(Color.parseColor("#CCCC00"));
            }
            else{
                risk.setText("Low" + " ");
                risk.setTextColor(Color.parseColor("#00BB00"));
            }

            orchard.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.6f));
            orchard.setGravity(Gravity.CENTER);
            orchard.setBackgroundResource(R.color.backgroundColor);

            risk.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.4f));
            risk.setGravity(Gravity.CENTER);
            risk.setBackgroundResource(R.color.backgroundColor);

            row.addView(orchard);
            row.addView(risk);

            table.addView(row, i);

            biofix_row_cursor.moveToNext();
            degree_day_cursor.moveToNext();
        }
        biofix_row_cursor.close();
        degree_day_cursor.close();

        description = (TextView) findViewById(R.id.rosyAppleAphidDescription);
        biofix_info = (TextView) findViewById(R.id.rosyAppleAphidBiofix);
        traps = (TextView) findViewById(R.id.rosyAppleAphidTraps);
        spray_timing = (TextView) findViewById(R.id.rosyAppleAphidSprayTiming);

        description.setText("Adult rosy apple aphids can be either winged or wingless. Wingless aphids are a rosy-purple color " +
                "and the winged adults are brownish-green to black. They are roughly 2-3 mm in length. " +
                "Its eggs begin as a pale green when lain and eventually turn to a shiny black color. " +
                "They look almost indistinguishable from other aphid eggs." +
                "\n\n"+
                "Damage: The rosy apple aphids feed on the leaves and shoots of trees causing the leaves to curl." +
                "The aphids' saliva releases toxins into the tree which can cause small, misshapen apples and irregular " +
                "shoot growth. This can be especially damaging to young trees where the tree's shape can be permanently altered.");

        biofix_info.setText("The first rosy apple aphids will emerge from winter eggs so biofix can be set as " +
                "January 1.");

        traps.setText("The best way to determine if the rosy apple aphid is in your orchard is to check the leaves " +
                "of apple trees. At early pink stage you should monitor 10-20 trees. Check for curled leaves or visible " +
                "signs of the insect. If more than one aphid-infested cluster is discovered per tree then an insecticide " +
                "treatment is recommended. Apple varieties such as Rome, York Imperial, Golden, and Stayman are " +
                "good places to look.");

        spray_timing.setText("Rosy Apple APhid eggs will begin to hatch between 100 and 180 degree days (base 40" + (char) 0x00B0 +
                "F) with 50% emergence around 130 degree days. Reproduction begins around 230 degree days. " +
                "Spraying is usually most effective between 180 and 230 degree days." +
                "\n\n" +
                "By late June/early July most rosy apple aphids have left apple trees for other weed hosts so control " +
                "is not necessary during the summer. Towards the beginning of fall adult aphids will return to apple " +
                "trees again and monitoring should commence again.");
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
