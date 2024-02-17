package com.example.myplants.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplants.DB.DBdateWater;
import com.example.myplants.Models.DatePlantsModel;
import com.example.myplants.R;
import com.example.myplants.WaterPlant;

import java.util.List;

public class AdapterReminder extends RecyclerView.Adapter<AdapterReminder.ViewHolder> {

        private Context context;
        private List<DatePlantsModel> modelList;

        public AdapterReminder(List<DatePlantsModel> modelList,Context context) {
            this.context = context;
            this.modelList = modelList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_reminder_file,
                    parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            final DatePlantsModel plantModel = modelList.get(position);
            holder.time.setText(modelList.get(position).getTime());
            holder.Date.setText(modelList.get(position).getDate());
            holder.name.setText(modelList.get(position).getTitle());

            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context).setTitle("Delete Item").setMessage("Are You sure")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DBdateWater db = new DBdateWater(context);
                                    if( db.deletedData(plantModel.getID()) > 0){
                                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                        v.getContext().startActivity(new Intent(context, WaterPlant.class));


                                    }else{
                                        Toast.makeText(context, "Erorr", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).show();

                }
            });

        }
        @Override
        public int getItemCount() {
            return modelList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView name, Date, time;
            ImageView deleteBtn;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                time = itemView.findViewById(R.id.txtTime);
                name = itemView.findViewById(R.id.txtTitle);
                Date = itemView.findViewById(R.id.txtDate);
                deleteBtn = itemView.findViewById(R.id.deleteBtn);


            }
        }

}
