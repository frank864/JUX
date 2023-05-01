package com.example.juxtagrocery;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class GroceryItem implements Parcelable {

    private int id;
    private String name;
    private String description;
    private String imageurl;
    private String category;
    private double price;
    private int availableamount;
    private int rate;
    private int userpoint;
    private int popularitypoint;
    private ArrayList<Review> reviews;

    public GroceryItem( String name, String description, String imageurl, String category, double price, int availableamount) {
        this.id = Utils.getID();
        this.name = name;
        this.description = description;
        this.imageurl = imageurl;
        this.category = category;
        this.price = price;
        this.availableamount = availableamount;
        this.rate = 0;
        this.userpoint = 0;
        this.popularitypoint = 0;
        reviews = new ArrayList<>();
    }

    protected GroceryItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        imageurl = in.readString();
        category = in.readString();
        price = in.readDouble();
        availableamount = in.readInt();
        rate = in.readInt();
        userpoint = in.readInt();
        popularitypoint = in.readInt();
    }

    public static final Creator<GroceryItem> CREATOR = new Creator<GroceryItem>() {
        @Override
        public GroceryItem createFromParcel(Parcel in) {
            return new GroceryItem(in);
        }

        @Override
        public GroceryItem[] newArray(int size) {
            return new GroceryItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableamount() {
        return availableamount;
    }

    public void setAvailableamount(int availableamount) {
        this.availableamount = availableamount;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getUserpoint() {
        return userpoint;
    }

    public void setUserpoint(int userpoint) {
        this.userpoint = userpoint;
    }

    public int getPopularitypoint() {
        return popularitypoint;
    }

    public void setPopularitypoint(int popularitypoint) {
        this.popularitypoint = popularitypoint;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "GroceryItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", availableamount=" + availableamount +
                ", rate=" + rate +
                ", userpoint=" + userpoint +
                ", popularitypoint=" + popularitypoint +
                ", reviews=" + reviews +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(imageurl);
        parcel.writeString(category);
        parcel.writeDouble(price);
        parcel.writeInt(availableamount);
        parcel.writeInt(rate);
        parcel.writeInt(userpoint);
        parcel.writeInt(popularitypoint);
    }
}
