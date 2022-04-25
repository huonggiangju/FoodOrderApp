package com.example.foodorderapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Menus implements Parcelable {
    private String name;
    private float price;
    private int totalInCart;
    private String url;

    protected Menus(Parcel in) {
        name = in.readString();
        price = in.readFloat();
        url = in.readString();
        totalInCart = in.readInt();
    }

    public static final Creator<Menus> CREATOR = new Creator<Menus>() {
        @Override
        public Menus createFromParcel(Parcel in) {
            return new Menus(in);
        }

        @Override
        public Menus[] newArray(int size) {
            return new Menus[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTotalInCart() {
        return totalInCart;
    }

    public void setTotalInCart(int totalInCart) {
        this.totalInCart = totalInCart;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeFloat(price);
        parcel.writeString(url);
        parcel.writeInt(totalInCart);
    }
}
