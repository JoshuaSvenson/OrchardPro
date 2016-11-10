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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.joshuasvenson.insectmanager.Orchard.mChildRef;
import static com.joshuasvenson.insectmanager.Home.myDb;

public class AddOrchardForm extends AppCompatActivity {

    JSONParser1 xml = new JSONParser1();
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
                mOrchardLatitude.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Otherlatitude = String.valueOf(mOrchardLatitude.getText());
                        Otherlongitude = String.valueOf(mOrchardLongitude.getText());
                        new autofillStation().execute();
                        mOrchardStation.setText(Otherstation);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                mOrchardStation.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Otherstation = String.valueOf(mOrchardStation.getText());
                        new autofillCoordinates().execute();
                        mOrchardLatitude.setText(Otherlatitude);
                        mOrchardLongitude.setText(Otherlongitude);

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });


        SubmitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mChildRef.child("name").setValue(mOrchardName.getText().toString());
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
                mChildRef.child("latitude").setValue(Double.parseDouble(mOrchardLatitude.getText().toString()));
                mChildRef.child("longitude").setValue(Double.parseDouble(mOrchardLongitude.getText().toString()));
                mChildRef.child("tree_row_spacing").setValue(Double.parseDouble(mOrchardTreeRowSpacing.getText().toString()));
                mChildRef.child("cross_row_spread").setValue(Double.parseDouble(mOrchardCrossRowSpread.getText().toString()));
                mChildRef.child("plant_height").setValue(Double.parseDouble(mOrchardPlantHeight.getText().toString()));
                mChildRef.child("density_factor").setValue(Double.parseDouble(mOrchardDensity.getText().toString()));

                boolean isInserted = myDb.createOrchard(mOrchardName.getText().toString(),
                        Double.parseDouble(mOrchardLatitude.getText().toString()),
                        Double.parseDouble(mOrchardLongitude.getText().toString()),
                        Double.parseDouble(mOrchardTreeRowSpacing.getText().toString()),
                        Double.parseDouble(mOrchardCrossRowSpread.getText().toString()),
                        Double.parseDouble(mOrchardPlantHeight.getText().toString()),
                        Double.parseDouble(mOrchardDensity.getText().toString()));
                //boolean isInserted = myDb.insertData2("NAME", "NAME IS NULL", mOrchardName.getText().toString(), 0);
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
