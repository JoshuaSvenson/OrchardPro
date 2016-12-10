package com.joshuasvenson.insectmanager;

/**
 * Created by Joshua on 5/27/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.joshuasvenson.insectmanager.Home.myDb;

/*
Name: Orchard
Description: This class provides the code for the Orchards page where the list of orchards are
Layout File: orchards_main.xml
 */
public class Orchard extends AppCompatActivity {

    Button AddOrchardButton;
    Button DeleteAllOrchardsButton;

    static ArrayList<String> list = new ArrayList<String>();

    static MyListAdapter list_adapter;
    ListView lv;

    static int OrchardCount = 0;

    /*
    Name: onCreate
    Description: Creates the activity
    Parameters: Bundle savedInstanceState
    Returns: void
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.orchards_main);

        lv = (ListView) findViewById(R.id.orchard_list);

        addListenerOnButton();
        list_adapter = new MyListAdapter(this, R.layout.orchard_listview, list);

        //Add buttons for all orchards in database
        Cursor res = myDb.getAllNameData();
        list.clear();
        if(res.getCount() > 0){
            OrchardCount = res.getCount();
            int count = 0;
            while (count < OrchardCount){
                list.add("New");
                list_adapter.notifyDataSetChanged();
                count++;
            }
        }

        lv.setAdapter(list_adapter);
    }

    /*
    Name: addListenerOnButton
    Description: Adds listeners on all of the buttons on the page and provides functionality when each button is clicked
    Parameters: None
    Returns: void
     */
    public void addListenerOnButton(){
        final Context context = this;

        AddOrchardButton = (Button) findViewById(R.id.addOrchardButton);
        DeleteAllOrchardsButton = (Button) findViewById(R.id.orchardsCurrentWeatherButton);

        //Add listener for add orchard button
        AddOrchardButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, AddOrchardForm.class);
                startActivity(intent);
            }

        });

        //Add listener for deleting all ochards button
        DeleteAllOrchardsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Deletes all data in database. This is okay because insects will repopulate itself from the Home class
                myDb.onUpgrade(myDb.getWritableDatabase(), 1, 2);
                Intent intent = new Intent(context, Orchard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

        });
    }

    /*
    Name: MyListAdapter
    Description: A custom adapter for the list that contains the orchards
     */
    private class MyListAdapter extends ArrayAdapter<String> {
        private int layout;
        private List<String> objects;
        public MyListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
            this.objects = objects;
        }

        /*
        Name: getView
        Description: populate the list with the orchards in database
        Parameters: final int position: the position in the list
                    View convertView:
                    ViewGroup parent:
        Returns: View
        */
        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.button = (Button) convertView.findViewById((R.id.NewOrchardButton));
                //When the orchard button is clicked, create activity for that orchard
                viewHolder.button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), AllensOrchard.class);
                        intent.putExtra("index", ((Button) v).getText().toString());
                        startActivity(intent);
                    }
                });
                convertView.setTag(viewHolder);

                //Get names of all orchards in database
                Cursor res = myDb.getAllNameData();
                boolean IsMoved = res.moveToPosition(position);
                if(IsMoved)
                    //Set orchard name text
                    viewHolder.button.setText(res.getString(0));
            }
            else{
                mainViewholder = (ViewHolder) convertView.getTag();
            }

            return convertView;
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public String getItem(int location) {
            return objects.get(location);
        }

        public long getItemId(Object obj) {
            return objects.indexOf(obj);
        }
    }

    public class ViewHolder {
        Button button;
    }

    /*
    Name: onCreateOptionsMenu
    Description: Initialize the contents of the Activity's standard options menu.
    Parameters: Menu menu - The options menu in which you place your items.
    Returns: boolean - You must return true for the menu to be displayed; if you return false it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. This adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //return true to display menu
        return true;
    }

    /*
    Name: onOptionsItemSelected
    Description: This hook is called whenever an item in your options menu is selected. The default implementation
                simply returns false to have the normal processing happen (calling the item's Runnable or sending
                a message to its Handler as appropriate). You can use this method for any items for which you would
                like to do processing without those other facilities.
    Parameters: MenuItem item - The menu item that was selected.
    Returns: boolean - Return false to allow normal menu processing to proceed, true to consume it here.
     */
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
        else if(id ==R.id.Insects_bar) {
            Intent intent = new Intent(context, Insects.class);
            startActivity(intent);
            return true;
        }
        else if(id ==R.id.Diseases_bar) {
            Intent intent = new Intent(context, Diseases.class);
            startActivity(intent);
            return true;
        }
        else if(id ==R.id.LocalWeather_bar) {
            Intent intent = new Intent(context, Weather1.class);
            startActivity(intent);
            return true;
        }
        else if(id ==R.id.Settings_bar) {
            Intent intent = new Intent(context, SettingsToolbar.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}