package com.example.myplants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myplants.Adapter.CategoryAdater;
import com.example.myplants.DB.FavDB;
import com.example.myplants.Models.CategoryItem;
import com.example.myplants.Models.userModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.time.Year;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;

public class Mainpage extends AppCompatActivity {


    EditText edittSearch;
    RecyclerView recCategPlant;


    ArrayList<CategoryItem> arrayList;
    BottomNavigationView bottomNavigationView;



    TextView logOutTextView;

    CategoryAdater adater;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        bottomNavigationView = findViewById(R.id.bottomNav);
        logOutTextView = findViewById(R.id.logout);
        edittSearch = findViewById(R.id.Search);




        edittSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });




        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                if (item.getItemId() == R.id.miHome) {
                    Intent intent = new Intent(Mainpage.this, Mainpage.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.myPlants) {
                    Intent intent = new Intent(Mainpage.this, myPlants.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.Watering) {
                Intent intent = new Intent(Mainpage.this, WaterPlant.class);
                startActivity(intent);
            }
                return true;


            }
        });
        recCategPlant = findViewById(R.id.recy);

            arrayList = new ArrayList<CategoryItem>();

            arrayList.add(new CategoryItem("Peace lily plant", R.drawable.pl4, "Peace lilies are not true lilies, but tropical plants that bloom with white, spath-like flowers that resemble flags of peace.", "Watering Once a week", "Dappled light", "0", "0", "during the growing season"));
            arrayList.add(new CategoryItem("Aglaonema plant", R.drawable.pl3, "Aglaonema is a lucky and colorful plant from China and the Philippines, with shiny leaves in various patterns and shades.", "Every 1-2 weeks", "indirect light", "0", "1", "once per season"));
            arrayList.add(new CategoryItem("ZZ Plant", R.drawable.pl5, "ZZ Plants are tough—making them perfect for the forgetful plant owner. These hardy plants are able to survive for months without water and will grow well in any light except direct sun.", "Water when the soil is completely dry", "indirect bright light", "0", "2", "once or twice a year"));
            arrayList.add(new CategoryItem("Pothos Plant", R.drawable.pl6, "Pothos plants can live for many years with basic care and are super adaptable, as various light, soil, and moisture conditions suit them. They're fast-growing plants even indoors, ", "Let the soil dry out completely between waterings.", "Medium indirect light", "0", "3", "once per month during the spring and summer"));
            arrayList.add(new CategoryItem("Philodendron Plant", R.drawable.pl1, "Philodendrons are beautiful plants that grow vines with heart-shaped leaves. It's a strikingly beautiful plant that stands out in any room of your house", "let the top of the soil dry out between waterings.", "indirect light", "0", "4", "once a month during the spring and summer months"));
            arrayList.add(new CategoryItem("Snake plant", R.drawable.pl7, "Snake plant is a good choice for beginners due to it's broad tolerance of growing conditions. It's an ideal container plant that adds vertical structure and interest as a decorative indoor plant. Snake plant thrives in warm weather", "Water every 5-7 days during the growing season", "bright light", "0", "5", "Fertilize them during the spring and summer"));
            arrayList.add(new CategoryItem("Dieffenbachia", R.drawable.pl9, "Dieffenbachia is a fast-growing plant that can achieve 2 feet in height within a year of planting a rooted cutting, provided it gets enough light. Though the name dumb cane has fallen out of favor as a derogatory term, it got that name because the plant contains toxins in all of its parts", "Watering Once a week", "bright light", "0", "6", "every twelve weeks except in the winter"));
            arrayList.add(new CategoryItem("Bamboo", R.drawable.pl8, "bamboo plants are known for their quick growth, so much so they can become invasive.", "The Water change every week", "medium light", "0", "7", "once a month"));
            arrayList.add(new CategoryItem("Fittonia Plant", R.drawable.pl2, "Fittonia are compact plants with striking patterned foliage. Fittonia are fairly easy to care for–they can tolerate a range of lighting conditions and like to be kept moist. They prefer humid environments and will benefit from regular misting.", "Every 3-4 days", "medium light", "0", "8", "Once a month"));



        LinearLayoutManager layoutManager = new LinearLayoutManager(Mainpage.this, LinearLayoutManager.VERTICAL, false);
        recCategPlant.setLayoutManager(layoutManager);

        recCategPlant.setHasFixedSize(true);
         adater = new CategoryAdater(arrayList,this);
         recCategPlant.setAdapter(adater);



        //for loging out
        logOutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Mainpage.this, loginActivity.class);

                //not letting the user to be able to return back if he sign out
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });

    }

    //search recycler
    private void filter(String text){
        ArrayList<CategoryItem> filteredList = new ArrayList<>();

        for(CategoryItem item : arrayList){
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }

        }
        adater.filterList(filteredList);

    }

}

