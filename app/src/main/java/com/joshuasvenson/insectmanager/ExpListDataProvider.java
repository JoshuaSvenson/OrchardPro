package com.joshuasvenson.insectmanager;

import android.database.Cursor;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.joshuasvenson.insectmanager.Home.myDb;

/**
 * Created by Joshua on 11/6/2016.
 */

public class ExpListDataProvider {

    public static HashMap<String, List<String>> getInfo(String orchard_key)
    {
        HashMap<String, List<String>> RiskLevelDetails = new HashMap<String, List<String>>();

        List<String> Low_Risk_List = new ArrayList<String>();
        //Low_Risk_List.add("White Apple LeafRoller");

        List<String> Medium_Risk_List = new ArrayList<String>();
        //Medium_Risk_List.add("Apple Maggot");
        //Medium_Risk_List.add("Oriental Fruit Moth");

        List<String> High_Risk_List = new ArrayList<String>();
        //High_Risk_List.add("Codling Moth");
        //High_Risk_List.add("Rosy Apple Aphid");

        RiskLevelDetails.put("High Risk", High_Risk_List);
        RiskLevelDetails.put("Medium Risk", Medium_Risk_List);
        RiskLevelDetails.put("Low Risk", Low_Risk_List);

        Cursor biofix_row_cursor = myDb.GetOrchardBiofix(orchard_key);
        Cursor degree_day_cursor = myDb.GetAllDegreeDaysForOrchard(orchard_key);
        int biofixCount = biofix_row_cursor.getCount();

        biofix_row_cursor.moveToFirst();
        degree_day_cursor.moveToFirst();
        for(int i =0; i < biofixCount; i++){

            int sprayDegreeDay = myDb.GetInsectSprayDay(Integer.parseInt(biofix_row_cursor.getString(4)));
            int accumulatedDegreeDay = Integer.parseInt(degree_day_cursor.getString(1));

            if(accumulatedDegreeDay >= sprayDegreeDay){
                High_Risk_List.add(myDb.GetInsectName(Integer.parseInt(biofix_row_cursor.getString(4))));
            }
            else if(accumulatedDegreeDay >= sprayDegreeDay - 100){
                Medium_Risk_List.add(myDb.GetInsectName(Integer.parseInt(biofix_row_cursor.getString(4))));
            }
            else{
                Low_Risk_List.add(myDb.GetInsectName(Integer.parseInt(biofix_row_cursor.getString(4))));
            }
            //Low_Risk_List.add(myDb.GetInsectName(Integer.parseInt(cursor.getString(4))));
            //Low_Risk_List.add(String.valueOf(accumulatedDegreeDay));

            biofix_row_cursor.moveToNext();
            degree_day_cursor.moveToNext();
        }
        biofix_row_cursor.close();
        degree_day_cursor.close();

        return RiskLevelDetails;
    }
}
