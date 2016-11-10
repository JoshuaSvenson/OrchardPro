package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.joshuasvenson.insectmanager.Orchard.mChildRef;

public class Tree_Spacing extends AppCompatActivity {
    Button NextButton;

    EditText mOrchardRowSpacing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree__spacing);

        addListenerOnButton();

        mOrchardRowSpacing = (EditText)findViewById(R.id.addOrchardRowSpacingEditText);
    }
    public void addListenerOnButton() {
        final Context context = this;

        NextButton = (Button) findViewById(R.id.addOrchardRowSpacingNextButton);

        NextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mChildRef.child("tree_row_spacing").setValue(Double.parseDouble(mOrchardRowSpacing.getText().toString()));
                Intent intent = new Intent(context, Cross_Row_Spread.class);
                startActivity(intent);
            }

        });
    }
}