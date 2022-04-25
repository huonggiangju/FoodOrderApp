package com.example.foodorderapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.foodorderapp.Model.Restaurant;

public class OrderSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        Intent intent = getIntent();
        Restaurant restaurant = intent.getParcelableExtra("Restaurant");

        //actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Place your order");
        actionBar.setSubtitle(restaurant.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(false);

        //done Btn
        Button doneBtn = (Button) findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}