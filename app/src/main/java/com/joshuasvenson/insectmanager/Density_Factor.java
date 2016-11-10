package com.joshuasvenson.insectmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.joshuasvenson.insectmanager.Orchard.mChildRef;

public class Density_Factor extends Activity {
    Button NextButton;

    EditText mOrchardDensityFactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_density__factor);

        addListenerOnButton();

        mOrchardDensityFactor = (EditText)findViewById(R.id.addOrchardDensityFactorEditText);
    }
    public void addListenerOnButton() {
        final Context context = this;

        NextButton = (Button) findViewById(R.id.addOrchardDensityFactorNextButton);

        NextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mChildRef.child("density_factor").setValue(Double.parseDouble(mOrchardDensityFactor.getText().toString()));
                Intent intent = new Intent(context, Tractor_Speed.class);
                startActivity(intent);
            }

        });
    }
}
