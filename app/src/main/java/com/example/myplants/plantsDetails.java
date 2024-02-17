package com.example.myplants;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.Model;
import com.example.myplants.DB.DBdateWater;
import com.example.myplants.DB.FavDB;
import com.example.myplants.Models.CategoryItem;
import com.example.myplants.Models.FavItem;
import com.google.android.material.card.MaterialCardView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class plantsDetails extends AppCompatActivity {

    TextView namePDisc,disText,waterText,sunLIGHTText,FertilizText;
    MaterialCardView materialCardView;
    ImageView imageView;
    Button DateBtn,TimeBtn,SeveBtn;
    TextView WaterBtn;
    String timeTonotify;//fortime/hour choose


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants_details);




        WaterBtn = findViewById(R.id.WaterPage);
        namePDisc = findViewById(R.id.textplant);
        imageView = findViewById(R.id.image_Plant);
        disText = findViewById(R.id.discPlant);
        waterText = findViewById(R.id.water);
        sunLIGHTText = findViewById(R.id.sun);
        FertilizText = findViewById(R.id.fertiz);

        ImageView arrowBack = findViewById(R.id.arrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Mainpage.class);
                startActivity(intent);

            }
        });

        WaterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WaterPlant.class);
                startActivity(intent);                                                              //Starts the new activity to add Reminders
            }
        });



        Intent i = getIntent();

        String name= i.getStringExtra("txt");
        String id= i.getStringExtra("id");
        String FavStatue= i.getStringExtra("FavStatue");
        String disc = i.getStringExtra("disc");
        disText.setText(disc);
        String SunLight = i.getStringExtra("sun");
        sunLIGHTText.setText(SunLight);
        String water = i.getStringExtra("water");
        waterText.setText(water);
        String Fertilizer = i.getStringExtra("Fertilizer");
        FertilizText.setText(Fertilizer);

       int image= i.getIntExtra("img",R.drawable.pl2);
       imageView.setImageResource(image);




        DateBtn = findViewById(R.id.DateBtn);
        DateBtn.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View v) {


        AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(plantsDetails.this);
        LayoutInflater inflater = plantsDetails.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_water_time, null);
        dialogBuilder1.setView(dialogView);

        DateBtn = (Button) dialogView.findViewById(R.id.Date);
        TimeBtn = (Button) dialogView.findViewById(R.id.Time);
        SeveBtn = (Button) dialogView.findViewById(R.id.saveBtn);

        DateBtn.setText("Date");
        TimeBtn.setText("Time");
        SeveBtn.setText("Save");

        AlertDialog alertDialog1 = dialogBuilder1.create();
        alertDialog1.show();


        TimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime();                                                                       //when we click on the choose time button it calls the select time method
            }
        });

        DateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }                                        //when we click on the choose date button it calls the select date method
        });

        SeveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = DateBtn.getText().toString().trim();                                 //access the date from the choose date button
                String time = TimeBtn.getText().toString().trim();                                 //access the time from the choose time button


                if (time.equals("time") || date.equals("date")) {                                               //shows toast if date and time are not selected
                    Toast.makeText(getApplicationContext(), "Please select date and time", Toast.LENGTH_SHORT).show();
                } else {
                    processinsert(name,time,date);

                }
            }


        });
    }
});


    }


    private void processinsert(String title,String time,String date) {
         String result = new DBdateWater(this).addreminder(title,time,date);                  //inserts the title,date,time into sql lite database
        setAlarm(title, date, time);
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();




    }


    //Date and time Methods
    private void selectTime() {                                                                     //this method performs the time picker task
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeTonotify = i + ":" + i1;                                                        //temp variable to store the time to set alarm
                TimeBtn.setText(FormatTime(i, i1));                                                //sets the button text as selected time
            }
        }, hour, minute, false);
        timePickerDialog.show();
    }


    private void selectDate() {                                                                     //this method performs the date picker task
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                DateBtn.setText(day + "-" + (month + 1) + "-" + year);                             //sets the selected date as test for button
            }
        }, year, month, day);
        datePickerDialog.show();
    }


    public String FormatTime(int hour, int minute) {                                                //this method converts the time into 12hr format and assigns am or pm

        String time;
        time = "";
        String formattedMinute;

        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }


        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }


        return time;
    }

    private void setAlarm(String title, String date, String time) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);                   //assigning alarm manager object to set alarm

        Intent intent = new Intent(getApplicationContext(), AlarmBroadcast.class);
        intent.putExtra("event", title);                                                       //sending data to alarm class to create channel and notification
        intent.putExtra("time", time);
        intent.putExtra("date", date);



        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_MUTABLE);//معدل
        String dateandtime = date + " " + timeTonotify;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
        try {
            Date date1 = formatter.parse(dateandtime);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);
            Toast.makeText(getApplicationContext(), "Alarm", Toast.LENGTH_SHORT).show();

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


}