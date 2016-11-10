package com.joshuasvenson.insectmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by anaso_000 on 10/9/2016.
 */

public class FireBlight extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fire_blight);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText("One of the more devastating of the apple tree diseases, fire blight is a " +
                "bacterial disease that affects all parts of the tree and can lead to death. " +
                "\n\nSymptoms of fire blight include die back of branches, leaves and blossoms and " +
                "depressed areas on the bark that will be discolored and are, in fact, areas of the " +
                "branches that are dying.");
    }
}
