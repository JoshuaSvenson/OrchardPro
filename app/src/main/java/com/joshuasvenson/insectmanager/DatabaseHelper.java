package com.joshuasvenson.insectmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Joshua on 10/13/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "orchard.db";

    public static final String ORCHARD_TABLE = "orchard_table";
    public static final String ORCHARD_ID = "ID";
    public static final String ORCHARD_NAME = "NAME";
    public static final String ORCHARD_CRS = "CROSS_ROW_SPREAD";
    public static final String ORCHARD_DENSITY = "DENSITY_FACTOR";
    public static final String ORCHARD_LATITUDE = "LATITUDE";
    public static final String ORCHARD_LONGITUDE = "LONGITUDE";
    public static final String ORCHARD_PLANT_HEIGHT = "PLANT_HEIGHT";
    public static final String ORCHARD_TRS = "TREE_ROW_SPACING";

    public static final String BIOFIX_TABLE = "biofix_table";
    public static final String BIOFIX_ID = "BIOFIX_ID";
    public static final String BIOFIX_DATE = "BIOFIX_DATE";
    public static final String BIOFIX_DEGREE_DAYS = "BIOFIX_DEGREE_DAYS";
    public static final String BIOFIX_LAST_UPDATE = "BIOFIX_LAST_UPDATE";
    public static final String BIOFIX_INSECT_FOREIGN_KEY = "BIOFIX_INSECT_FOREIGN_KEY";
    public static final String BIOFIX_ORCHARD_FOREIGN_KEY = "BIOFIX_ORCHARD_FOREIGN_KEY";

    public static final String INSECT_TABLE = "insect_table";
    public static final String INSECT_ID = "INSECT_ID";
    public static final String INSECT_NAME = "INSECT_NAME";
    public static final String INSECT_LOW_THRESH = "INSECT_LOW_THRESH";
    public static final String INSECT_HIGH_THRESH = "INSECT_HIGH_THRESH";
    public static final String INSECT_FIRST_DD = "INSECT_FIRST_DD";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +ORCHARD_TABLE +
                "(ID INTEGER PRIMARY KEY, " +
                "NAME TEXT," +
                "CROSS_ROW_SPREAD DOUBLE," +
                "DENSITY_FACTOR DOUBLE," +
                "LATITUDE DOUBLE," +
                "LONGITUDE DOUBLE," +
                "PLANT_HEIGHT DOUBLE," +
                "TREE_ROW_SPACING DOUBLE)");
        db.execSQL("create table " +BIOFIX_TABLE +
                "(BIOFIX_ID INTEGER PRIMARY KEY, " +
                "BIOFIX_DATE DATETIME," +
                "BIOFIX_DEGREE_DAYS DOUBLE," +
                "BIOFIX_LAST_UPDATE DATETIME," +
                "BIOFIX_INSECT_FOREIGN_KEY INTEGER," +
                "BIOFIX_ORCHARD_FOREIGN_KEY INTEGER)");
        db.execSQL("create table " +INSECT_TABLE +
                "(INSECT_ID INTEGER PRIMARY KEY, " +
                "INSECT_NAME TEXT," +
                "INSECT_LOW_THRESH DOUBLE," +
                "INSECT_HIGH_THRESH DOUBLE," +
                "INSECT_FIRST_DD)");
        /*createInsect("Codling Moth", 50.0, 88.0, 225.0);
        createInsect("Apple Maggot", 50.0, -1, 1000.0);
        createInsect("Rosy Apple Aphid", 40.0, -1, 200.0);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +ORCHARD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +BIOFIX_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +INSECT_TABLE);
        onCreate(db);
    }

    public boolean insertData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ORCHARD_NAME, name);
        long result = db.insert(ORCHARD_TABLE, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertData2(String col, String row, String str_val, double double_val){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if(str_val != null){
            contentValues.put(col, str_val);
        }
        else {
            contentValues.put(col, double_val);
        }
        db.rawQuery("update " + ORCHARD_TABLE + "set " + col + "= " + str_val + "where " + col + "= NULL", null);
        return true;
    }

    public boolean createOrchard(String name, double latitude, double longitude, double trs, double crs, double plantHeight, double density){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ORCHARD_NAME, name);
        contentValues.put(ORCHARD_CRS, crs);
        contentValues.put("DENSITY_FACTOR", density);
        contentValues.put(ORCHARD_LATITUDE, latitude);
        contentValues.put(ORCHARD_LONGITUDE, longitude);
        contentValues.put(ORCHARD_PLANT_HEIGHT, plantHeight);
        contentValues.put(ORCHARD_TRS, trs);
        long result = db.insert(ORCHARD_TABLE, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean createBiofix(String date, int degree_days, String last_update, int orchard_key, int insect_key){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BIOFIX_DATE", date);
        contentValues.put("BIOFIX_DEGREE_DAYS", degree_days);
        contentValues.put("BIOFIX_LAST_UPDATE", last_update);
        contentValues.put("BIOFIX_INSECT_FOREIGN_KEY", insect_key);
        contentValues.put("BIOFIX_ORCHARD_FOREIGN_KEY", orchard_key);
        long result = db.insert("biofix_table", null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean createInsect(String name, double low_temp, double high_temp, double first_DD){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("INSECT_NAME", name);
        contentValues.put("INSECT_LOW_THRESH", low_temp);
        contentValues.put("INSECT_HIGH_THRESH", high_temp);
        contentValues.put("INSECT_FIRST_DD", first_DD);

        Cursor cursor = db.rawQuery("select 1 from insect_table where INSECT_NAME=?",
                new String[] { name });
        boolean exists = (cursor.getCount() > 0);
        cursor.close();

        if(exists != true) {
            long result = db.insert("insect_table", null, contentValues);
            if (result == -1)
                return false;
            else
                return true;
        }
        return false;
    }


    public Cursor getAllNameData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select NAME from " +ORCHARD_TABLE, null);
        return result;
    }

    public Cursor query_row(String orchardName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " +ORCHARD_TABLE + " where NAME = ?", new String[] {orchardName});
        return result;
    }

    public void deleteOrchard(String orchardName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " +ORCHARD_TABLE + " where NAME = ?", new String[] {orchardName});
    }

    public Cursor query_names(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select NAME from " +ORCHARD_TABLE, null);
        return result;
    }

    public Cursor query_biofix_row(int orchardKey){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " +BIOFIX_TABLE, null);// + " where BIOFIX_ORCHARD_FOREIGN_KEY = ?", new String[] {String.valueOf(orchardKey)});
        return result;
    }

    public Cursor getBiofixData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select BIOFIX_DATE from " +BIOFIX_TABLE, null);
        return result;
    }

    ////////////////////////////////////////////////////////////////
    // Multiple Table Functions
    ////////////////////////////////////////////////////////////////

    public void deleteOrchardData(String orchardKey){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " +ORCHARD_TABLE + " where ID = ?", new String[] {orchardKey});
        db.execSQL("delete from " +BIOFIX_TABLE + " where BIOFIX_ORCHARD_FOREIGN_KEY = ?", new String[] {orchardKey});
    }

    ////////////////////////////////////////////////////////////////
    // Orchard Table Functions
    ////////////////////////////////////////////////////////////////

    public Cursor GetOrchardSettings(String orchardKey){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " +ORCHARD_TABLE + " where ID = ?", new String[] {orchardKey});
        return result;
    }

    ////////////////////////////////////////////////////////////////
    // Biofix Table Functions
    ////////////////////////////////////////////////////////////////

    /*
    Name: GetOrchardBiofix
    Description:
    Parameters: The key of the orchard that we want biofix data for
    Return Value: A Cursor to all rows from biofix table where orchard key is equal to the parameter
     */
    public Cursor GetOrchardBiofix(String orchardKey){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " +BIOFIX_TABLE + " where BIOFIX_ORCHARD_FOREIGN_KEY = "+orchardKey, null);
        return result;
    }

    /*
    Name: GetInsectBiofix
    Description:
    Parameters: The key of the insect that we want biofix data for
    Return Value: A Cursor to all rows from biofix table where insect key is equal to the parameter
     */
    public Cursor GetInsectBiofix(String insectKey){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " +BIOFIX_TABLE + " where BIOFIX_INSECT_FOREIGN_KEY = "+insectKey, null);
        return result;
    }

    /*
    Name: GetAllDegreeDaysForOrchard
    Description:
    Parameters: The key of the orchard that we want accumulated degree days for
    Return Value: A Cursor to all
     */
    public Cursor GetAllDegreeDaysForOrchard(String orchardKey){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select BIOFIX_INSECT_FOREIGN_KEY, BIOFIX_DEGREE_DAYS from " +BIOFIX_TABLE + " where BIOFIX_ORCHARD_FOREIGN_KEY = " +orchardKey, null);
        return result;
    }

    /*
    Name: GetAllDegreeDaysForInsect
    Description:
    Parameters: The key of the insect that we want accumulated degree days for
    Return Value: A Cursor to all
     */
    public Cursor GetAllDegreeDaysForInsects(String insectKey){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select BIOFIX_ORCHARD_FOREIGN_KEY, BIOFIX_DEGREE_DAYS from " +BIOFIX_TABLE + " where BIOFIX_INSECT_FOREIGN_KEY = " +insectKey, null);
        return result;
    }

    ////////////////////////////////////////////////////////////////
    // Insect Table Functions
    ////////////////////////////////////////////////////////////////

    /*
    Name: GetInsectNames
    Description:
    Parameters: None
    Return Value: A Cursor to all columns containing only names of insects
     */
    public Cursor GetInsectNames(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select INSECT_NAME from " +INSECT_TABLE, null);
        return result;
    }

    /*
    Name: GetInsectName
    Description:
    Parameters: int insectKey
    Return Value: A String of the name of the insect
     */
    public String GetInsectName(int insectKey){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select INSECT_NAME from " +INSECT_TABLE + " where INSECT_ID = "+insectKey, null);
        result.moveToFirst();
        return String.valueOf(result.getString(0));
    }

    /*
    Name: GetInsectSprayDay
    Description:
    Parameters: int insectKey
    Return Value: An integer of the degree day value the insect needs to be sprayed at
     */
    public int GetInsectSprayDay(int insectKey){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select INSECT_FIRST_DD from " +INSECT_TABLE + " where INSECT_ID = "+insectKey, null);
        result.moveToFirst();

        return Integer.parseInt(result.getString(0));
    }



}
