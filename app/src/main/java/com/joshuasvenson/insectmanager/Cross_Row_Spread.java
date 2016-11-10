package com.joshuasvenson.insectmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.joshuasvenson.insectmanager.Orchard.mChildRef;

public class Cross_Row_Spread extends Activity {
    Button NextButton;

    EditText mOrchardCrossRowSpread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross__row__spread);

        addListenerOnButton();

        mOrchardCrossRowSpread = (EditText)findViewById(R.id.addOrchardCrossRowSpreadEditText);
    }
    public void addListenerOnButton() {
        final Context context = this;

        NextButton = (Button) findViewById(R.id.addOrchardCrossRowSpreadNextButton);

        NextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mChildRef.child("cross_row_spread").setValue(Double.parseDouble(mOrchardCrossRowSpread.getText().toString()));
                Intent intent = new Intent(context, Plant_Height.class);
                startActivity(intent);
            }

        });
    }
}
