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

public class Insects extends AppCompatActivity  {
    ListView listView;
    String insects[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.listView);

        String[] insects = {"Apple Maggot", "Aphids", "Codling Moth", "Plum Curculio", "European Red Mites"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview, R.id.listText, insects);

        //listView.setBackgroundResource(R.drawable.apple_maggot);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                if(position==0){
                    Intent one = new Intent(Insects.this, AppleMagot.class);
                    startActivity(one);
                }
                if(position==1){
                    Intent two=new Intent(Insects.this,Aphids.class);
                    startActivity(two);
                }
                if(position == 2){
                    Intent three = new Intent(Insects.this, CodlingMoth.class);
                    startActivity(three);
                }
                if (position == 3){
                    Intent four = new Intent(Insects.this, PlumCurculio.class);
                    startActivity(four);
                }
                if (position == 4){
                    Intent five = new Intent (Insects.this, EuropeanRedMites.class);
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
