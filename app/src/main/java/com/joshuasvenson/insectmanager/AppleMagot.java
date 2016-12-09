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
Name: AppleMagot
Description: This class provides the code for the AppleMagot screen and activity of the app and
manages the button clicks and info to be displayehd in the textviews.
Layout File: apple_magot.xml
 */
public class AppleMagot extends AppCompatActivity {

    //Initializes Textviews of activity
    TextView description;
    TextView biofix_info;
    TextView traps;
    TextView spray_timing;

    String insectKey = "2";

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
        setContentView(R.layout.apple_magot);

        viewPager = (ViewPager)findViewById(R.id.apple_maggot_view_pager);
        adapter = new CustomSwipeAdapter(this, insectKey, "insect");
        viewPager.setAdapter(adapter);

        TableLayout table = (TableLayout) findViewById(R.id.appleMagotTable);

        Cursor biofix_row_cursor = myDb.GetInsectBiofix(insectKey);
        Cursor degree_day_cursor = myDb.GetAllDegreeDaysForInsects(insectKey);
        int biofixCount = biofix_row_cursor.getCount();

        biofix_row_cursor.moveToFirst();
        degree_day_cursor.moveToFirst();

        for(int i =0; i < biofixCount; i++){
            TableRow row = new TableRow(findViewById(R.id.appleMagotTableRow).getContext());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            TextView orchard = new TextView(findViewById(R.id.appleMaggotDescription).getContext());
            TextView risk = new TextView(findViewById(R.id.appleMaggotDescription).getContext());

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

        //Set the textviews to their references in the corresponding xml file (apple_magot.xml)
        description = (TextView) findViewById(R.id.appleMaggotDescription);
        biofix_info = (TextView) findViewById(R.id.appleMaggotBiofix);
        traps = (TextView) findViewById(R.id.appleMaggotTraps);
        spray_timing = (TextView) findViewById(R.id.appleMaggotSprayTiming);

        //set text for the description textview of the activity
        description.setText("The apple maggot larva is a typical fly larva which looks like a creamy white cylindrical " +
        "maggot about 10mm long. Adult apple maggots are slightly smaller than house flys and are black with white bands. " +
        "Apple maggot eggs get laid underneath the skin of the fruit where the larvae hatch and develop inside. " +
        "\n\n"+
        "Damage: Feeding larvae in the fruit create tunnels which turn brown and rot. This may leave the fruit " +
        "misshapen and cause the fruit to drop prematurely. Small dimples in the fruit may be an indication " +
        "of eggs laid underneath the skin.");

        //set text for the biofix_info textview in the activity
        biofix_info.setText("Apple maggot pupae overwinter underground and will energe as adults the following summer. " +
        "As a result, Biofix is set as January 1.");

        //set text for the traps textview in the activity
        traps.setText("An apple maggot trap consists of a red sphere coated with TangleFoot. The sticky TangleFoot " +
        "on the red sphere is designed to trap adult females attemping to lay eggs. These traps should be placed " +
        "in the tree canopy just as trees finish blossoming. Traps should be placed high in the brightest " +
        "areas of the tree with one trap for every 100-150 apples.");

        //set text for the spray_timing textview in the activity
        spray_timing.setText("Spraying should begin within a few days of an apple maggot being caught in the trap " +
                " or when apple maggots begin emerging from the ground around 900 degree days " +
                "(base 50" + (char) 0x00B0 + "F). Check pesticide label for spray interval, but generally " +
                "reapplication will be needed every 7 to 10 days. Continue spraying as long as apple maggots are " +
                "being caught in traps.");

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