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

public class CodlingMoth extends AppCompatActivity {

    TextView description;
    TextView biofix_info;
    TextView traps;
    TextView spray_timing;

    String insectKey = "1";

    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codling_moth);

        viewPager = (ViewPager)findViewById(R.id.codling_moth_view_pager);
        adapter = new CustomSwipeAdapter(this, insectKey);
        viewPager.setAdapter(adapter);

        TableLayout table = (TableLayout) findViewById(R.id.codlingMothTable);

        Cursor biofix_row_cursor = myDb.GetInsectBiofix(insectKey);
        Cursor degree_day_cursor = myDb.GetAllDegreeDaysForInsects(insectKey);
        int biofixCount = biofix_row_cursor.getCount();

        biofix_row_cursor.moveToFirst();
        degree_day_cursor.moveToFirst();

        for(int i =0; i < biofixCount; i++){
            TableRow row = new TableRow(findViewById(R.id.codlingMothTableRow).getContext());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            TextView orchard = new TextView(findViewById(R.id.codlingMothDescription).getContext());
            TextView risk = new TextView(findViewById(R.id.codlingMothDescription).getContext());

            int sprayDegreeDay = myDb.GetInsectSprayDay(Integer.parseInt(biofix_row_cursor.getString(4)));
            int accumulatedDegreeDay = Integer.parseInt(degree_day_cursor.getString(1));

            orchard.setText(myDb.GetOrchardName(Integer.parseInt(biofix_row_cursor.getString(5))) + " ");
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
            orchard.setBackgroundResource(R.color.backgroundColor2);

            risk.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.4f));
            risk.setGravity(Gravity.CENTER);
            risk.setBackgroundResource(R.color.backgroundColor2);

            row.addView(orchard);
            row.addView(risk);

            table.addView(row, i);

            biofix_row_cursor.moveToNext();
            degree_day_cursor.moveToNext();
        }
        biofix_row_cursor.close();
        degree_day_cursor.close();

        description = (TextView) findViewById(R.id.codlingMothDescription);
        biofix_info = (TextView) findViewById(R.id.codlingMothBiofix);
        traps = (TextView) findViewById(R.id.codlingMothTraps);
        spray_timing = (TextView) findViewById(R.id.codlingMothSprayTiming);

        description.setText("The adult codling moth is greyish with light grey and copper stripes on its wings. Its " +
                "wingspan is 17 mm on average. Codling moth larvae can be 1/2 - 3/4 inchs long with a pinkish body and " +
                "a brownish head. Codling moth eggs get laid on the surface of the fruit or leaves and, once hatched, " +
                "will chew an opening into the fruit to feed. In three to four weeks they will leave the fruit to pupate." +
                "\n\n"+
                "Damage: Feeding larvae will tunnel through the fruit to eat the proteinous seeds in the center. The " +
                "most visible sign of this will be a brown hole in the fruit and brown frass may be extruding from it. " +
                "These deep tunnels will eventually cause the fruit to rot.");

        biofix_info.setText("Codling moth biofix is set at the first sustained trap catch. A 'sustained' catch is " +
                "a continual period of moth activity. If one or two moths are trapped followed by a period of no " +
                "captures, then those early captures are ignored.");

        traps.setText("Codling moth pheremone traps are used to determine peak flight times of the moth. These traps " +
                "attract male moths by containing pheremones female moths use to attract mates. These traps have " +
                "plastic tops and sticky bottoms and are hung by a wire. These traps should be hung at eye level with one for " +
                "every 10 acres of trees. Traps should be set out during pink stage of bud development and should " +
                "be replaced about every month.");

        spray_timing.setText("Codling moth eggs will begin to hatch around 250 degree days (base 50" + (char) 0x00B0 +
                "F). It is at this time spraying should begin. If after the first spray more than 10 moths are being " +
                "caught per trap per week, a second spray may be necessary. Around 1000 degree days is when the " +
                "first generation of moth begin to fly. This should be the second biofix. Second generation egg " +
                "hatch occurs around 1300 degree days and is when the next round of spraying should occur.");

    }
}
