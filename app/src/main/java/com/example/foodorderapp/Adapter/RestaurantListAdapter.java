package com.example.foodorderapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderapp.Model.Restaurant;
import com.example.foodorderapp.R;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.MyViewHolder> {

    private List<Restaurant> restaurantList;
    private RestaurantListClickListener clickListener;

    public RestaurantListAdapter(List<Restaurant> restaurantList, RestaurantListClickListener clickListener){
        this.restaurantList = restaurantList;
        this.clickListener = clickListener;

    }

    public void updateData(List<Restaurant> restaurantList){
        this.restaurantList = restaurantList;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RestaurantListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListAdapter.MyViewHolder holder, int position) {
        holder.tvResName.setText(restaurantList.get(position).getName());
        holder.tvResAdd.setText("Address: "+restaurantList.get(position).getAddress());
        holder.tvResHours.setText("Today's hours: "+restaurantList.get(position).getHours().getTodayHour());
        Glide.with(holder.thumbImg).load(restaurantList.get(position).getImage()).into(holder.thumbImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(restaurantList.get(holder.getAdapterPosition()));
            }
        });


    }




    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvResName, tvResAdd, tvResHours;
        ImageView thumbImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvResName = itemView.findViewById(R.id.resName);
            tvResAdd = itemView.findViewById(R.id.resAdd);
            tvResHours = itemView.findViewById(R.id.resHours);
            thumbImg = itemView.findViewById(R.id.thumbImg);

        }
    }

    public interface RestaurantListClickListener{
        public void onItemClick(Restaurant restaurant);
    }
}
