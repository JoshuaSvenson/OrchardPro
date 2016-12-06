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

public class BlackRot extends AppCompatActivity {

    TextView description;
    TextView biofix_info;
    TextView traps;
    TextView spray_timing;

    String diseaseKey = "3";

    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.black_rot);

        viewPager = (ViewPager)findViewById(R.id.black_rot_view_pager);
        adapter = new CustomSwipeAdapter(this, diseaseKey, "disease");
        viewPager.setAdapter(adapter);

        description = (TextView) findViewById(R.id.blackRotDescription);
        biofix_info = (TextView) findViewById(R.id.blackRotBiofix);
        spray_timing = (TextView) findViewById(R.id.blackRotSprayTiming);

        description.setText("Black rot apple disease can appear in one or a combination of three different " +
                "forms: black fruit rot, frogeye leaf spot, and black rot limb canker. Black rot is a " +
                "disease of apples that infects fruit, leaves and bark caused by the fungus Botryosphaeria " +
                "obtusa. Begin checking your apple trees for signs of infection about a week after the petals " +
                "fall from your apple blossoms. \n\nEarly symptoms are often limited to leaf symptoms such as purple " +
                "spots on upper leaf surfaces. As these spots age, the margins remain purple, but the centers dry " +
                "out and turn yellow to brown. Over time, the spots expand and heavily infected leaves drop from the " +
                "tree. Infected branches or limbs will show characteristic red-brown sunken areas that expand each year.");

        biofix_info.setText("To control this disease, remove dead material from the planting. Prune out dead or " +
                "diseased branches, and pick all mummy fruits remaining on the trees, as these are sources of " +
                "spores for future infections. All infected plant parts should be burned, buried, included it " +
                "in household trash, or sent to a municipal composting site. Be sure to remove the stumps of " +
                "any apple trees you cut down, as dead stumps can be a source of spores.\n" +
                "\n" + "All winter pruning, of healthy or dead tissue, must be completed during freezing weather, " +
                "as the fungus is not active until spring. Pruning cuts made in winter will have dried out and " +
                "will not be susceptible to the disease by the time black rot spores are available to infect them.");

        spray_timing.setText("Treating black rot on apple trees starts with sanitation. Because fungal " +
                "spores overwinter on fallen leaves, mummified fruits, dead bark and cankers, it’s " +
                "important to keep all the fallen debris and dead fruit cleaned up and away from the tree." +
                "\n\nDuring the winter, check for red cankers and remove them by cutting them out or pruning " +
                "away the affected limbs at least six inches beyond the wound. Destroy all infected " +
                "tissue immediately and keep a watchful eye out for new signs of infection.\n\nFungicide sprays" +
                "are typically unnecessary for black rot management. Use fungicides only if the " +
                "disease has persisted after cultural control practices have been implemented. \n\nCaptan and " +
                "fungicides containing a strobulurin (FRAC Group 11 Fungicides) as an active ingredient " +
                "are effective controlling black rot on fruit. Management programs based on sanitation to " +
                "reduce inoculum levels in the orchard are the primary means of control. Captan and sulfur " +
                "products are labeled for control of both scab and black rot, so a scab spray program " +
                "including these chemicals may help prevent the frog-eye leafspot of black rot, as well " +
                "as the infection of fruit. These sprays will not control or prevent infection of branches.");


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
