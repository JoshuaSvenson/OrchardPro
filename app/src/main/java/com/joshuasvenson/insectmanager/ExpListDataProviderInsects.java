package com.joshuasvenson.insectmanager;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.joshuasvenson.insectmanager.Home.myDb;

/**
 * Created by Joshua on 11/9/2016.
 */

public class ExpListDataProviderInsects {

    public static HashMap<String, List<String>> getInfo(String insect_key)
    {
        HashMap<String, List<String>> RiskLevelDetails = new HashMap<String, List<String>>();

        List<String> Low_Risk_List = new ArrayList<String>();

        List<String> Medium_Risk_List = new ArrayList<String>();

        List<String> High_Risk_List = new ArrayList<String>();

        RiskLevelDetails.put("High Risk", High_Risk_List);
        RiskLevelDetails.put("Medium Risk", Medium_Risk_List);
        RiskLevelDetails.put("Low Risk", Low_Risk_List);

        Cursor biofix_row_cursor = myDb.GetInsectBiofix(insect_key);
        Cursor degree_day_cursor = myDb.GetAllDegreeDaysForInsects(insect_key);
        int biofixCount = biofix_row_cursor.getCount();

        biofix_row_cursor.moveToFirst();
        degree_day_cursor.moveToFirst();
        for(int i =0; i < biofixCount; i++){

            int sprayDegreeDay = myDb.GetInsectSprayDay(Integer.parseInt(biofix_row_cursor.getString(4)));
            int accumulatedDegreeDay = Integer.parseInt(degree_day_cursor.getString(1));

            if(accumulatedDegreeDay >= sprayDegreeDay){
                High_Risk_List.add(myDb.GetOrchardName(Integer.parseInt(biofix_row_cursor.getString(5))));
            }
            else if(accumulatedDegreeDay >= sprayDegreeDay - 100){
                Medium_Risk_List.add(myDb.GetOrchardName(Integer.parseInt(biofix_row_cursor.getString(5))));
            }
            else{
                Low_Risk_List.add(myDb.GetOrchardName(Integer.parseInt(biofix_row_cursor.getString(5))));
            }

            biofix_row_cursor.moveToNext();
            degree_day_cursor.moveToNext();
        }
        biofix_row_cursor.close();
        degree_day_cursor.close();

        return RiskLevelDetails;

    }
}
