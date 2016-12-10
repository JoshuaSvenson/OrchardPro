package com.joshuasvenson.insectmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.joshuasvenson.insectmanager.Home.myDb;

/**
 * Created by Joshua on 10/13/2016.
 */

/*
Name: DatabaseHelper
Description: This class provides the code for the Database functions
Layout File: none
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "orchard.db";

    //Orchard Table
    public static final String ORCHARD_TABLE = "orchard_table";
    public static final String ORCHARD_ID = "ID";
    public static final String ORCHARD_NAME = "NAME";
    public static final String ORCHARD_CRS = "CROSS_ROW_SPREAD";
    public static final String ORCHARD_DENSITY = "DENSITY_FACTOR";
    public static final String ORCHARD_LATITUDE = "LATITUDE";
    public static final String ORCHARD_LONGITUDE = "LONGITUDE";
    public static final String ORCHARD_STATION = "WEATHER_STATION";
    public static final String ORCHARD_PLANT_HEIGHT = "PLANT_HEIGHT";
    public static final String ORCHARD_TRS = "TREE_ROW_SPACING";
    public static final String ORCHARD_KEY_IMAGE = "IMAGE_DATA";

    //Biofix Table
    public static final String BIOFIX_TABLE = "biofix_table";
    public static final String BIOFIX_ID = "BIOFIX_ID";
    public static final String BIOFIX_DATE_DAY = "BIOFIX_DATE_DAY";
    public static final String BIOFIX_DATE_MONTH = "BIOFIX_DATE_MONTH";
    public static final String BIOFIX_DATE_YEAR = "BIOFIX_DATE_YEAR";
    public static final String BIOFIX_DEGREE_DAYS = "BIOFIX_DEGREE_DAYS";
    public static final String BIOFIX_LAST_UPDATE_DAY = "BIOFIX_LAST_UPDATE_DAY";
    public static final String BIOFIX_LAST_UPDATE_MONTH = "BIOFIX_LAST_UPDATE_MONTH";
    public static final String BIOFIX_LAST_UPDATE_YEAR = "BIOFIX_LAST_UPDATE_YEAR";
    public static final String BIOFIX_INSECT_FOREIGN_KEY = "BIOFIX_INSECT_FOREIGN_KEY";
    public static final String BIOFIX_ORCHARD_FOREIGN_KEY = "BIOFIX_ORCHARD_FOREIGN_KEY";

    //Insect Table
    public static final String INSECT_TABLE = "insect_table";
    public static final String INSECT_ID = "INSECT_ID";
    public static final String INSECT_NAME = "INSECT_NAME";
    public static final String INSECT_LOW_THRESH = "INSECT_LOW_THRESH";
    public static final String INSECT_HIGH_THRESH = "INSECT_HIGH_THRESH";
    public static final String INSECT_FIRST_DD = "INSECT_FIRST_DD";

    //Settings Table
    public static final String SETTINGS_TABLE = "settings_table";
    public static final String SETTINGS_DATE_DAY = "SETTINGS_DATE_DAY";
    public static final String SETTINGS_DATE_MONTH = "SETTINGS_DATE_MONTH";
    public static final String SETTINGS_DATE_YEAR = "SETTINGS_DATE_YEAR";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    /*
    Name: onCreate
    Description: Creates the activity
    Parameters: SQLiteDatabase db
    Returns: void
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create orchard table
        db.execSQL("create table " +ORCHARD_TABLE +
                "(ID INTEGER PRIMARY KEY, " +
                "NAME TEXT," +
                "CROSS_ROW_SPREAD DOUBLE," +
                "DENSITY_FACTOR DOUBLE," +
                "LATITUDE DOUBLE," +
                "LONGITUDE DOUBLE," +
                "PLANT_HEIGHT DOUBLE," +
                "TREE_ROW_SPACING DOUBLE," +
                "WEATHER_STATION TEXT," +
                "IMAGE_DATA BLOB)");
        //create biofix table
        db.execSQL("create table " +BIOFIX_TABLE +
                "(BIOFIX_ID INTEGER PRIMARY KEY, " +
                "BIOFIX_DATE_DAY INTEGER," +
                "BIOFIX_DATE_MONTH INTEGER," +
                "BIOFIX_DATE_YEAR INTEGER," +
                "BIOFIX_DEGREE_DAYS DOUBLE," +
                "BIOFIX_LAST_UPDATE_DAY INTEGER," +
                "BIOFIX_LAST_UPDATE_MONTH INTEGER," +
                "BIOFIX_LAST_UPDATE_YEAR INTEGER," +
                "BIOFIX_INSECT_FOREIGN_KEY INTEGER," +
                "BIOFIX_ORCHARD_FOREIGN_KEY INTEGER)");
        //create insect table
        db.execSQL("create table " +INSECT_TABLE +
                "(INSECT_ID INTEGER PRIMARY KEY, " +
                "INSECT_NAME TEXT," +
                "INSECT_LOW_THRESH DOUBLE," +
                "INSECT_HIGH_THRESH DOUBLE," +
                "INSECT_FIRST_DD)");
        //create settings table
        db.execSQL("create table " +SETTINGS_TABLE +
                "(SETTINGS_ID INTEGER, " +
                "SETTINGS_DATE_DAY INTEGER, " +
                "SETTINGS_DATE_MONTH INTEGER, " +
                "SETTINGS_DATE_YEAR INTEGER)");
    }

    /*
    Name: onUpgrade
    Description: Drops all tables effectively resetting the databse
    Parameters: SQLiteDatabase db:
                int oldVersion:
                int newVersion:
    Returns: void
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +ORCHARD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +BIOFIX_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +INSECT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +SETTINGS_TABLE);
        onCreate(db);
    }

    /*
    Name: createOrchard
    Description: creates row for new orchard in database
    Parameters: the values for the orchard
    Returns: boolean
     */
    public boolean createOrchard(String name, double latitude, double longitude, String stationID, double trs, double crs, double plantHeight, double density, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ORCHARD_NAME, name);
        contentValues.put(ORCHARD_CRS, crs);
        contentValues.put("DENSITY_FACTOR", density);
        contentValues.put(ORCHARD_LATITUDE, latitude);
        contentValues.put(ORCHARD_LONGITUDE, longitude);
        contentValues.put(ORCHARD_PLANT_HEIGHT, plantHeight);
        contentValues.put(ORCHARD_TRS, trs);
        contentValues.put(ORCHARD_STATION, stationID);
        contentValues.put(ORCHARD_KEY_IMAGE, image);
        long result = db.insert(ORCHARD_TABLE, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    /*
    Name: createBiofix
    Description: creates row for new biofix in database
    Parameters: the values for the biofix
    Returns: long - representing successful entry
     */
    public long createBiofix(String date_day, String date_month, String date_year, int degree_days,
                                String last_update_day, String last_update_month, String last_update_year,
                                int orchard_key, int insect_key){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BIOFIX_DATE_DAY", date_day);
        contentValues.put("BIOFIX_DATE_MONTH", date_month);
        contentValues.put("BIOFIX_DATE_YEAR", date_year);
        contentValues.put("BIOFIX_DEGREE_DAYS", degree_days);
        contentValues.put("BIOFIX_LAST_UPDATE_DAY", last_update_day);
        contentValues.put("BIOFIX_LAST_UPDATE_MONTH", last_update_month);
        contentValues.put("BIOFIX_LAST_UPDATE_YEAR", last_update_year);
        contentValues.put("BIOFIX_INSECT_FOREIGN_KEY", insect_key);
        contentValues.put("BIOFIX_ORCHARD_FOREIGN_KEY", orchard_key);
        long result = db.insert("biofix_table", null, contentValues);
        if(result == -1)
            return -1;
        else
            return result;
    }

    /*
    Name: createInsect
    Description: creates row for new insect in database
    Parameters: the values for the insect
    Returns: boolean - representing successful entry
     */
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

    /*
    Name: createSettings
    Description: creates row for new setting in database
    Parameters: the values for the setting
    Returns: boolean - representing successful entry
     */
    public boolean createSettings(int day, int month, int year){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("SETTINGS_ID", 0);
        contentValues.put("SETTINGS_DATE_DAY", day);
        contentValues.put("SETTINGS_DATE_MONTH", month);
        contentValues.put("SETTINGS_DATE_YEAR", year);

        long result = db.insert(SETTINGS_TABLE, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    /*
    Name: query_row
    Description: gets information from orchard table from the name of orchard
    Parameters: String orchardName: the name of the orchard
    Returns: Cursor pointing to the row that was retrieved
     */
    public Cursor query_row(String orchardName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " +ORCHARD_TABLE + " where NAME = ?", new String[] {orchardName});
        return result;
    }

    ////////////////////////////////////////////////////////////////
    // Multiple Table Functions
    ////////////////////////////////////////////////////////////////

    /*
    Name: deleteOrchardData
    Description: Deletes orchard data including biofixes
    Parameters: String orchardKey: the key of the orchard to delete
    Returns: void
     */
    public void deleteOrchardData(String orchardKey){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " +ORCHARD_TABLE + " where ID = ?", new String[] {orchardKey});
        db.execSQL("delete from " +BIOFIX_TABLE + " where BIOFIX_ORCHARD_FOREIGN_KEY = ?", new String[] {orchardKey});
    }

    ////////////////////////////////////////////////////////////////
    // Orchard Table Functions
    ////////////////////////////////////////////////////////////////

    /*
    Name: GetImage
    Description: Gets image data from orchard table from orchard key
    Parameters: String orchardKey: the key of the orchard to get image for
    Returns: byte array of the image
     */
    public byte[] GetImage (String orchardKey){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select IMAGE_DATA from " + ORCHARD_TABLE + " where ID = ?", new String[] {orchardKey});
        result.moveToFirst();
        return result.getBlob(0);
    }

    /*
    Name: addImage
    Description: adds image to the database for given orchard
    Parameters: String orchardKey: the key of the orchard to add image for
                byte[] image: the image to insert
    Returns: void
     */
    public void addImage (String orchardKey, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("IMAGE_DATA", image);

        db.update(ORCHARD_TABLE, cv, "ID=" + orchardKey, null);
    }

    /*
    Name: GetOrchardSettings
    Description: Get all entries from requested orchard
    Parameters: String orchardKey: the key of the orchard to get data for
    Returns: Cursor to the row containing all data
     */
    public Cursor GetOrchardSettings(String orchardKey){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " +ORCHARD_TABLE + " where ID = ?", new String[] {orchardKey});
        return result;
    }

    /*
    Name: getAllNameData
    Description: Gets all orchard names from orchard table
    Parameters: none
    Returns: Cursor to all rows which contain name data of all orchards
     */
    public Cursor getAllNameData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select NAME from " +ORCHARD_TABLE, null);
        return result;
    }

    /*
    Name: GetOrchardName
    Description: Gets the name of an orchard given a key
    Parameters: String orchardKey: the key of the orchard to get name for
    Returns: String -  the name of the orchard
     */
    public String GetOrchardName(int orchardKey){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select NAME from " +ORCHARD_TABLE + " where ID = "+orchardKey, null);
        result.moveToFirst();
        return String.valueOf(result.getString(0));
    }

    /*
    Name: GetOrchardLatitude
    Description: Gets the latitude of an orchard given a key
    Parameters: String orchardKey: the key of the orchard to get latitude for
    Returns: String -  the latitude of the orchard
     */
    public String GetOrchardLatitude(int orchardKey){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select LATITUDE from " +ORCHARD_TABLE + " where ID = "+orchardKey, null);
        result.moveToFirst();
        return String.valueOf(result.getString(0));
    }

    /*
    Name: GetOrchardLongitude
    Description: Gets the Longitude of an orchard given a key
    Parameters: String orchardKey: the key of the orchard to get Longitude for
    Returns: String -  the Longitude of the orchard
     */
    public String GetOrchardLongitude(int orchardKey){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select LONGITUDE from " +ORCHARD_TABLE + " where ID = "+orchardKey, null);
        result.moveToFirst();
        return String.valueOf(result.getString(0));
    }

    /*
    Name: updateData
    Description: update all orchard settings data
    Parameters: all data for orchard to update
    Returns: boolean representing successful update
     */
    public boolean updateData(String orchardKey, String name, String latitude, String longitude, String station, String trs, String crs, String plantHeight, String density) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ORCHARD_NAME, name);
        contentValues.put(ORCHARD_CRS, crs);
        contentValues.put(ORCHARD_DENSITY, density);
        contentValues.put(ORCHARD_LATITUDE, latitude);
        contentValues.put(ORCHARD_LONGITUDE, longitude);
        contentValues.put(ORCHARD_PLANT_HEIGHT, plantHeight);
        contentValues.put(ORCHARD_TRS, trs);
        contentValues.put(ORCHARD_STATION, station);
        db.update(ORCHARD_TABLE, contentValues, "ID = ?", new String[]{orchardKey});
        return true;
    }

    ////////////////////////////////////////////////////////////////
    // Biofix Table Functions
    ////////////////////////////////////////////////////////////////

    /*
    Name: GetOrchardBiofix
    Description: Get all biofix data for given orchard
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
    Description: Get all biofix data for given insect
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
    Description: Get insect key and degree days for given orchard
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
    Description: Get insect key and degree days for given insect
    Parameters: The key of the insect that we want accumulated degree days for
    Return Value: A Cursor to all
     */
    public Cursor GetAllDegreeDaysForInsects(String insectKey){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select BIOFIX_ORCHARD_FOREIGN_KEY, BIOFIX_DEGREE_DAYS from " +BIOFIX_TABLE + " where BIOFIX_INSECT_FOREIGN_KEY = " +insectKey, null);
        return result;
    }

    /*
    Name: GetBiofixDate
    Description: Get the date of the biofix for orchard
    Parameters: String orchardKey: the key of the orchard we want biofix date for
    Return Value: A Cursor to all
     */
    public Cursor GetBiofixDate(String orchardKey){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select BIOFIX_DATE_DAY, BIOFIX_DATE_MONTH, BIOFIX_DATE_YEAR from " +BIOFIX_TABLE +
                " where BIOFIX_ORCHARD_FOREIGN_KEY = ?"
                , new String[]{orchardKey});
        return result;
    }

    /*
    Name: SetDegreeDay
    Description: sets degree day in biofix table and updates date
    Parameters: String biofixID:
                double degree_days:
    Return Value: boolean representing successful update
     */
    public boolean SetDegreeDay(String biofixID, double degree_days){
        SQLiteDatabase db = this.getWritableDatabase();

        double old_degree_days;

        Cursor result = GetSettingsDate();
        result.moveToFirst();

        String setYear = String.valueOf(result.getString(2));
        String setMonth = String.valueOf(result.getString(1));
        String setDay = String.valueOf(result.getString(0));

        old_degree_days = Double.parseDouble(GetDegreeDay(biofixID));

        degree_days += old_degree_days;

        //Update date last updated
        ContentValues contentValues = new ContentValues();
        contentValues.put("BIOFIX_DEGREE_DAYS", degree_days);
        contentValues.put("BIOFIX_LAST_UPDATE_DAY", setDay);
        contentValues.put("BIOFIX_LAST_UPDATE_MONTH", setMonth);
        contentValues.put("BIOFIX_LAST_UPDATE_YEAR", setYear);
        db.update(BIOFIX_TABLE, contentValues, "BIOFIX_ID = ?", new String[]{biofixID});
        return true;
    }

    /*
    Name: GetDegreeDay
    Description: gets degree day in biofix table for specific biofix ID
    Parameters: String biofixID: ID of degree days to get
    Return Value: String of degree days value
     */
    public String GetDegreeDay(String biofixID){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery("select BIOFIX_DEGREE_DAYS from " + BIOFIX_TABLE +
                " where BIOFIX_ID = ?", new String[]{biofixID});

        result.moveToFirst();
        return result.getString(0);
    }

    /*
    Name: GetBiofx
    Description: Gets specific biofix given its ID
    Parameters: String biofixID: ID of biofix to get
    Return Value: Cursor to the biofix retrieved
     */
    public Cursor GetBiofix(String biofixID){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery("select * from " + BIOFIX_TABLE +
                " where BIOFIX_ID = ?", new String[]{biofixID});

        result.moveToFirst();
        return result;
    }

    /*
    Name: deleteBiofixData
    Description: deletes row of given biofix ID
    Parameters: String biofixID: ID of biofix to delete
    Return Value: void
     */
    public void deleteBiofixData(String biofixID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " +BIOFIX_TABLE + " where BIOFIX_ID = ?", new String[] {biofixID});
    }

    ////////////////////////////////////////////////////////////////
    // Insect Table Functions
    ////////////////////////////////////////////////////////////////

    /*
    Name: GetInsectNames
    Description: Get name of all insects in table
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
    Description: Get name of insect given insect key
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
    Description: Get degree days for when specific insect is at risk to the orchard
    Parameters: int insectKey
    Return Value: An integer of the degree day value the insect needs to be sprayed at
     */
    public int GetInsectSprayDay(int insectKey){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select INSECT_FIRST_DD from " +INSECT_TABLE + " where INSECT_ID = "+insectKey, null);
        result.moveToFirst();

        return Integer.parseInt(result.getString(0));
    }

    /*
    Name: GetInsectLowerThreshTemp
    Description: Get lower temperature threshold for particular insect
    Parameters: int insectKey
    Return Value: int of temperature
     */
    public int GetInsectLowerThreshTemp(String insectKey){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select INSECT_LOW_THRESH from " +INSECT_TABLE + " where INSECT_ID = "+insectKey, null);
        result.moveToFirst();

        return Integer.parseInt(result.getString(0));
    }

    ////////////////////////////////////////////////////////////////
    // Settings Table Functions
    ////////////////////////////////////////////////////////////////

    /*
    Name: GetSettingDate
    Description: Get the current set date
    Parameters: None
    Return Value: A string of the date set in settings
     */
    public Cursor GetSettingsDate(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select SETTINGS_DATE_DAY, SETTINGS_DATE_MONTH, SETTINGS_DATE_YEAR from "
                +SETTINGS_TABLE, null);
        return result;
    }

    /*
    Name: SetSettingDate
    Description: set the date you want the app to think it is
    Parameters: String date
    Return Value: boolean
     */
    public boolean SetSettingsDate(String day, String month, String year){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SETTINGS_DATE_DAY", day);
        contentValues.put("SETTINGS_DATE_MONTH", month);
        contentValues.put("SETTINGS_DATE_YEAR", year);
        db.update(SETTINGS_TABLE, contentValues, "SETTINGS_ID = ?", new String[]{"0"});
        return true;
    }
}
