package com.example.myplants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.model.Model;
import com.example.myplants.Adapter.AdapterReminder;
import com.example.myplants.DB.DBdateWater;
import com.example.myplants.DB.FavDB;
import com.example.myplants.Models.DatePlantsModel;

import java.util.ArrayList;

public class WaterPlant extends AppCompatActivity {
    AdapterReminder adapter;
    RecyclerView recPlantDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_plant);
        recPlantDate = findViewById(R.id.recPlantDate);
        TextView noWater = findViewById(R.id.noWater);


        ImageView arrowBack = findViewById(R.id.arrowBack2);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Mainpage.class);
                startActivity(intent);

            }
        });



       DBdateWater favDB = new DBdateWater(this);                  //Cursor To Load data From the database
        ArrayList<DatePlantsModel> orderModels = favDB.readallreminders();


        if(orderModels.isEmpty()){
            noWater.setVisibility(View.VISIBLE);
        }else{
            noWater.setVisibility(View.GONE);

        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recPlantDate.setLayoutManager(layoutManager);

        adapter = new AdapterReminder(orderModels,this);
        recPlantDate.setAdapter(adapter);





    }

}