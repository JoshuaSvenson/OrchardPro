package com.joshuasvenson.insectmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import static com.joshuasvenson.insectmanager.Home.myDb;

public class CalculateDDBase extends AppCompatActivity {

    EditText DegreeDayCalcValue1;
    NumberPicker BaseValue1;

    EditText DegreeDayCalcValue2;
    NumberPicker BaseValue2;

    Button CalculateDegreeDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate_ddbase);

        DegreeDayCalcValue1 = (EditText) findViewById(R.id.DDBaseEditText1);
        BaseValue1 = (NumberPicker) findViewById(R.id.DDBaseNumberPicker1);

        BaseValue1.setMinValue(0);
        BaseValue1.setMaxValue(100);
        BaseValue1.setValue(50);

        DegreeDayCalcValue2 = (EditText) findViewById(R.id.DDBaseEditText2);
        BaseValue2 = (NumberPicker) findViewById(R.id.DDBaseNumberPicker2);

        BaseValue2.setMinValue(0);
        BaseValue2.setMaxValue(100);
        BaseValue2.setValue(50);

        CalculateDegreeDay = (Button) findViewById(R.id.ConvertDDButton);

        addListenerOnButton();
    }

    private void addListenerOnButton(){
        CalculateDegreeDay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if(DegreeDayCalcValue1.getText().toString() == ""){
                    DegreeDayCalcValue1.setText("0");
                }



            }

        });
    }
}
