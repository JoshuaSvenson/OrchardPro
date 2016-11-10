package com.joshuasvenson.insectmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by anaso_000 on 10/9/2016.
 */

public class AppleMagot extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apple_magot);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Maggots are sometimes called \"railroad worms\" because they leave brown" +
                "winding trails just under the apple skin of some varieties. " +
                "Apples may also be pitted and dimpled with numerous " +
                "tiny egg punctures." +
                "\n\nControl: Insecticides are directed against the apple maggot flies before eggs are laid. " +
                "Successful apple maggot control depends on killing the flies before eggs are deposited.");
    }
}