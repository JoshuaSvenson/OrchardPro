package com.joshuasvenson.insectmanager;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.joshuasvenson.insectmanager.Home.myDb;

public class Calculations extends AppCompatActivity {

    double tree_row_volume;
    TextView TRV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculations);

        TRV = (TextView)findViewById(R.id.TreeRowVolume);

        String orchard_key = getIntent().getExtras().get("orchard_key").toString();
        Cursor cursor = myDb.GetOrchardSettings(orchard_key);

        cursor.moveToFirst();

        tree_row_volume = CalcTreeRowVolume(Double.parseDouble(cursor.getString(7)),
                Double.parseDouble(cursor.getString(2)),
                Double.parseDouble(cursor.getString(6)),
                Double.parseDouble(cursor.getString(3)));
        TRV.setText(String.valueOf(tree_row_volume));

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
