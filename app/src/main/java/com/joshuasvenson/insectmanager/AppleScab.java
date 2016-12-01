package com.joshuasvenson.insectmanager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by anaso_000 on 10/9/2016.
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
}
