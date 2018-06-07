package com.example.prabhjot.firstapp;


import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {

    String title;
    String rating;
    String releaseYear;
    String image;

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Storing the Item data to a Parcel object
     **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(rating);
        dest.writeString(releaseYear);
        dest.writeString(image);
    }


    public Item(String t, String r, String ry, String i){
        title=t;
        rating=r;
        releaseYear=ry;
        image=i;
    }

    /**
     * Retrieving Item data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    private Item(Parcel in){
        this.title = in.readString();
        this.rating = in.readString();
        this.releaseYear = in.readString();
        this.image = in.readString();
    }

    public String getTitle(){
        return title;
    }

    public String getRating(){
        return rating;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getImage()
    {
        return image;
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {

        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }



        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }

    };
}