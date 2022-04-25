package com.example.foodorderapp.Adapter;

import android.text.Layout;
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
import com.example.foodorderapp.Model.Restaurant;
import com.example.foodorderapp.R;

import java.util.List;

public class MenusListAdapter extends RecyclerView.Adapter<MenusListAdapter.MyViewHolder> {

    private List<Menus> menusList;
    private MenusListClickListener clickListener;


    public MenusListAdapter(List<Menus> menusList, MenusListClickListener clickListener){
        this.menusList = menusList;
        this.clickListener = clickListener;

    }

    public void updateData(List<Menus> menusList){
        this.menusList = menusList;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenusListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.menu_recycler_row, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MenusListAdapter.MyViewHolder holder, int position) {
        holder.tvMenuName.setText(menusList.get(position).getName());
        holder.tvPrice.setText("Price: $"+menusList.get(position).getPrice());
        //fetch item img
        Glide.with(holder.thumbImg).load(menusList.get(position).getUrl()).into(holder.thumbImg);

        //add to cart btn click
        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Menus menus = menusList.get(holder.getAdapterPosition());
                 menus.setTotalInCart(1);
                 clickListener.onAddToCartClick(menus);
                 holder.addMoreLayout.setVisibility(View.VISIBLE);
                 holder.addToCartBtn.setVisibility(View.GONE);
                 holder.tvCount.setText(menus.getTotalInCart()+" "); //setting total in cart into tv count




            }
        });

        //remove more item
        holder.removeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menus menus = menusList.get(holder.getAdapterPosition());
                int total = menus.getTotalInCart();
                total--;
                if(total >0){
                    menus.setTotalInCart(total);
                    clickListener.onUpdateCartClick(menus);
                    holder.tvCount.setText(total +"");

                }else{
                    holder.addMoreLayout.setVisibility(View.GONE);
                    holder.addToCartBtn.setVisibility(View.VISIBLE);
                    menus.setTotalInCart(total);
                    clickListener.onRemoveCartClick(menus);
                }
            }
        });

        //additem from cart
        holder.addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menus menus = menusList.get(holder.getAdapterPosition());
                int total = menus.getTotalInCart();
                total++;
                if(total <=100){
                    menus.setTotalInCart(total);
                    clickListener.onUpdateCartClick(menus);
                    holder.tvCount.setText(total +"");

                }
            }
        });


    }




    @Override
    public int getItemCount() {
        return menusList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvMenuName, tvPrice, tvCount;
        Button addToCartBtn;
        ImageView thumbImg, addImg, removeImg;
        LinearLayout addMoreLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMenuName = itemView.findViewById(R.id.menuName);
            tvPrice = itemView.findViewById(R.id.menuPrice);
            thumbImg = itemView.findViewById(R.id.menuThumbImg);
            addToCartBtn = itemView.findViewById(R.id.addBtn);
            tvCount = itemView.findViewById(R.id.tvCount);
            addImg = itemView.findViewById(R.id.addImg);
            removeImg = itemView.findViewById(R.id.removeImg);

            addMoreLayout = itemView.findViewById(R.id.addMoreLayout);


        }
    }

    public interface MenusListClickListener{
        public void onAddToCartClick(Menus menus);
        public void onUpdateCartClick(Menus menus);
        public void onRemoveCartClick(Menus menus);
    }
}
