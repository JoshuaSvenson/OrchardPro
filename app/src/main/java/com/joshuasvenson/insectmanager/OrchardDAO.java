package com.joshuasvenson.insectmanager;

/**
 * Created by Joshua on 10/23/2016.
 */

public class OrchardDAO {

    int id;
    String name;
    double latitude;
    double longitude;
    double tree_row_spacing;
    double cross_row_spread;
    double plant_height;
    double density;

    public OrchardDAO() {

    }

    public OrchardDAO(String name, double latitude, double longitude, double tree_row_spacing, double cross_row_spread,
                      double plant_height, double density){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tree_row_spacing = tree_row_spacing;
        this.cross_row_spread = cross_row_spread;
        this.plant_height = plant_height;
        this.density = density;
    }

    public OrchardDAO(int id, String name, double latitude, double longitude, double tree_row_spacing, double cross_row_spread,
                      double plant_height, double density){
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tree_row_spacing = tree_row_spacing;
        this.cross_row_spread = cross_row_spread;
        this.plant_height = plant_height;
        this.density = density;
    }

    // Setters
    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public void setTRS(double tree_row_spacing){
        this.tree_row_spacing = tree_row_spacing;
    }

    public void setCRS(double cross_row_spread){
        this.cross_row_spread = cross_row_spread;
    }

    public void setPlantHeight(double plant_height){
        this.plant_height = plant_height;
    }

    public void setDensity(double density){
        this.density = density;
    }

    // Getters
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getTRS(){
        return tree_row_spacing;
    }

    public double getCRS(){
        return cross_row_spread;
    }

    public double getPlantHeight(){
        return plant_height;
    }

    public double getDensity(){
        return density;
    }
}
