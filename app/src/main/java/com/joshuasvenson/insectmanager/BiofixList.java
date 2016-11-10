package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

        Cursor cursor = myDb.GetOrchardBiofix(orchardKey);
        int biofixCount = cursor.getCount();

        cursor.moveToFirst();
        for(int i =0; i < biofixCount; i++){
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            TextView insect = new TextView(this);
            TextView biofix = new TextView(this);
            TextView degreeDays = new TextView(this);

            insect.setText(myDb.GetInsectName(Integer.parseInt(cursor.getString(4))) + " ");
            biofix.setText(cursor.getString(1) + " ");
            degreeDays.setText(cursor.getString(2));

            row.addView(insect);
            row.addView(biofix);
            row.addView(degreeDays);

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
