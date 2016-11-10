package com.joshuasvenson.insectmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by anaso_000 on 10/9/2016.
 */

public class PowderyMildew extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.powdery_mildew);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText("While powdery mildew affects a great many plants, and on apple trees it can " +
                "decrease the number of flowers and fruit and cause stunted growth and blemished fruit. " +
                "\n\nPowdery mildew on apples will look like a velvety covering on leaves and branches. " +
                "\n\nIt can affect any apple variety, but some varieties are more susceptible than others.");
    }
}
