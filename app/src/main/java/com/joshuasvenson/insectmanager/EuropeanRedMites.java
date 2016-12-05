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

public class EuropeanRedMites extends AppCompatActivity {

    TextView description;
    TextView biofix_info;
    TextView monitoring;
    TextView spray_timing;

    String insectKey = "5";

    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.european_redmites);

        viewPager = (ViewPager)findViewById(R.id.european_red_mites_view_pager);
        adapter = new CustomSwipeAdapter(this, insectKey, "insect");
        viewPager.setAdapter(adapter);

        TableLayout table = (TableLayout) findViewById(R.id.europeanRedMitesTable);

        Cursor biofix_row_cursor = myDb.GetInsectBiofix(insectKey);
        Cursor degree_day_cursor = myDb.GetAllDegreeDaysForInsects(insectKey);
        int biofixCount = biofix_row_cursor.getCount();

        biofix_row_cursor.moveToFirst();
        degree_day_cursor.moveToFirst();

        for(int i =0; i < biofixCount; i++){
            TableRow row = new TableRow(findViewById(R.id.europeanRedMitesTableRow).getContext());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            TextView orchard = new TextView(findViewById(R.id.europeanRedMitesDescription).getContext());
            TextView risk = new TextView(findViewById(R.id.europeanRedMitesDescription).getContext());

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

        description = (TextView) findViewById(R.id.europeanRedMitesDescription);
        biofix_info = (TextView) findViewById(R.id.europeanRedMitesBiofix);
        monitoring = (TextView) findViewById(R.id.europeanRedMitesMonitoring);
        //spray_timing = (TextView) findViewById(R.id.europeanRedMitesSprayTiming);

        description.setText("Adult european red mites are very small with females being about 0.35 mm in length " +
                "and a red brick color. Males are typically 0.3 mm in length and yellow with a red tinge. Eggs are " +
                "red and are about 0.15 mm in length. " +
                "\n\n"+
                "Damage: European red mites feed on the leaves of apple trees. They feed on the contents of " +
                "the plant cells eventually causing the leaves to get small white spots. This can lead to bronzing " +
                "of the trees leaves. Their presence can also result in russeting of the fruit which is when the " +
                "fruit develops a brown, netlike condition on the skin.");

        biofix_info.setText("No biofix information has been found for European Red Mites");

        monitoring.setText("To monitor examine five leaves from each of four different limbs of a tree +" +
                "and determine the average number of european red mites per leaf. Before April 1 if there is an " +
                "average of at least five mites per leaf, then control measures should be taken. In April or May " +
                "maintain control if the average is greater than ten and later in the season  maintain control " +
                "if the average is greater than 15. Varieties such as Red Delicious are more likely to develop " +
                "larger mite populations.");

        /*spray_timing.setText("");*/

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
