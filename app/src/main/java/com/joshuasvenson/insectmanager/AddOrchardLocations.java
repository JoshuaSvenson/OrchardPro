package com.joshuasvenson.insectmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.joshuasvenson.insectmanager.Orchard.mChildRef;

/**
 * Created by sunsh_000 on 9/8/2016.
 */
public class AddOrchardLocations extends Activity {
    Button NextButton;

    EditText mOrchardLatitude;
    EditText mOrchardLongitude;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_orchard_locations);

        addListenerOnButton();

        mOrchardLatitude = (EditText)findViewById(R.id.addOrchardLatitudeEditText);
        mOrchardLongitude = (EditText)findViewById(R.id.addOrchardLongitudeEditText);
    }

    public void addListenerOnButton() {
        final Context context = this;

        NextButton = (Button) findViewById(R.id.addOrchardLocationsNextButton);

        NextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mChildRef.child("latitude").setValue(Double.parseDouble(mOrchardLatitude.getText().toString()));
                mChildRef.child("longitude").setValue(Double.parseDouble(mOrchardLongitude.getText().toString()));
                Intent intent = new Intent(context, Tree_Spacing.class);
                startActivity(intent);
            }
        });
    }

    /*@Override
    protected void onStart() {
        super.onStart();

        mOrchardLatitudeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/

}
