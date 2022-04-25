package com.example.foodorderapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Restaurant implements Parcelable {

    private String name;
    private String address;
    private String image;
    private float delivery_fee;
    private Hours hours;
    private List<Menus> menus;


    protected Restaurant(Parcel in) {
        name = in.readString();
        address = in.readString();
        image = in.readString();
        delivery_fee = in.readFloat();
        menus = in.createTypedArrayList(Menus.CREATOR);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(float delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public Hours getHours() {
        return hours;
    }

    public void setHours(Hours hours) {
        this.hours = hours;
    }

    public List<Menus> getMenus() {
        return menus;
    }

    public void setMenus(List<Menus> menus) {
        this.menus = menus;
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(image);
        parcel.writeFloat(delivery_fee);
        parcel.writeTypedList(menus);
    }
}
