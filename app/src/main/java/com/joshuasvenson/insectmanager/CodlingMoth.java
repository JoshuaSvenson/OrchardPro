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
Name: CodlingMoth
Description: This class provides the code for the CodlingMoth screen and activity of the app and
manages the button clicks and info to be displayehd in the textviews.
Layout File: codling_moth.xml
 */
public class CodlingMoth extends AppCompatActivity {

    TextView description;
    TextView biofix_info;
    TextView traps;
    TextView spray_timing;

    //Codling Moth is first insect in table
    String insectKey = "1";

    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    /*
    Name: onCreate
    Description: Creates activity
    Parameters: Bundle savedInstanceState
    Returns: void
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codling_moth);

        //Sets the viewpager so that sliding pictures will work
        viewPager = (ViewPager)findViewById(R.id.codling_moth_view_pager);
        adapter = new CustomSwipeAdapter(this, insectKey, "insect");
        viewPager.setAdapter(adapter);

        //The table that shows insect risk
        TableLayout table = (TableLayout) findViewById(R.id.codlingMothTable);

        //Get information to fill out insect risk table
        Cursor biofix_row_cursor = myDb.GetInsectBiofix(insectKey);
        Cursor degree_day_cursor = myDb.GetAllDegreeDaysForInsects(insectKey);
        int biofixCount = biofix_row_cursor.getCount();

        biofix_row_cursor.moveToFirst();
        degree_day_cursor.moveToFirst();

        //Adds an entry for each biofix. If one orchard has multiple biofixes, then there will be two
        //entries for that orchard.
        for(int i =0; i < biofixCount; i++){
            TableRow row = new TableRow(findViewById(R.id.codlingMothTableRow).getContext());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            TextView orchard = new TextView(findViewById(R.id.codlingMothDescription).getContext());
            TextView risk = new TextView(findViewById(R.id.codlingMothDescription).getContext());

            //Get the degree days for when insect is at risk
            double sprayDegreeDay = myDb.GetInsectSprayDay(Integer.parseInt(biofix_row_cursor.getString(8)));
            //get the current degree day accumulations
            double accumulatedDegreeDay = Double.parseDouble(degree_day_cursor.getString(1));

            //Add orchard name
            orchard.setText(myDb.GetOrchardName(Integer.parseInt(biofix_row_cursor.getString(9))) + " ");
            orchard.setTextColor(Color.parseColor("#000000"));

            //Risk is High if accumulated degree days is greater than the risk threshold
            if(accumulatedDegreeDay >= sprayDegreeDay){
                risk.setText("High" + " ");
                //Red
                risk.setTextColor(Color.parseColor("#BB0000"));
            }
            //Risk is Medium if accumulated degree days is within 100 of the risk threshold
            else if(accumulatedDegreeDay >= sprayDegreeDay - 100){
                risk.setText("Medium" + " ");
                //Yellow
                risk.setTextColor(Color.parseColor("#CCCC00"));
            }
            //Risk is Low if accumulated degree days is lower than the risk threshold minus 100
            else{
                risk.setText("Low" + " ");
                //Green
                risk.setTextColor(Color.parseColor("#00BB00"));
            }

            orchard.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.6f));
            orchard.setGravity(Gravity.CENTER);
            orchard.setBackgroundResource(R.color.backgroundColor);

            risk.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.4f));
            risk.setGravity(Gravity.CENTER);
            risk.setBackgroundResource(R.color.backgroundColor);

            //add view to the row
            row.addView(orchard);
            row.addView(risk);

            //add row to the table
            table.addView(row, i);

            //move to the next entry
            biofix_row_cursor.moveToNext();
            degree_day_cursor.moveToNext();
        }
        biofix_row_cursor.close();
        degree_day_cursor.close();

        //Set the textviews to their references in the corresponding xml file (codling_moth.xml)
        description = (TextView) findViewById(R.id.codlingMothDescription);
        biofix_info = (TextView) findViewById(R.id.codlingMothBiofix);
        traps = (TextView) findViewById(R.id.codlingMothTraps);
        spray_timing = (TextView) findViewById(R.id.codlingMothSprayTiming);

        //set text for the description textview of the activity
        description.setText("The adult codling moth is greyish with light grey and copper stripes on its wings. Its " +
                "wingspan is 17 mm on average. Codling moth larvae can be 1/2 - 3/4 inchs long with a pinkish body and " +
                "a brownish head. Codling moth eggs get laid on the surface of the fruit or leaves and, once hatched, " +
                "will chew an opening into the fruit to feed. In three to four weeks they will leave the fruit to pupate." +
                "\n\n"+
                "Damage: Feeding larvae will tunnel through the fruit to eat the proteinous seeds in the center. The " +
                "most visible sign of this will be a brown hole in the fruit and brown frass may be extruding from it. " +
                "These deep tunnels will eventually cause the fruit to rot.");

        //set text for the biofix textview of the activity
        biofix_info.setText("Codling moth biofix is set at the first sustained trap catch. A 'sustained' catch is " +
                "a continual period of moth activity. If one or two moths are trapped followed by a period of no " +
                "captures, then those early captures are ignored.");

        //set text for the traps textview of the activity
        traps.setText("Codling moth pheremone traps are used to determine peak flight times of the moth. These traps " +
                "attract male moths by containing pheremones female moths use to attract mates. These traps have " +
                "plastic tops and sticky bottoms and are hung by a wire. These traps should be hung at eye level with one for " +
                "every 10 acres of trees. Traps should be set out during pink stage of bud development and should " +
                "be replaced about every month.");

        //set text for the spray timing textview of the activity
        spray_timing.setText("Codling moth eggs will begin to hatch around 250 degree days (base 50" + (char) 0x00B0 +
                "F). It is at this time spraying should begin. If after the first spray more than 10 moths are being " +
                "caught per trap per week, a second spray may be necessary. Around 1000 degree days is when the " +
                "first generation of moth begin to fly. This should be the second biofix. Second generation egg " +
                "hatch occurs around 1300 degree days and is when the next round of spraying should occur.");
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
