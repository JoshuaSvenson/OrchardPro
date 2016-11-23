package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by anaso_000 on 11/22/2016.
 */

public class gpm extends AppCompatActivity {

    EditText gpa;
    EditText W;
    EditText mph;
    Button Save;
    TextView gpm;

    EditText GPA;
    EditText W2;
    EditText MPH;
    TextView GPM;

    double gallons_acre;
    double spacing;
    double speed;

    double gallons_acre2;
    double spacing2;
    double speed2;

    static String gallons_minute2 = "0";

    static String  gallons_minute ="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gpm);

        gpa = (EditText) findViewById(R.id.addOrchardForm_gpa);
        mph = (EditText) findViewById(R.id.addOrchardForm_mph);
        W = (EditText) findViewById(R.id.addOrchardForm_W);
        Save = (Button) findViewById(R.id.addOrchardForm_Submit);
        gpm = (TextView) findViewById(R.id.addOrchardForm_gpm);


        GPA = (EditText) findViewById(R.id.addOrchardForm_GPA);
        MPH = (EditText) findViewById(R.id.addOrchardForm_MPH);
        W2 = (EditText) findViewById(R.id.addOrchardForm_W2);
        GPM = (TextView) findViewById(R.id.addOrchardForm_GPM);

        addListenerOnButton();

    }

    private void addListenerOnButton() {
        final Context context = this;

        Save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                gallons_acre = Double.parseDouble(gpa.getText().toString());
                spacing = Double.parseDouble(W.getText().toString());
                speed = Double.parseDouble(mph.getText().toString());
                gallons_minute = String.valueOf((spacing * gallons_acre * speed)/1000);

                gpm.setText(gallons_minute);

                gallons_acre2 = Double.parseDouble(GPA.getText().toString());
                spacing2 = Double.parseDouble(W2.getText().toString());
                speed2 = Double.parseDouble(MPH.getText().toString());
                gallons_minute2 = String.valueOf((spacing2 * gallons_acre2 * speed2)/5940);

                GPM.setText(gallons_minute2);

            }

        });
    }


    public static String getGpm(){
        return gallons_minute;
    }

    public static String getGpm2(){
        return gallons_minute2;
    }
}
