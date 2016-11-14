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
    Button CurrentLocationConditionsButton;

    static DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    static DatabaseReference mChildRef;

    static DatabaseReference orchardArray[] = new DatabaseReference[3];

    static ArrayList<String> list = new ArrayList<String>();

    static MyListAdapter list_adapter;
    ListView lv;

    static int OrchardCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.orchards_main);

        lv = (ListView) findViewById(R.id.orchard_list);

        addListenerOnButton();
        list_adapter = new MyListAdapter(this, R.layout.orchard_listview, list);

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

        myDb.createInsect("Codling Moth", 50.0, 88.0, 225.0);
        myDb.createInsect("Apple Maggot", 50.0, -1, 1000.0);
        myDb.createInsect("Rosy Apple Aphid", 40.0, -1, 200.0);
    }

    public void addListenerOnButton(){
        final Context context = this;

        AddOrchardButton = (Button) findViewById(R.id.addOrchardButton);
        CurrentLocationConditionsButton = (Button) findViewById(R.id.orchardsCurrentWeatherButton);
        //NewOrchardButton = (Button) findViewById(R.id.AllenButton);

        AddOrchardButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mChildRef = mRootRef.push();
                orchardArray[0] = mChildRef;

                Intent intent = new Intent(context, AddOrchardForm.class);
                startActivity(intent);
            }

        });

        CurrentLocationConditionsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                myDb.onUpgrade(myDb.getWritableDatabase(), 1, 2);
                Intent intent = new Intent(context, Orchard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(orchardArray[0] != null) {
            orchardArray[0].child("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    /*String text = dataSnapshot.getValue(String.class);
                    NewOrchardButton.setText(text);*/
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private class MyListAdapter extends ArrayAdapter<String> {
        private int layout;
        private List<String> objects;
        public MyListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
            this.objects = objects;
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.button = (Button) convertView.findViewById((R.id.NewOrchardButton));
                viewHolder.button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), AllensOrchard.class);
                        intent.putExtra("index", ((Button) v).getText().toString());
                        startActivity(intent);
                    }
                });
                convertView.setTag(viewHolder);

                Cursor res = myDb.getAllNameData();
                boolean IsMoved = res.moveToPosition(position);
                if(IsMoved)
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
}