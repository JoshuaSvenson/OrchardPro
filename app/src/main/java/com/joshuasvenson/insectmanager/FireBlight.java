package com.joshuasvenson.insectmanager;

        import android.content.Context;
        import android.database.Cursor;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.AppCompatActivity;
        import android.view.Gravity;
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;

        import org.w3c.dom.Text;

        import static com.joshuasvenson.insectmanager.Home.myDb;

/**
 * Created by anaso_000 on 10/9/2016.
 */

public class FireBlight extends AppCompatActivity {

    TextView description;
    TextView biofix_info;
    TextView traps;
    TextView spray_timing;

    String diseaseKey = "1";

    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fire_blight);

        viewPager = (ViewPager)findViewById(R.id.fire_blight_view_pager);
        adapter = new CustomSwipeAdapter(this, diseaseKey, "disease");
        viewPager.setAdapter(adapter);

        description = (TextView) findViewById(R.id.fireBlightDescription);
        biofix_info = (TextView) findViewById(R.id.fireBlightBiofix);
        spray_timing = (TextView) findViewById(R.id.fireBlightSprayTiming);

        description.setText("One of the more devastating of the apple tree diseases, fire blight is a " +
                "bacterial disease that affects all parts of the tree and can lead to death. " +
                "\n\nSymptoms of fire blight include die back of branches, leaves and blossoms and " +
                "depressed areas on the bark that will be discolored and are, in fact, areas of the " +
                "branches that are dying.");

        biofix_info.setText("The Fire Blight bacterium is considered a complete epiphyte meaning it relies " +
                "on the apple blossom for growth only. Therefore, biofix is set at first blossom open. ");

        spray_timing.setText("Fire blight has a temperature base of 65" + (char) 0x00B0 +"F. After roughly 198 degree " +
                "hours have accumulated since biofix the first spray should be applied. Optimal bacteria development conditions " +
                "occur when temperatures are between 75 and 85" + (char) 0x00B0 +"F with sporadic rain or high humidity levels. " +
                "During these optimal conditions sprays should be applied in four or five day intervals. Spraying should continue " +
                "until late bloom is over.");

    }
}
