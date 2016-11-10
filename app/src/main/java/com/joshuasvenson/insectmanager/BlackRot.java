package com.joshuasvenson.insectmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by anaso_000 on 10/9/2016.
 */

public class BlackRot extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.black_rot);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Black rot apple disease can appear in one or a combination of three different " +
                "forms: \n\nBlack fruit rot: blossom end rot, turning brown and then black. \n\nFrogeye leaf spot: Appear" +
                "on the leaves and will be grey or light brown spots with a purple edge and. \n\nBlack " +
                "rot limb canker: Appear as deppresions on the limbs.");
    }
}
