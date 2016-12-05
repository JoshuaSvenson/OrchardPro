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

public class CollarRot extends AppCompatActivity {

    TextView description;
    TextView symptoms_info;
    TextView traps;
    TextView preventing_info;
    TextView treating_info;

    String diseaseKey = "4";

    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collar_rot);

        viewPager = (ViewPager) findViewById(R.id.collar_rot_view_pager);
        adapter = new CustomSwipeAdapter(this, diseaseKey, "disease");
        viewPager.setAdapter(adapter);

        description = (TextView) findViewById(R.id.collarRotDescription);
        symptoms_info = (TextView) findViewById(R.id.collarRotSymptoms);
        preventing_info = (TextView) findViewById(R.id.collarRotPreventing);
        treating_info = (TextView) findViewById(R.id.collarRotTreating);

        description.setText("Collar rot is a fungal disease that begins at the tree union. Over time, the " +
                "fungus will girdle the trunk, which prevents important nutrients and water from moving " +
                "into the vascular system of the plant. The causal agent is a water mold named Phytophthora. " +
                "Treating collar rot starts with creating a well-drained planting site and watching young trees " +
                "carefully for any signs of disease. Reddish leaves in late summer may be the first identification " +
                "of collar rot. Trees may then develop poor twig growth, small fruit and smaller, discolored leaves.");

        symptoms_info.setText("Apple trees with collar rot decline at the same rate as the fungus spreads " +
                "through their root or collar tissues. The disease advances most quickly during warm, wet " +
                "spring weather. Initial signs of infection include wilted, early-dropping yellowed or purple " +
                "spring foliage, sparse, undersized fruit and stunted growth. Peeling back the darkened crown " +
                "bark of trees with advanced collar rot reveals wood discolored from healthy, pale green to " +
                "rusty brown. Fatalities are highest in young apple trees, because the fungi so rapidly engulf " +
                "their small root systems.");

        preventing_info.setText("Preventing apple collar rot begins with your choice of orchard site and rootstocks. " +
                "Give your trees a location with well-draining, loose soil where water can’t collect around " +
                "their crowns. Alternatively, plant them on raised, drainage-improving berms. Watering more " +
                "frequently, but for shorter periods, provides adequate water without saturating the soil. " +
                "Mefenoxam-fungicide soil drench applied immediately after planting, and every spring and " +
                "fall until the trees begin fruiting, discourages infection. Always apply mefenoxam according " +
                "to the manufacturer's specifications. Finally, use apple cultivars grafted to Phytophthora-resistant " +
                "rootstocks, such as M 9 or M 26.");

        treating_info.setText("Apples with Phytophthora damage to less than one-half of their collars may " +
                "recover if you cut back the darkened crown bark in late spring. Remove the soil from the " +
                "tree's base to the top of the largest roots and dispose of it. Leave the affected collar " +
                "tissue exposed; the air may dry it enough to heal. \n\nTreat with the systemic fungicides Ridomil, " +
                "Subdue or Aliette. Contact your county agent for new registrations and rates.");

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