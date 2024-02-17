package com.example.myplants.Models;


public class FavItem {

    private String item_title,Describe,Water,Light,Fertilizer;
    private String key_id;
    String date,time;
    private int item_image;

    public FavItem() {
    }



    public FavItem(String item_title, String key_id, int item_image, String Describe, String Water, String Light, String Fertilizer) {
        this.item_title = item_title;
        this.key_id = key_id;
        this.item_image = item_image;
        this.Describe = Describe;
        this.Water = Water;
        this.Light = Light;
        this.Fertilizer = Fertilizer;


    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public int getItem_image() {
        return item_image;
    }

    public void setItem_image(int item_image) {
        this.item_image = item_image;
    }

    public String getDescribe() {
        return Describe;
    }

    public void setDescribe(String describe) {
        Describe = describe;
    }

    public String getWater() {
        return Water;
    }

    public void setWater(String water) {
        Water = water;
    }

    public String getLight() {
        return Light;
    }

    public void setLight(String light) {
        Light = light;
    }

    public String getFertilizer() {
        return Fertilizer;
    }




    public void setFertilizer(String fertilizer) {
        Fertilizer = fertilizer;
    }
}