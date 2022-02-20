package com.example.junkpos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.concurrent.CompletionStage;

public class Catalogue implements Parcelable {

    String itemName;
    double itemPrice;
    int quantityAvailable;

    public Catalogue() {
        itemName = "";
        itemPrice = 0;
        quantityAvailable = 0;
    }

    public Catalogue(String iName, double iPrice, int quant) {
        itemName = iName;
        itemPrice = iPrice;
        quantityAvailable = quant;
    }


    protected Catalogue(Parcel in) {
        itemName = in.readString();
        itemPrice = in.readDouble();
        quantityAvailable = in.readInt();
    }

    public static final Creator<Catalogue> CREATOR = new Creator<Catalogue>() {
        @Override
        public Catalogue createFromParcel(Parcel in) {
            return new Catalogue(in);
        }

        @Override
        public Catalogue[] newArray(int size) {
            return new Catalogue[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(itemName);
        parcel.writeDouble(itemPrice);
        parcel.writeInt(quantityAvailable);
    }


}
