package com.example.myplants.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myplants.Models.DatePlantsModel;

import java.util.ArrayList;


public class DBdateWater extends SQLiteOpenHelper {


    private static int DB_VERSION = 1;
    private static String DATABASE_NAME = "remainderPlant";
    private static String TABLE_NAME = "remainderTable";

    public static String ITEM_TITLE = "itemTitle";

    public static String Date_Water = "dateWater";
    public static String Time_Water = "timeWater";
    SQLiteDatabase database;

    // dont forget write this spaces
    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + " id integer primary key autoincrement,"
            + ITEM_TITLE + " TEXT," + Date_Water + " TEXT," + Time_Water + " TEXT)";

    public DBdateWater(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public String addreminder(String title, String date, String time) {
        SQLiteDatabase database = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("itemTitle", title);                                                          //Inserts  data into sqllite database
        contentValues.put("dateWater", date);
        contentValues.put("timeWater", time);

        float result = database.insert("remainderTable", null, contentValues);    //returns -1 if data successfully inserts into database

        if (result == -1) {
            return "Failed";
        } else {
            return "Successfully inserted";
        }
    }


    public ArrayList<DatePlantsModel> readallreminders() {
        ArrayList<DatePlantsModel> orders = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select id,itemTitle,dateWater,timeWater from remainderTable", null);


        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                DatePlantsModel model = new DatePlantsModel();
                model.setID(cursor.getInt(0) + "");
                model.setTitle(cursor.getString(1));
                model.setDate(cursor.getString(2));
                model.setTime(cursor.getString(3));
                orders.add(model);


            }
        }
        cursor.close();
        db.close();
        return orders;
    }


    public int deletedData(String id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.delete(TABLE_NAME, "id=" + id, null);


    }

}