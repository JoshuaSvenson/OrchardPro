package com.joshuasvenson.insectmanager;

/**
 * This class if for the page after user selects +Add Orchard button. It asks user for orchard name.
 *
 * Created by Joshua on 5/27/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.joshuasvenson.insectmanager.Orchard.OrchardCount;
import static com.joshuasvenson.insectmanager.Orchard.list;
import static com.joshuasvenson.insectmanager.Orchard.list_adapter;
import static com.joshuasvenson.insectmanager.Orchard.mChildRef;
import static com.joshuasvenson.insectmanager.Orchard.mRootRef;
import static com.joshuasvenson.insectmanager.Orchard.myDb;
import static com.joshuasvenson.insectmanager.Orchard.orchardArray;

public class AddOrchard extends Activity {

    Button NextButton;
    EditText mOrchardName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_orchard);

        addListenerOnButton();

        mOrchardName = (EditText)findViewById(R.id.addOrchardNameEditText);

        TextView mTest = (TextView)findViewById(R.id.textViewTest);
        mTest.setText(orchardArray[0].toString());
    }

    public void addListenerOnButton(){
        final Context context = this;

        NextButton = (Button) findViewById(R.id.addOrchardNameNextButton);

        NextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mChildRef.child("name").setValue(mOrchardName.getText().toString());
                //boolean isInserted = myDb.insertData(mOrchardName.getText().toString());
                boolean isInserted = myDb.insertData2("NAME", "NAME IS NULL", mOrchardName.getText().toString(), 0);
                if(isInserted == true){
                    Toast.makeText(AddOrchard.this, "Data Inserted " +mOrchardName.getText().toString(),Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AddOrchard.this, "Error Inserting",Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(context, AddOrchardLocations.class);
                startActivity(intent);
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mChildRef.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
