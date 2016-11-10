package com.joshuasvenson.insectmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by anaso_000 on 10/9/2016.
 */

public class CollarRot extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collar_rot);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Collar rot is a particularly bad apple tree problem. Initially, it will cause " +
                "stunted or delayed growth and blossoming, yellowing leaves and leaf drop. \n\nEventually a " +
                "canker (dying area) will appear at the base of the tree, girdling and killing the tree.");
    }
}