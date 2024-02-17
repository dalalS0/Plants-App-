package com.example.myplants.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myplants.Models.DatePlantsModel;
import com.example.myplants.Models.FavItem;

import java.security.Key;
import java.util.ArrayList;

public class FavDB extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static String DATABASE_NAME = "FavPlant";
    private static String TABLE_NAME = "favoriteTable";
    public static String KEY_ID = "id";

    public static String ITEM_TITLE = "itemTitle";
    public static String ITEM_Describe = "itemDescribe";
    public static String ITEM_Water = "itemWater";
    public static String ITEM_Light = "itemLight";
    public static String ITEM_Fertilizer = "itemFertilizer";
    public static String ITEM_IMAGE = "itemImage";
    public static String FAVORITE_STATUS = "fStatus";



    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            +" id1 integer primary key autoincrement,"
            + KEY_ID + " TEXT," + ITEM_TITLE+ " TEXT,"
            + ITEM_Describe + " TEXT," + ITEM_Water+ " TEXT,"
            + ITEM_Light + " TEXT," + ITEM_Fertilizer+ " TEXT,"
            + ITEM_IMAGE + " TEXT," + FAVORITE_STATUS+" TEXT)";

    public FavDB(Context context) { super(context,DATABASE_NAME,null,DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // create empty table
    public void insertEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        // enter your value
        for (int x = 1; x < 11; x++) {
            cv.put(KEY_ID, x);
            cv.put(FAVORITE_STATUS, "0");

            db.insert(TABLE_NAME,null, cv);
        }
    }

    // insert data into database
    public void insertIntoTheDatabase(String item_title, int item_image,
                                      String id, String fav_status,String
                                              Describe,String Water, String Light, String Fertilizer) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_TITLE, item_title);
        cv.put(ITEM_IMAGE, item_image);
        cv.put(KEY_ID, id);
        cv.put(FAVORITE_STATUS, fav_status);
        cv.put(ITEM_Describe,Describe);
        cv.put(ITEM_Water,Water);
        cv.put(ITEM_Light,Light);
        cv.put(ITEM_Fertilizer,Fertilizer);
        db.insert(TABLE_NAME,null, cv);
        Log.d("FavDB Status", item_title + ", favstatus - "+fav_status+" - . " + cv);


    }
    // read all data
    public Cursor read_all_data(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME + " where " + KEY_ID+"="+id+"";
        return db.rawQuery(sql,null,null);
    }

    // remove line from database
    public void remove_fav(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_NAME + " SET  "+ FAVORITE_STATUS+" ='0' WHERE "+KEY_ID+"="+id+"";
        db.execSQL(sql);
        Log.d("remove", id.toString());

    }


    public Cursor select_all_favorite_list() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+FAVORITE_STATUS+" ='1'";
        return db.rawQuery(sql,null,null);
    }





}