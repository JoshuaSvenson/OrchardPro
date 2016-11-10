package com.joshuasvenson.insectmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by anaso_000 on 10/9/2016.
 */

public class EuropeanRedMites extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.european_redmites);


        textView = (TextView) findViewById(R.id.textView);
        textView.setText("European red mites feed on leaves. Severe mite injury produces browning and loss " +
                "of color in the leaves, commonly referred to as bronzing. \nExtensive foliage injury may " +
                "reduce the quality and quantity of fruit and the following year's return bloom." +
                "\n\nMite management emphasizes orchard floor management, scouting of pest and beneficial " +
                "populations, consideration of other stresses on the trees, and prudent use of chemicals.");
    }
}
