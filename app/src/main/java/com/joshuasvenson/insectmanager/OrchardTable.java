package com.joshuasvenson.insectmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.joshuasvenson.insectmanager.Home.myDb;

/**
 * Created by Joshua on 10/20/2016.
 */

public class OrchardTable {

   /* public static final String TAG = "Orchard";
    private SQLiteDatabase mDatabase;
    private Context mContext;
    private String[] mAllColumns = { myDb.COL_1,
            myDb.COL_2,
            myDb.COL_3,
            myDb.COL_4,
            myDb.COL_5,
            myDb.COL_6,
            myDb.COL_7,
            myDb.COL_8 };

    public OrchardTable(Context context){
        this.mContext = context;

        try{
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on opening database" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException{
        mDatabase = myDb.getWritableDatabase();
    }

    public void close(){
        myDb.close();
    }*/

    /*public Orchard createOrchard(String name, double latitude, double longitude, double trs, double crs, double plantHeight, double density){
        ContentValues values = new ContentValues();
        values.put(myDb.COL_2, name);
        values.put(myDb.COL_3, latitude);
        values.put(myDb.COL_4, longitude);
        values.put(myDb.COL_5, trs);
        values.put(myDb.COL_6, crs);
        values.put(myDb.COL_7, plantHeight);
        values.put(myDb.COL_8, density);
        long insertId = mDatabase.insert(myDb.ORCHARD_TABLE, null, values);
        Cursor cursor = mDatabase.query(myDb.ORCHARD_TABLE, mAllColumns, myDb.COL_1 + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Orchard newOrchard = cursorToOrchard(cursor);
        cursor.close();
        return newOrchard;
    }

    public void deleteOrchard(Orchard orchard) {
        long id = orchard.getId();
        mDatabase.delete(myDb.ORCHARD_TABLE, myDb.COL_1 + " = " + id, null);
    }

    public List<Orchard> getAllOrchards(){
        List<Orchard> listOrchards = new ArrayList<Orchard>();

        Cursor cursor = mDatabase.query(myDb.ORCHARD_TABLE, mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Orchard orchard = cursorToOrchard(cursor);
            listOrchards.add(orchard);
            cursor.moveToNext();
        }
        cursor.close();
        return listOrchards;
    }*/

}
