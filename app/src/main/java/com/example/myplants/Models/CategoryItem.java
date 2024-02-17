package com.example.myplants.Models;

public class CategoryItem {
    public String name,describe,water,light;
    public int image;
    String favStatus;
    String key_id;
    private String date,time;

    String Fertilizer;



    public CategoryItem(int image, String name, String key_id, String favStatus) {
        this.name = name;
        this.image = image;
        this.favStatus = favStatus;
        this.key_id = key_id;
    }

    public CategoryItem(String name) {
        this.name = name;
    }

    public String getFertilizer() {
        return Fertilizer;
    }

    public void setFertilizer(String fertilizer) {
        this.Fertilizer = fertilizer;
    }


    public String getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(String favStatus) {
        this.favStatus = favStatus;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public CategoryItem(String name, int image, String describe, String water, String light, String favStatus, String key_id, String fertilizer) {
        this.name = name;
        this.describe = describe;
        this.water = water;
        this.light = light;
        this.image = image;
        this.favStatus = favStatus;
        this.key_id = key_id;
        Fertilizer = fertilizer;

    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        time = time;
    }


}
