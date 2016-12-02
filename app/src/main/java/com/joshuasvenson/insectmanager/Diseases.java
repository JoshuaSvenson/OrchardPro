package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by anaso_000 on 10/9/2016.
 */

public class Diseases extends AppCompatActivity {
    ListView listView;
    String diseases[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseases);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.listView);

        String[] diseases = {"Apple Scab", "Powdery Mildew", "Black Rot", "Collar Rot", "Fire Blight"};

        Integer diseaseImages[] = {R.drawable.apple_scab_list_view,
                R.drawable.powdery_mildew_list_view,
                R.drawable.black_rot_list_view,
                R.drawable.collar_rot_list_view,
                R.drawable.fire_blight_list_view};

        ListAdapter adapter = new ListAdapter(this, diseases, diseaseImages);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                if(position==0){
                    Intent one = new Intent(Diseases.this, AppleScab.class);
                    startActivity(one);
                }
                if(position==1){
                    Intent two = new Intent(Diseases.this, PowderyMildew.class);
                    startActivity(two);
                }
                if(position == 2){
                    Intent three = new Intent(Diseases.this, BlackRot.class);
                    startActivity(three);
                }
                if (position == 3){
                    Intent four = new Intent(Diseases.this, CollarRot.class);
                    startActivity(four);
                }
                if (position == 4){
                    Intent five = new Intent(Diseases.this, FireBlight.class);
                    startActivity(five);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        final Context context = this;

        //noinspection SimplifiableIfStatement
        if (id == R.id.Home_bar) {
            Intent intent = new Intent(context, Home.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
