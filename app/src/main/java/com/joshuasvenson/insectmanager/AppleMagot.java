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

public class AppleMagot extends AppCompatActivity {

    TextView description;
    TextView biofix_info;
    TextView traps;
    TextView spray_timing;

    String insectKey = "2";

    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apple_magot);

        viewPager = (ViewPager)findViewById(R.id.apple_maggot_view_pager);
        adapter = new CustomSwipeAdapter(this, insectKey, "insect");
        viewPager.setAdapter(adapter);

        TableLayout table = (TableLayout) findViewById(R.id.appleMagotTable);

        Cursor biofix_row_cursor = myDb.GetInsectBiofix(insectKey);
        Cursor degree_day_cursor = myDb.GetAllDegreeDaysForInsects(insectKey);
        int biofixCount = biofix_row_cursor.getCount();

        biofix_row_cursor.moveToFirst();
        degree_day_cursor.moveToFirst();

        for(int i =0; i < biofixCount; i++){
            TableRow row = new TableRow(findViewById(R.id.appleMagotTableRow).getContext());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            TextView orchard = new TextView(findViewById(R.id.appleMaggotDescription).getContext());
            TextView risk = new TextView(findViewById(R.id.appleMaggotDescription).getContext());

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

        description = (TextView) findViewById(R.id.appleMaggotDescription);
        biofix_info = (TextView) findViewById(R.id.appleMaggotBiofix);
        traps = (TextView) findViewById(R.id.appleMaggotTraps);
        spray_timing = (TextView) findViewById(R.id.appleMaggotSprayTiming);

        description.setText("The apple maggot larva is a typical fly larva which looks like a creamy white cylindrical " +
        "maggot about 10mm long. Adult apple maggots are slightly smaller than house flys and are black with white bands. " +
        "Apple maggot eggs get laid underneath the skin of the fruit where the larvae hatch and develop inside. " +
        "\n\n"+
        "Damage: Feeding larvae in the fruit create tunnels which turn brown and rot. This may leave the fruit " +
        "misshapen and cause the fruit to drop prematurely. Small dimples in the fruit may be an indication " +
        "of eggs laid underneath the skin.");

        biofix_info.setText("Apple maggot pupae overwinter underground and will energe as adults the following summer. " +
        "As a result, Biofix is set as January 1.");

        traps.setText("An apple maggot trap consists of a red sphere coated with TangleFoot. The sticky TangleFoot " +
        "on the red sphere is designed to trap adult females attemping to lay eggs. These traps should be placed " +
        "in the tree canopy just as trees finish blossoming. Traps should be placed high in the brightest " +
        "areas of the tree with one trap for every 100-150 apples.");

        spray_timing.setText("Spraying should begin within a few days of an apple maggot being caught in the trap " +
                " or when apple maggots begin emerging from the ground around 900 degree days " +
                "(base 50" + (char) 0x00B0 + "F). Check pesticide label for spray interval, but generally " +
                "reapplication will be needed every 7 to 10 days. Continue spraying as long as apple maggots are " +
                "being caught in traps.");

    }
}