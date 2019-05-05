package com.example.lenovo.ap2;

import android.os.Parcel;

import java.io.Serializable;

public class Item implements Serializable {

    public Integer Id;
    public String Name;
    public int Stock;
    public int Rating;
    public String ImageURL;
    public Float Price;

    public void setPrice(Float price) {
        Price = price;
    }

    public Item() {
    }

    public Float getPrice() {


        return Price;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public Item(int id, String name, int stock, int rating, String imageURL, Float price) {
        Id = id;
        Name = name;
        Stock = stock;
        Rating = rating;
        ImageURL = imageURL;
        Price = price;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public int getId() {

        return Id;
    }

    public String getName() {
        return Name;
    }

    public int getStock() {
        return Stock;
    }

    public int getRating() {
        return Rating;
    }

    public String getImageURL() {
        return ImageURL;
    }
    private Item(Parcel in) {
        this.Name=in.readString();
        this.Id = in.readInt();
        this.ImageURL= in.readString();
        this.Price=in.readFloat();
        this.Stock=in.readInt();
        this.Rating=in.readInt();
    }
}
