package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.joshuasvenson.insectmanager.Home.myDb;

public class AddOrchardForm extends AppCompatActivity {

    XMLParser1 xml = new XMLParser1();
    XMLParser_Station xml2 = new XMLParser_Station();

    Button SubmitButton;
    Button UseCurrentLocation;
    Button autofill;
    EditText mOrchardName;
    EditText mOrchardLatitude;
    EditText mOrchardLongitude;
    EditText mOrchardTreeRowSpacing;
    EditText mOrchardCrossRowSpread;
    EditText mOrchardPlantHeight;
    EditText mOrchardDensity;
    EditText mOrchardStation;

    String latitude;
    String longitude;
    String station;

    String Otherlatitude;
    String Otherlongitude;
    String Otherstation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_orchard_form);

        addListenerOnButton();

        mOrchardName = (EditText)findViewById(R.id.addOrchardForm_Name);
        mOrchardLatitude = (EditText)findViewById(R.id.addOrchardForm_Latitude);
        mOrchardLongitude = (EditText)findViewById(R.id.addOrchardForm_Longitude);
        mOrchardTreeRowSpacing = (EditText)findViewById(R.id.addOrchardForm_TreeRowSpacing);
        mOrchardCrossRowSpread = (EditText)findViewById(R.id.addOrchardForm_CrossRowSpread);
        mOrchardPlantHeight = (EditText)findViewById(R.id.addOrchardForm_PlantHeight);
        mOrchardDensity = (EditText)findViewById(R.id.addOrchardForm_DensityFactor);
        mOrchardStation = (EditText) findViewById(R.id.weather_station);

        getCoordinates();


    }



    private void getCoordinates(){
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        String provider = locationManager.getBestProvider(new Criteria(), true);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        double c = location.getLatitude();
        double d = location.getLongitude();

        latitude = Double.toString(c);
        longitude = Double.toString(d);

        new letsGetWeather().execute();
    }

    public class letsGetWeather extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... params) {


            String weatherData = xml.getWeatherData(latitude, longitude);

            return weatherData;
        }

        protected void onPostExecute(String weatherData) {

            station = xml.getStation(weatherData);

        }
    }





    public class autofillCoordinates extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... params) {


            String weatherData = xml2.getWeatherData(Otherstation);

            return weatherData;
        }

        protected void onPostExecute(String weatherData) {

            Otherlatitude = xml2.getLatitude(weatherData);
            Otherlongitude = xml2.getLongitude(weatherData);

        }
    }

    public class autofillStation extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... params) {


            String weatherData = xml.getWeatherData(Otherlatitude, Otherlongitude);

            return weatherData;
        }

        protected void onPostExecute(String weatherData) {

            Otherstation = xml.getStation(weatherData);

        }
    }


    public void addListenerOnButton(){
        final Context context = this;

        SubmitButton = (Button) findViewById(R.id.addOrchardForm_Submit);
        UseCurrentLocation = (Button) findViewById(R.id.use_curent_location);
        autofill = (Button) findViewById(R.id.autofill);


        UseCurrentLocation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View arg0){
                mOrchardLatitude.setText(latitude);
                mOrchardLongitude.setText(longitude);
                mOrchardStation.setText(station);
            }
        });

        autofill.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                String s = mOrchardStation.getText().toString();
                if (s.matches("")){
                    if((!mOrchardLongitude.getText().toString().matches(""))&&(!mOrchardLongitude.getText().toString().matches(""))){
                        Otherlatitude = mOrchardLatitude.getText().toString();
                        Otherlongitude = mOrchardLongitude.getText().toString();
                        new autofillStation().execute();
                        mOrchardStation.setText(Otherstation);
                    }
                }
                if (!s.matches("")){
                    Otherstation = mOrchardStation.getText().toString();
                    new autofillCoordinates().execute();
                    mOrchardLongitude.setText(Otherlongitude);
                    mOrchardLatitude.setText(Otherlatitude);
                }
            }
        });


        SubmitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //mChildRef.child("name").setValue(mOrchardName.getText().toString());
                if(mOrchardLatitude.getText().toString() == ""){
                    mOrchardLatitude.setText("0");
                }
                if(mOrchardLongitude.getText().toString() == ""){
                    mOrchardLongitude.setText("0");
                }
                if(mOrchardTreeRowSpacing.getText().toString() == ""){
                    mOrchardTreeRowSpacing.setText("0");
                }
                if(mOrchardCrossRowSpread.getText().toString() == ""){
                    mOrchardCrossRowSpread.setText("0");
                }
                if(mOrchardPlantHeight.getText().toString() == ""){
                    mOrchardPlantHeight.setText("0");
                }
                if(mOrchardDensity.getText().toString() == ""){
                    mOrchardDensity.setText("0");
                }
                /*mChildRef.child("latitude").setValue(Double.parseDouble(mOrchardLatitude.getText().toString()));
                mChildRef.child("longitude").setValue(Double.parseDouble(mOrchardLongitude.getText().toString()));
                mChildRef.child("tree_row_spacing").setValue(Double.parseDouble(mOrchardTreeRowSpacing.getText().toString()));
                mChildRef.child("cross_row_spread").setValue(Double.parseDouble(mOrchardCrossRowSpread.getText().toString()));
                mChildRef.child("plant_height").setValue(Double.parseDouble(mOrchardPlantHeight.getText().toString()));
                mChildRef.child("density_factor").setValue(Double.parseDouble(mOrchardDensity.getText().toString()));*/

                boolean isInserted = myDb.createOrchard(mOrchardName.getText().toString(),
                        Double.parseDouble(mOrchardLatitude.getText().toString()),
                        Double.parseDouble(mOrchardLongitude.getText().toString()),
                        mOrchardStation.getText().toString(),
                        Double.parseDouble(mOrchardTreeRowSpacing.getText().toString()),
                        Double.parseDouble(mOrchardCrossRowSpread.getText().toString()),
                        Double.parseDouble(mOrchardPlantHeight.getText().toString()),
                        Double.parseDouble(mOrchardDensity.getText().toString()),
                        null);


                if(isInserted == true){
                    Toast.makeText(AddOrchardForm.this, "Data Inserted " +mOrchardName.getText().toString(),Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AddOrchardForm.this, "Error Inserting",Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(context, Orchard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        });
    }

    /*@Override
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
    }*/




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
