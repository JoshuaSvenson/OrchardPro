package com.joshuasvenson.insectmanager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by anaso_000 on 10/9/2016.
 */

public class PowderyMildew extends AppCompatActivity {

    TextView description;
    TextView biofix_info;
    TextView traps;
    TextView spray_timing;

    String diseaseKey = "5";

    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.powdery_mildew);

        viewPager = (ViewPager)findViewById(R.id.powdery_mildew_view_pager);
        adapter = new CustomSwipeAdapter(this, diseaseKey, "disease");
        viewPager.setAdapter(adapter);

        description = (TextView) findViewById(R.id.pMildewDescription);
        biofix_info = (TextView) findViewById(R.id.pMildewBiofix);
        spray_timing = (TextView) findViewById(R.id.pMildewSprayTiming);

        description.setText("Powdery mildew of apple occurs in all apple-producing regions of the world.  " +
                "The disease causes economic damage by reducing tree vigor, flower bud production, and fruit " +
                "quality.");

        biofix_info.setText("If infected, white-appearing shoots are noticed early in the season, during early " +
                "spring, gardeners should prune them. Disinfecting your shears after pruning and destroying " +
                "all infected plant parts helps prevent spreading the disease to other plants. Cultural " +
                "conditions can also enhance the chance of powdery mildew; dampness, poor circulation, " +
                "excessive shade and high humidity all lead to higher infection rates. Planting trees in " +
                "sunny areas and avoiding excessive use of fertilizer helps avoid infection. Young trees are " +
                "least resistant to the disease. ");

        spray_timing.setText("Secondary infections and fruit infections can be controlled by foliar fungicide " +
                "applications.  In commercial orchards, fungicides are almost always used to control mildew, as " +
                "well as other apple diseases.  Fungicides are usually applied at 7- to 10-day intervals from " +
                "the tight-cluster stage until terminal shoot growth ends (about midsummer).  This ensures that " +
                "fungicide application coincides with rapid leaf development and the post-bloom period, and that " +
                "the new growth does not remain unprotected for long.  For highly susceptible cultivars, this " +
                "could mean as many as 18 sprays. \n\nFailure to include pre-bloom sprays is one of the most common " +
                "mistakes growers make in mildew management.  When P. leucotricha resumes growth in spring, large " +
                "numbers of conidia are produced in uncontrolled secondary cycles.  These asexual spores infect " +
                "healthy flower and shoot buds, which serve as the primary inoculum source next year.  Control is " +
                "difficult to achieve during the growing season if it has been neglected early on.  Growers may be " +
                "tempted to relax spray programs during dry conditions when other apple diseases cannot develop, but " +
                "mildew thrives in dry weather and protection needs to be maintained.");

    }
}
