package com.example.myplants.Models;

public class DatePlantsModel {
    String time;
    String Date;
    String title;
    String ID;


    public DatePlantsModel(){}
    public DatePlantsModel(String title,String time, String date) {
        this.time = time;
        Date = date;
        this.title = title;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
