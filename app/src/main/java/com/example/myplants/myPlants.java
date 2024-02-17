package com.example.myplants;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplants.Adapter.FavAdapter;
import com.example.myplants.DB.DBdateWater;
import com.example.myplants.DB.FavDB;
import com.example.myplants.Models.DatePlantsModel;
import com.example.myplants.Models.FavItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class myPlants extends AppCompatActivity implements FavAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private FavDB favDB;
    private List<FavItem> favItemList = new ArrayList<>();
    private FavAdapter favAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plants);


        ImageView arrowBack = findViewById(R.id.arrowBack1);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Mainpage.class);
                startActivity(intent);

            }
        });

        favDB = new FavDB(this);
        recyclerView = findViewById(R.id.recyclerviewFav);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadData();
        favAdapter = new FavAdapter(this, this, favItemList); // listener (this) is for the button click get it from the adapter
        recyclerView.setAdapter(favAdapter);


    }


    @Override
    public void onButtonClick(int position) {


        // for the date textBtn in FavItems
        //the button we got from the adapter with interface methods
    }




    //for disply the data from database
    private void loadData() {
        if (favItemList != null) {
            favItemList.clear();
        }
        SQLiteDatabase db = favDB.getReadableDatabase();
        Cursor cursor = favDB.select_all_favorite_list();
        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_TITLE));
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(FavDB.KEY_ID));
                @SuppressLint("Range") int image = Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavDB.ITEM_IMAGE)));
                @SuppressLint("Range") String Describe = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_Describe));
                @SuppressLint("Range") String Water = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_Water));
                @SuppressLint("Range") String Light = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_Light));
                @SuppressLint("Range") String Fertilizer = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_Fertilizer));

                FavItem favItem = new FavItem(title, id, image, Describe, Water, Light, Fertilizer);

                favItemList.add(favItem);


            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }


    }


}



