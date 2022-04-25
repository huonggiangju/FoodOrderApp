package com.example.foodorderapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.foodorderapp.Adapter.RestaurantListAdapter;
import com.example.foodorderapp.Model.Restaurant;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RestaurantListAdapter.RestaurantListClickListener {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Restaurant List");

        //fetch restaurant data
        List<Restaurant>  restaurantsList = getRestaurantData();

        initRecycleView(restaurantsList); //running recycleview
    }

    public void initRecycleView(List<Restaurant>  restaurantsList){
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RestaurantListAdapter adapter = new RestaurantListAdapter(restaurantsList,this );
        recyclerView.setAdapter(adapter);

    }

    //getting restaurant data
    private List<Restaurant> getRestaurantData(){
        //reading json file
        InputStream input = getResources().openRawResource(R.raw.restaurant);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];

        try{
            Reader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            int n;
            while((n = reader.read(buffer)) != -1){
                writer.write(buffer, 0,n);
            }

        }catch(Exception e){

        }

        String jsonStr = writer.toString();
        Gson gson = new Gson();
        Restaurant[] restaurants = gson.fromJson(jsonStr, Restaurant[].class);
        List<Restaurant> restaurantList = Arrays.asList(restaurants);

        return restaurantList;
    }

    //go into restaurant page
    @Override
    public void onItemClick(Restaurant restaurant) {
        Intent intent = new Intent(MainActivity.this, MenusActivity.class);
        intent.putExtra("Restaurant", restaurant);
        startActivity(intent);
    }
}