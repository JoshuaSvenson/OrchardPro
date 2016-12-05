package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import static com.joshuasvenson.insectmanager.Home.myDb;

public class Calculations extends AppCompatActivity {

    double tree_row_volume;
    TextView TRV;
    Button button;

    TextView GPM;
    TextView GPM2;

    String gallons_minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculations);

        TRV = (TextView)findViewById(R.id.TreeRowVolume);
        button = (Button) findViewById(R.id.button1);
        GPM = (TextView) findViewById(R.id.GPM);
        GPM2 = (TextView) findViewById(R.id.GPM_nozzle);

        String orchard_key = getIntent().getExtras().get("orchard_key").toString();
        Cursor cursor = myDb.GetOrchardSettings(orchard_key);

        cursor.moveToFirst();

        tree_row_volume = CalcTreeRowVolume(Double.parseDouble(cursor.getString(7)),
                Double.parseDouble(cursor.getString(2)),
                Double.parseDouble(cursor.getString(6)),
                Double.parseDouble(cursor.getString(3)));
        TRV.setText(String.valueOf(tree_row_volume));

        GPM.setText("Gpm (gallons per minute) = " + gpm.getGpm() + " per side");

        GPM2.setText("Gpm (gallons per minute) = " + gpm.getGpm2() + " per nozzle");

        addListenerOnButton();

    }

    private void addListenerOnButton() {
        final Context context = this;

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Create new activity from the gpm class
                Intent intent = new Intent(context, gpm.class);
                //Starts activity and opens up the gpm calculator page
                startActivity(intent);
            }

        });
    }

    double CalcTreeRowVolume(double trs, double crs, double ph, double density){
        double frpa = CalculateFeetOfRowPerAcre(trs);
        double cftrva = CalculateCubicFeetOfTreeRowVolumePerAcre(frpa, ph, crs);
        double trv = CalculateTreeRowVolumeGallonagePerAcre(cftrva, density);

        return trv;
    }

    //brs is between_row spacing (tree_row_spacing)
    double CalculateFeetOfRowPerAcre(double brs){
        double feet_of_row_per_acre;

        feet_of_row_per_acre = 43560/brs;
        return feet_of_row_per_acre;
    }

    //frpa is feet of row per acre
    //crps is cross row plant spread
    double CalculateCubicFeetOfTreeRowVolumePerAcre(double frpa, double plantHeight, double crps){
        double cubic_feet_of_tree_row_volume_per_acre;

        cubic_feet_of_tree_row_volume_per_acre = frpa*plantHeight*crps;
        return cubic_feet_of_tree_row_volume_per_acre;
    }

    //cftrva is cubic feet of tree row volume per acre
    double CalculateTreeRowVolumeGallonagePerAcre(double cftrva, double density){
        double tree_row_volume_gallonage_per_acre;

        tree_row_volume_gallonage_per_acre = (cftrva*density)/1000;
        return tree_row_volume_gallonage_per_acre;
    }

}
