package com.example.myplants.Adapter;

import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplants.DB.FavDB;
import com.example.myplants.Mainpage;
import com.example.myplants.Models.CategoryItem;
import com.example.myplants.R;
import com.example.myplants.plantsDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class CategoryAdater extends RecyclerView.Adapter<CategoryAdater.ViewHolder> {


    private Context context;
    private ArrayList<CategoryItem> categoryItems;
    private FavDB favDB;



    public CategoryAdater( ArrayList<CategoryItem> categoryItems,Context context) {
        this.context = context;
        this.categoryItems = categoryItems;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        favDB = new FavDB(context);
        SharedPreferences preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStar = preferences.getBoolean("firstStart", true);
        if (firstStar) {
            createTableOnFirstStart();

        }
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_items_adapter, parent, false);

        return new ViewHolder(v);

    }


    @Override
    public void onBindViewHolder(@NonNull CategoryAdater.ViewHolder holder, int position) {

        final CategoryItem categoryItem = categoryItems.get(position);

        readCursorData(categoryItem, holder);

        holder.txt.setText(categoryItem.getName());
        holder.discText.setText(categoryItem.getDescribe());
        holder.img.setImageResource(categoryItem.getImage());



        holder.Favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                CategoryItem categoryItem =categoryItems.get(position);

                if (categoryItem.getFavStatus().equals("0")) {
                    categoryItem.setFavStatus("1");
                    favDB.insertIntoTheDatabase(categoryItem.getName(),
                            categoryItem.getImage(), categoryItem.getKey_id(), categoryItem.getFavStatus(),
                            categoryItem.getDescribe(),categoryItem.getWater(),categoryItem.getLight(),categoryItem.getFertilizer());
                    holder.Favbtn.setBackgroundResource(R.drawable.heart);

                } else {
                    categoryItem.setFavStatus("0");
                    favDB.remove_fav(categoryItem.getKey_id());
                    holder.Favbtn.setBackgroundResource(R.drawable.heart2);
                }
            }

        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, plantsDetails.class);
                intent.putExtra("txt", categoryItem.getName());
                intent.putExtra("id", categoryItem.getKey_id());
                intent.putExtra("FavStatue", categoryItem.getFavStatus());
                intent.putExtra("disc", categoryItem.getDescribe());
                intent.putExtra("water", categoryItem.getWater());
                intent.putExtra("sun", categoryItem.getLight());
                intent.putExtra("img", categoryItem.getImage());
                intent.putExtra("Fertilizer", categoryItem.getFertilizer());
                intent.putExtra("time", categoryItem.getDate());
                intent.putExtra("date", categoryItem.getTime());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    //Search

    public void filterList(ArrayList<CategoryItem> filteredList) {
        this.categoryItems = filteredList;
        notifyDataSetChanged();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

         TextView txt, discText;
         ImageView img;
         ImageView Favbtn;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.name);
            img = (ImageView) itemView.findViewById(R.id.img);
            discText = (TextView) itemView.findViewById(R.id.disc);
            Favbtn = itemView.findViewById(R.id.favBtn);



        }
    }


    ///databasse
    private void createTableOnFirstStart() {
        favDB.insertEmpty();

        SharedPreferences preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();

    }

    private void readCursorData(CategoryItem categoryItem, RecyclerView.ViewHolder viewHolder) {
        Cursor cursor = favDB.read_all_data(categoryItem.getKey_id());
        SQLiteDatabase db = favDB.getReadableDatabase();
        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String item_fav_statu = cursor.getString(cursor.getColumnIndex(FavDB.FAVORITE_STATUS));
                categoryItem.setFavStatus(item_fav_statu);


                if (item_fav_statu != null && item_fav_statu.equals("1")) {
                    viewHolder.itemView.findViewById(R.id.favBtn).setBackgroundResource(R.drawable.heart);

                } else if (item_fav_statu != null && item_fav_statu.equals("0")) {
                    viewHolder.itemView.findViewById(R.id.favBtn).setBackgroundResource(R.drawable.heart2);

                }
            }

        } finally {
            if (cursor != null && cursor.isClosed()) {
                cursor.close();
                db.close();
            }
        }
    }


}

