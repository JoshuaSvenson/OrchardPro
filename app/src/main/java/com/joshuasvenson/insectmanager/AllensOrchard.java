package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import static com.joshuasvenson.insectmanager.Home.myDb;

/*
Name: AllensOrchard
Description: This class provides the code for the orchard the user creates
Layout File: activity_allens_orchard.xml
 */
public class AllensOrchard extends AppCompatActivity {

    //Initialize variables
    Button deleteButton;
    Button SprayScheduleButton;
    Button CalculationsButton;
    Button BiofixDataButton;
    Button WeatherButton;
    Button SettingsButton;

    TextView orchardName;

    String orchard_key;
    String name;

    Bitmap bitmap;

    private static final int SELECTED_PICTURE= 1;

    ImageView iv;


    /*
    Name: onCreate
    Description: Called when the activity gets created.
    */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allens_orchard);

        //Find the image view for the image that the user can optionally add
        iv = (ImageView) findViewById(R.id.imageView1);

        orchardName = (TextView)findViewById(R.id.ScreenTitle);

        //Get the name of the current orchard from value passed from previous activity
        name = getIntent().getExtras().get("index").toString();
        Cursor cursor = myDb.query_row(name);
        cursor.moveToFirst();

        setTitle(name);

        //Get the orchard key. Currently the orchard key is retrieved from the orchard name. This
        //could cause problems as if you add two orchards with the same name, which key do you use?
        orchard_key = cursor.getString(0);
        orchardName.setText(String.valueOf(cursor.getString(1)));

        cursor.close();

        //Get image from database if the is an image available and set it
        byte[] image2 = myDb.GetImage(orchard_key);
        if(image2 != null){
            bitmap = getImage(image2);
            iv.setImageBitmap(bitmap);
        }

        addListenerOnButton();
    }

    /*
    Name: btnClick
    Description:
    Parameters: View v
    Returns: void
     */
    public void btnClick (View v){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECTED_PICTURE);
    }

    /*
    Name: onActivityResult
    Description:
    Parameters: int requestCode -
                int resultCode -
                Intent data -
    Return: void
     */
    /*!!!!!!!!!!!!!!*/@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECTED_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    //Drawable d = new BitmapDrawable(yourSelectedImage);

                    byte[] image = getBytes(yourSelectedImage);

                    myDb.addImage(orchard_key,image);

                    byte[] image2 = myDb.GetImage(orchard_key);
                    bitmap = getImage(image2);

                    iv.setImageBitmap(bitmap);

                    //iv.setImageBitmap(BitmapFactory.decodeFile(filePath));
                    //iv.setBackground(d);


                }
                break;
            default:
                break;
        }

    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    /*
    Name: addListenerOnButton
    Description: add listeners on all of the buttons in the activity
    Parameters: None
    Returns: void
     */
    public void addListenerOnButton() {
        final Context context = this;

        SprayScheduleButton = (Button) findViewById(R.id.SprayScheduleButton);
        CalculationsButton = (Button) findViewById(R.id.OrchardCalculationsButton);
        BiofixDataButton = (Button) findViewById(R.id.BiofixDataButton);
        WeatherButton = (Button) findViewById(R.id.OrchardWeatherButton);
        SettingsButton = (Button) findViewById(R.id.OrchardSettingsButton);
        deleteButton = (Button) findViewById(R.id.DeleteButton);

        //Set listener on Spray Schedule button
        SprayScheduleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //History class should probably be renamed to something like SpraySchedule
                Intent intent = new Intent(context, History.class);
                //pass the orchard key to the next activity
                intent.putExtra("orchard_key", orchard_key);
                startActivity(intent);
            }

        });

        //Set listener on Calculations button
        CalculationsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Calculations.class);
                //pass the orchard key to the next activity
                intent.putExtra("orchard_key", orchard_key);
                startActivity(intent);
            }

        });

        //Set listener on Biofix button
        BiofixDataButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, BiofixList.class);
                //pass the orchard key to the next activity
                intent.putExtra("orchard_key", orchard_key);
                startActivity(intent);
            }

        });

        //Set listener on Weather button
        WeatherButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, OrchardWeather.class);
                //pass the orchard key to the next activity
                intent.putExtra("orchard_key", orchard_key);
                startActivity(intent);
            }

        });

        //Set listener on Settings button
        SettingsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, OrchardSettings.class);
                //pass the orchard key to the next activity
                intent.putExtra("orchard_key", orchard_key);
                startActivity(intent);
            }

        });

        //Set listener on Delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //Delete current orchard from database
                myDb.deleteOrchardData(orchard_key);

                //Go back to orchards homepage
                Intent intent = new Intent(context, Orchard.class);
                //clears activity so you can't go back to the page (since you deleted it)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        });
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

