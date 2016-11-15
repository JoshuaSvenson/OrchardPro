package com.joshuasvenson.insectmanager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by anaso_000 on 10/9/2016.
 */

public class PlumCurculio extends AppCompatActivity {


    TextView textView;

    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    String insectKey = "4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plum_curculio);

        viewPager = (ViewPager)findViewById(R.id.plum_curculio_view_pager);
        adapter = new CustomSwipeAdapter(this, insectKey);
        viewPager.setAdapter(adapter);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Apples attacked by plum curculio frequently suffer surface scarring and distortion" +
                "from feeding and egg laying. \nThe most important injury results from the crescent-shaped " +
                "cuts made in apples by females during egg laying. \n\nInfested immature apples often fall " +
                "prematurely, or if they stay on the tree they may be hard, knotty, and misshapen.");

    }
}
