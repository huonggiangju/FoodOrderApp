package com.example.foodorderapp;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodorderapp.Adapter.MenusListAdapter;
import com.example.foodorderapp.Model.Menus;
import com.example.foodorderapp.Model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class MenusActivity extends AppCompatActivity implements MenusListAdapter.MenusListClickListener{

    private List<Menus> menusList;
    RecyclerView recyclerView;
    Button checkoutBtn;
    MenusListAdapter menusListAdapter;

    private List<Menus> itemInCartList;
    private int totalItemInCart = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);

        Intent intent = getIntent();
        Restaurant restaurant = intent.getParcelableExtra("Restaurant");

        //actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(restaurant.getName() + "'s Menu");
        actionBar.setSubtitle(restaurant.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(true);

        menusList = restaurant.getMenus();
        initRecycleView(); //display recycleview

        //checkout btn
        checkoutBtn = (Button) findViewById(R.id.checkoutBtn);
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemInCartList == null || itemInCartList.size() <=0){
                    Toast.makeText(MenusActivity.this, "Please add some items into the cart", Toast.LENGTH_SHORT).show();
                    return;
                }
                restaurant.setMenus(itemInCartList);
                Intent i = new Intent(MenusActivity.this, PlaceYourOrderActivity.class);
                i.putExtra("Restaurant", restaurant);
                startActivityForResult(i, 1000);
            }
        });



    }

    public void initRecycleView(){
         recyclerView = (RecyclerView) findViewById(R.id.menusRecycleView);
         recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        menusListAdapter = new MenusListAdapter(menusList, this);
        recyclerView.setAdapter(menusListAdapter);

    }

    @Override
    public void onAddToCartClick(Menus menus) {
        if(itemInCartList == null){
            itemInCartList = new ArrayList<>();
        }
        itemInCartList.add(menus);

        totalItemInCart = 0;
        for(Menus item: itemInCartList){
            totalItemInCart = totalItemInCart+ item.getTotalInCart();
        }
        checkoutBtn.setText("Checkout (" + totalItemInCart +") items.");

    }

    @Override
    public void onUpdateCartClick(Menus menus) {
        if(itemInCartList.contains(menus)){
            int index = itemInCartList.indexOf(menus);
            itemInCartList.add(index, menus);
            itemInCartList.remove(index);

            totalItemInCart = 0;
            for(Menus item: itemInCartList){
                totalItemInCart += item.getTotalInCart();
            }
            checkoutBtn.setText("Checkout (" + totalItemInCart +") items.");
        }
    }

    @Override
    public void onRemoveCartClick(Menus menus) {
        if(itemInCartList.contains(menus)){
            itemInCartList.remove(menus);
            totalItemInCart = 0;
            for(Menus item: itemInCartList){
                totalItemInCart += item.getTotalInCart();
            }
            checkoutBtn.setText("Checkout (" + totalItemInCart +") items.");
        }
    }

    //comeback to previous page on action bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000  && resultCode == Activity.RESULT_OK){

        }
    }
}