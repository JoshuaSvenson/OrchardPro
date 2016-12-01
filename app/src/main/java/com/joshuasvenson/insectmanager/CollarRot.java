package com.joshuasvenson.insectmanager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
}