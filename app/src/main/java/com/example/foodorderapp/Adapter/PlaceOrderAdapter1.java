package com.example.foodorderapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderapp.Model.Menus;
import com.example.foodorderapp.R;

import java.util.List;

public class PlaceOrderAdapter1 extends RecyclerView.Adapter<PlaceOrderAdapter1.MyViewHolder> {

    private List<Menus> menusList;



    public PlaceOrderAdapter1(List<Menus> menusList){
        this.menusList = menusList;


    }

    public void updateData(List<Menus> menusList){
        this.menusList = menusList;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaceOrderAdapter1.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.place_oder_recycler_row, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PlaceOrderAdapter1.MyViewHolder holder, int position) {
        holder.tvMenuName.setText(menusList.get(position).getName());
        holder.tvPrice.setText("Price: $"+ String.format("%.2f", menusList.get(position).getPrice() * menusList.get(position).getTotalInCart()));
        holder.tvQty.setText("Qty: " + menusList.get(position).getTotalInCart());
        //fetch item img
        Glide.with(holder.thumbImg).load(menusList.get(position).getUrl()).into(holder.thumbImg);



    }




    @Override
    public int getItemCount() {
        return menusList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvMenuName, tvPrice, tvQty;
        ImageView thumbImg;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMenuName = itemView.findViewById(R.id.menuName);
            tvPrice = itemView.findViewById(R.id.menuPrice);
            thumbImg = itemView.findViewById(R.id.menuThumbImg);
            tvQty = itemView.findViewById(R.id.menuQty);





        }
    }


}
