package com.joshuasvenson.insectmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by anaso_000 on 10/9/2016.
 */

public class AppleScab extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apple_scab);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Apple scab is an apple tree disease that leaves warty, brown bumps on the leaves and" +
                "fruit. It is a fungus that primarily affects trees in areas that have high humidity.");
    }
}
