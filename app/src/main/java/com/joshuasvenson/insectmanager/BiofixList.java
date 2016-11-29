package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.joshuasvenson.insectmanager.Home.myDb;

public class BiofixList extends AppCompatActivity {

    Button AddBiofixButton;
    String orchardKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.biofix_list);

        TableLayout table = (TableLayout) findViewById(R.id.biofixTable);

        orchardKey = getIntent().getExtras().get("orchard_key").toString();

        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);

        TextView insectTitle = new TextView(this);
        insectTitle.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.25f));
        TextView biofixTitle = new TextView(this);
        biofixTitle.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.25f));
        TextView degreeDaysTitle = new TextView(this);
        degreeDaysTitle.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.25f));
        TextView deleteBiofixButtonTitle = new TextView(this);
        deleteBiofixButtonTitle.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.25f));

        insectTitle.setText("Insect");
        insectTitle.setGravity(Gravity.CENTER);
        insectTitle.setTypeface(null, Typeface.BOLD);

        biofixTitle.setText("Biofix Date");
        biofixTitle.setGravity(Gravity.CENTER);
        biofixTitle.setTypeface(null, Typeface.BOLD);

        degreeDaysTitle.setText("Degree Days");
        degreeDaysTitle.setGravity(Gravity.CENTER);
        degreeDaysTitle.setTypeface(null, Typeface.BOLD);

        deleteBiofixButtonTitle.setText("Delete Biofix");
        deleteBiofixButtonTitle.setGravity(Gravity.CENTER);
        deleteBiofixButtonTitle.setTypeface(null, Typeface.BOLD);

        row.addView(insectTitle);
        row.addView(biofixTitle);
        row.addView(degreeDaysTitle);
        row.addView(deleteBiofixButtonTitle);

        table.addView(row, 0);

        Cursor cursor = myDb.GetOrchardBiofix(orchardKey);
        int biofixCount = cursor.getCount();

        cursor.moveToFirst();
        for(int i = 1; i <= biofixCount; i++){
            row = new TableRow(this);
            lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            row.setLayoutParams(lp);

            final TextView biofixID = new TextView(this);
            TextView insect = new TextView(this);
            insect.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.25f));
            TextView biofix = new TextView(this);
            biofix.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.25f));
            TextView degreeDays = new TextView(this);
            degreeDays.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.25f));
            Button deleteBiofixButton = new Button(this);
            deleteBiofixButton.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.25f));

            biofixID.setText(cursor.getString(0));
            insect.setText(myDb.GetInsectName(Integer.parseInt(cursor.getString(8))) + " ");
            insect.setGravity(Gravity.CENTER);

            biofix.setText(cursor.getString(2) + " " + cursor.getString(1) + " " + cursor.getString(3) + " ");
            biofix.setGravity(Gravity.CENTER);

            degreeDays.setText(cursor.getString(4));
            degreeDays.setGravity(Gravity.CENTER);

            deleteBiofixButton.setText("Delete");
            deleteBiofixButton.setGravity(Gravity.CENTER);

            row.addView(insect);
            row.addView(biofix);
            row.addView(degreeDays);
            row.addView(deleteBiofixButton);

            deleteBiofixButton.setOnClickListener(new View.OnClickListener()
            {
                @Override public void onClick(View v)
                {
                    myDb.deleteBiofixData(biofixID.getText().toString());
                    // row is your row, the parent of the clicked button
                    View row = (View) v.getParent();
                    // container contains all the rows, you could keep a variable somewhere else to the container which you can refer to here
                    ViewGroup container = ((ViewGroup)row.getParent());
                    // delete the row and invalidate your view so it gets redrawn
                    container.removeView(row);
                    container.invalidate();
                }
            });

            table.addView(row, i);

            cursor.moveToNext();
        }
        cursor.close();

        addListenerOnButton();
    }

    public void addListenerOnButton() {
        final Context context = this;

        AddBiofixButton = (Button) findViewById(R.id.addBiofixButton);

        AddBiofixButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, BioFix.class);
                intent.putExtra("orchard_key", orchardKey);
                startActivity(intent);
            }

        });

    }
}
