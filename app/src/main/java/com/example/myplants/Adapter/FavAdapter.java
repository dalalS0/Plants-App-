package com.example.myplants.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplants.DB.DBdateWater;
import com.example.myplants.DB.FavDB;
import com.example.myplants.Models.DatePlantsModel;
import com.example.myplants.Models.FavItem;
import com.example.myplants.R;
import com.example.myplants.plantsDetails;

import java.util.ArrayList;
import java.util.List;



public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    private Context context;
    private List<FavItem> favItemList;
    private FavDB favDB;


    private OnItemClickListener listener;

    // Define the interface
    public interface OnItemClickListener {
        void onButtonClick(int position);
    }

    // Constructor to receive the interface

    //
    public FavAdapter(OnItemClickListener listener,Context context, List<FavItem> favItemList) {
        this.context = context;
        this.favItemList = favItemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_item,
                parent, false);
        favDB = new FavDB(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.favTextView.setText(favItemList.get(position).getItem_title());
        holder.favImageView.setImageResource(favItemList.get(position).getItem_image());
        holder.FavDisc.setText(favItemList.get(position).getDescribe());








        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, plantsDetails.class);
                intent.putExtra("txt", favItemList.get(position).getItem_title());
                intent.putExtra("disc",favItemList.get(position).getDescribe());
                intent.putExtra("water", favItemList.get(position).getWater());
                intent.putExtra("sun", favItemList.get(position).getLight());
                intent.putExtra("Fertilizer", favItemList.get(position).getFertilizer());
                intent.putExtra("img",favItemList.get(position).getItem_image());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return favItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView favTextView,FavDisc;
        ImageView favBtn;
        ImageView favImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favTextView = itemView.findViewById(R.id.Favname);
            favBtn = itemView.findViewById(R.id.favBtn1);
            favImageView = itemView.findViewById(R.id.Favimg);
            FavDisc = itemView.findViewById(R.id.disc1);



            //remove from fav after click
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final FavItem favItem = favItemList.get(position);
                    favDB.remove_fav(favItem.getKey_id());
                    removeItem(position);

                }
            });






        }
    }

    private void removeItem(int position) {
        favItemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,favItemList.size());
    }




}

