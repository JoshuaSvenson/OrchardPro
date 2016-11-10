package com.joshuasvenson.insectmanager;

/**
 * Created by Joshua on 5/27/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.joshuasvenson.insectmanager.Orchard.mChildRef;

public class Plant_Height extends Activity {

    Button NextButton;

    EditText mOrchardPlantHeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant__height);

        addListenerOnButton();

        mOrchardPlantHeight = (EditText)findViewById(R.id.addOrchardPlantHeightEditText);
    }

    public void addListenerOnButton(){
        final Context context = this;

        NextButton = (Button) findViewById(R.id.addOrchardPlantHeightNextButton);

        NextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mChildRef.child("plant_height").setValue(Double.parseDouble(mOrchardPlantHeight.getText().toString()));
                Intent intent = new Intent(context, Density_Factor.class);
                startActivity(intent);
            }

        });
    }

}
