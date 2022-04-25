package com.example.foodorderapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodorderapp.Adapter.PlaceOrderAdapter1;
import com.example.foodorderapp.Model.Menus;
import com.example.foodorderapp.Model.Restaurant;

public class PlaceYourOrderActivity extends AppCompatActivity {

    EditText inputName, inputAdd, inputCity, inputState, inputPostcode, inputCardNum, inputCardExpiry, inputCardpin;
    RecyclerView cardItemRecycleView;
    TextView tvSubTotalAmount, tvDeliveryChargeAmount, tvTotalAmount, tvDeliveryCharge;
    Button placeOrderBtn;
    SwitchCompat switchDelivery;
    boolean isDelivery;

    PlaceOrderAdapter1 placeOrderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_your_order);

        Intent intent = getIntent();
        Restaurant restaurant = intent.getParcelableExtra("Restaurant");

        //actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Place your order");
        actionBar.setSubtitle(restaurant.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(true);

        //getting data
        inputName = (EditText) findViewById(R.id.inputName);
        inputAdd = (EditText) findViewById(R.id.inputAdd);
        inputState = (EditText) findViewById(R.id.inputState);
        inputCity = (EditText) findViewById(R.id.inputCity);
        inputPostcode = (EditText) findViewById(R.id.inputPostcode);
        inputCardNum = (EditText) findViewById(R.id.inputCardNumber);
        inputCardExpiry = (EditText) findViewById(R.id.inputCardExpiry);
        inputCardpin = (EditText) findViewById(R.id.inputCardPin);
        tvSubTotalAmount = (TextView) findViewById(R.id.tvSubtotalAmount);
        tvDeliveryChargeAmount = (TextView) findViewById(R.id.tvDeliveryChargeAmount);
        tvDeliveryCharge =(TextView) findViewById(R.id.tvDeliveryCharge);
        tvTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);
        cardItemRecycleView = (RecyclerView) findViewById(R.id.cartItemRecycleView);
        switchDelivery = (SwitchCompat) findViewById(R.id.switchDelivery);
        placeOrderBtn = (Button) findViewById(R.id.placeOrderBtn);

        //place order btn
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrderBtnClick(restaurant);
            }
        });

        //switch listener
        switchDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    inputAdd.setVisibility(View.VISIBLE);
                    inputCity.setVisibility(View.VISIBLE);
                    inputState.setVisibility(View.VISIBLE);
                    inputPostcode.setVisibility(View.VISIBLE);
                    tvDeliveryChargeAmount.setVisibility(View.VISIBLE);
                    tvDeliveryCharge.setVisibility(View.VISIBLE);
                    isDelivery  = true;
                    calculateTotalAmount(restaurant);

                }else{
                    inputAdd.setVisibility(View.GONE);
                    inputCity.setVisibility(View.GONE);
                    inputState.setVisibility(View.GONE);
                    inputPostcode.setVisibility(View.GONE);
                    tvDeliveryChargeAmount.setVisibility(View.GONE);
                    tvDeliveryCharge.setVisibility(View.GONE);
                    isDelivery = false;
                    calculateTotalAmount(restaurant);
                }
            }
        });
        initRecycleView1(restaurant);
        calculateTotalAmount(restaurant);

    }

    //comeback to previous page on action bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    //recycle view
    private void initRecycleView1(Restaurant restaurant){
        cardItemRecycleView.setLayoutManager(new LinearLayoutManager(this));
        placeOrderAdapter = new PlaceOrderAdapter1(restaurant.getMenus());
        cardItemRecycleView.setAdapter(placeOrderAdapter);

    }
    //place order btn click
    private void placeOrderBtnClick(Restaurant restaurant){
        //validation
        if(TextUtils.isEmpty(inputName.getText().toString())){
            inputName.setError("Please enter name");
            return;
        }else if(isDelivery && TextUtils.isEmpty(inputAdd.getText().toString())){
            inputAdd.setError("Please enter address");
            return;
        }else if(isDelivery && TextUtils.isEmpty(inputCity.getText().toString())){
            inputCity.setError("Please enter city");
            return;
        }else if(isDelivery && TextUtils.isEmpty(inputState.getText().toString())){
            inputState.setError("Please enter state");
            return;
        }else if(isDelivery && TextUtils.isEmpty(inputPostcode.getText().toString())){
            inputPostcode.setError("Please enter postcode");
            return;
        }else if(TextUtils.isEmpty(inputCardNum.getText().toString())) {
            inputCardNum.setError("Please enter card number");
            return;
        }else if(TextUtils.isEmpty(inputCardExpiry.getText().toString())) {
            inputCardExpiry.setError("Please enter card expiryDate");
            return;

        }else if(TextUtils.isEmpty(inputCardpin.getText().toString())) {
            inputCardpin.setError("Please enter card pin/cvv");
            return;
        }
        //start new activity
        Intent intent = new Intent(PlaceYourOrderActivity.this, OrderSuccessActivity.class);
        intent.putExtra("Restaurant", restaurant);
        startActivityForResult(intent, 2000);


    }

    //calculateTotalAmount
    private void calculateTotalAmount(Restaurant restaurant){
        float subTotalAmount =0f;
        for(Menus m: restaurant.getMenus()){
            subTotalAmount += m.getPrice() * m.getTotalInCart();
        }
        //setting text
        tvSubTotalAmount.setText("$" + String.format("%.2f", subTotalAmount));
        if(isDelivery){
            tvDeliveryChargeAmount.setText("$" + String.format("%.2f", restaurant.getDelivery_fee()));
            subTotalAmount += restaurant.getDelivery_fee();
//            tvTotalAmount.setText("$ " + String.format("%.2f", subTotalAmount));
        }
        tvTotalAmount.setText("$" + String.format("%.2f", subTotalAmount));
//        tvTotalAmount.setText("$ " + String.format("%.2f", subTotalAmount));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode ==1000){
            setResult(Activity.RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}