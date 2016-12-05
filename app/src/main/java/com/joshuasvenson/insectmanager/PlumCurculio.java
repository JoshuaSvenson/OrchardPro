package com.joshuasvenson.insectmanager;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import static com.joshuasvenson.insectmanager.Home.myDb;

/**
 * Created by anaso_000 on 10/9/2016.
 */

public class PlumCurculio extends AppCompatActivity {

    TextView description;
    TextView biofix_info;
    TextView traps;
    TextView spray_timing;

    String insectKey = "4";

    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plum_curculio);

        viewPager = (ViewPager)findViewById(R.id.plum_curculio_view_pager);
        adapter = new CustomSwipeAdapter(this, insectKey, "insect");
        viewPager.setAdapter(adapter);

        TableLayout table = (TableLayout) findViewById(R.id.plumCurculioTable);

        Cursor biofix_row_cursor = myDb.GetInsectBiofix(insectKey);
        Cursor degree_day_cursor = myDb.GetAllDegreeDaysForInsects(insectKey);
        int biofixCount = biofix_row_cursor.getCount();

        biofix_row_cursor.moveToFirst();
        degree_day_cursor.moveToFirst();

        for(int i =0; i < biofixCount; i++){
            TableRow row = new TableRow(findViewById(R.id.plumCurculioTableRow).getContext());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            TextView orchard = new TextView(findViewById(R.id.plumCurculioDescription).getContext());
            TextView risk = new TextView(findViewById(R.id.plumCurculioDescription).getContext());

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

        description = (TextView) findViewById(R.id.plumCurculioDescription);
        biofix_info = (TextView) findViewById(R.id.plumCurculioBiofix);
        traps = (TextView) findViewById(R.id.plumCurculioTraps);
        spray_timing = (TextView) findViewById(R.id.plumCurculioSprayTiming);

        description.setText("Plum curculio originally exists the east of Rocky Mountains in North America. This insect chooses multiple of host to lay eggs on, such as apples, plums, peaches and so on.  " +
                "Adult plum curculio are generally 4 to 6 mm long, they have specks with brown, black and grey colors  " +
                "Apple maggot eggs get laid underneath the skin of the fruit where the larvae hatch and develop inside. " +
                "\n\n"+
                "Damage: Uncontrolled plum curculio would be internally damaging to fruits listed above. " +
                "The damage can make the fruit drop prematurely.");

        biofix_info.setText("Application of appropriate insecticide during the pink and petal-fall stages of apples. " +
                "Removing the damaged, host fruit before adults emerge can significantly help prevention." +
                "Bioxfix at petal fall is also really important.");

        traps.setText("Growers can use Dead-inn Pyramid trap for plum curculio  " +
                "Unassembled dark green pyramid composed of heavy duty corrugated plastic, green entry cone, square collection jar, one bungee cord for securing collection jar, four 6” anchor pins for securing to ground. "
               /*"(information from http://www.agbio-inc.com/dead-inn-pyramid-trap.html)"*/);

        spray_timing.setText("Spraying needs to begin during the first few days of warm and humid weather after petal fall " +
                " with maximum temperature of 70" + (char) 0x00B0 + "F."+ "Avoid the low temperature and rains because spray would be washed off " +"A spray residue should be maintained for 308 DD base 50 following petal fall."
               /*"(information from http://extension.psu.edu/plants/tree-fruit/insects-mites/factsheets/plum-curculio)"*/ );

    }
}
