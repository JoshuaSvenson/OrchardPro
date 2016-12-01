package com.joshuasvenson.insectmanager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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

        biofix_info.setText("");

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
}
