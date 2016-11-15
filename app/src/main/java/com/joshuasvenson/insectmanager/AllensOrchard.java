package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.joshuasvenson.insectmanager.Home.myDb;

public class AllensOrchard extends AppCompatActivity {
    Button deleteButton;

    Button SprayScheduleButton;
    Button CalculationsButton;
    Button BiofixDataButton;
    Button WeatherButton;
    Button SettingsButton;

    TextView orchardName;

    String orchard_key;
    String name;

    private static final int SELECTED_PICTURE= 1;

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allens_orchard);

        iv = (ImageView) findViewById(R.id.imageView1);

        orchardName = (TextView)findViewById(R.id.ScreenTitle);

        name = getIntent().getExtras().get("index").toString();
        Cursor cursor = myDb.query_row(name);
        cursor.moveToFirst();

        orchard_key = cursor.getString(0);
        orchardName.setText(String.valueOf(cursor.getString(1)));

        cursor.close();

        addListenerOnButton();
    }

    public void btnClick (View v){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECTED_PICTURE);
    }

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
                    Drawable d = new BitmapDrawable(yourSelectedImage);

                    iv.setBackground(d);
                }
                break;
            default:
                break;
        }

    }

    public void addListenerOnButton() {
        final Context context = this;

        SprayScheduleButton = (Button) findViewById(R.id.SprayScheduleButton);
        CalculationsButton = (Button) findViewById(R.id.OrchardCalculationsButton);
        BiofixDataButton = (Button) findViewById(R.id.BiofixDataButton);
        WeatherButton = (Button) findViewById(R.id.OrchardWeatherButton);
        SettingsButton = (Button) findViewById(R.id.OrchardSettingsButton);
        deleteButton = (Button) findViewById(R.id.DeleteButton);

        SprayScheduleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, SpraySchedule.class);
                intent.putExtra("orchard_key", orchard_key);
                startActivity(intent);
            }

        });

        CalculationsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Calculations.class);
                intent.putExtra("orchard_key", orchard_key);
                startActivity(intent);
            }

        });

        BiofixDataButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, BiofixList.class);
                intent.putExtra("orchard_key", orchard_key);
                startActivity(intent);
            }

        });

        WeatherButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, OrchardWeather.class);
                intent.putExtra("orchard_key", orchard_key);
                startActivity(intent);
            }

        });

        SettingsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, OrchardSettings.class);
                intent.putExtra("orchard_key", orchard_key);
                startActivity(intent);
            }

        });

        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                myDb.deleteOrchardData(orchard_key);

                Intent intent = new Intent(context, Orchard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        });
    }
}

