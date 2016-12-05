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

public class Calculations extends AppCompatActivity {

    double tree_row_volume;
    TextView TRV;
    Button button;

    TextView GPM;
    TextView GPM2;

    String gallons_minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculations);

        TRV = (TextView)findViewById(R.id.TreeRowVolume);
        button = (Button) findViewById(R.id.button1);
        GPM = (TextView) findViewById(R.id.GPM);
        GPM2 = (TextView) findViewById(R.id.GPM_nozzle);

        String orchard_key = getIntent().getExtras().get("orchard_key").toString();
        Cursor cursor = myDb.GetOrchardSettings(orchard_key);

        cursor.moveToFirst();

        tree_row_volume = CalcTreeRowVolume(Double.parseDouble(cursor.getString(7)),
                Double.parseDouble(cursor.getString(2)),
                Double.parseDouble(cursor.getString(6)),
                Double.parseDouble(cursor.getString(3)));
        TRV.setText(String.valueOf(tree_row_volume));

        GPM.setText("Gpm (gallons per minute) = " + gpm.getGpm() + " per side");

        GPM2.setText("Gpm (gallons per minute) = " + gpm.getGpm2() + " per nozzle");


        addListenerOnButton();

    }

    private void addListenerOnButton() {
        final Context context = this;

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Create new activity from the Orchard class
                Intent intent = new Intent(context, gpm.class);
                //Starts activity and opens up the Orchards page
                startActivity(intent);



            }

        });
    }

    double CalcTreeRowVolume(double trs, double crs, double ph, double density){
        double frpa = CalculateFeetOfRowPerAcre(trs);
        double cftrva = CalculateCubicFeetOfTreeRowVolumePerAcre(frpa, ph, crs);
        double trv = CalculateTreeRowVolumeGallonagePerAcre(cftrva, density);

        return trv;
    }

    //brs is between_row spacing (tree_row_spacing)
    double CalculateFeetOfRowPerAcre(double brs){
        double feet_of_row_per_acre;

        feet_of_row_per_acre = 43560/brs;
        return feet_of_row_per_acre;
    }

    //frpa is feet of row per acre
    //crps is cross row plant spread
    double CalculateCubicFeetOfTreeRowVolumePerAcre(double frpa, double plantHeight, double crps){
        double cubic_feet_of_tree_row_volume_per_acre;

        cubic_feet_of_tree_row_volume_per_acre = frpa*plantHeight*crps;
        return cubic_feet_of_tree_row_volume_per_acre;
    }

    //cftrva is cubic feet of tree row volume per acre
    double CalculateTreeRowVolumeGallonagePerAcre(double cftrva, double density){
        double tree_row_volume_gallonage_per_acre;

        tree_row_volume_gallonage_per_acre = (cftrva*density)/1000;
        return tree_row_volume_gallonage_per_acre;
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
