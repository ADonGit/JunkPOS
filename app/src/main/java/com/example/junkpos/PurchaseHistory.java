package com.example.junkpos;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class PurchaseHistory implements Parcelable {
    String itemName;
    double totalPrice;
    int quantitySold;
    String purchaseDate;

    public PurchaseHistory() {
        itemName = "";
        totalPrice = 0;
        quantitySold = 0;
        purchaseDate = "";
    }

    protected PurchaseHistory(Parcel in) {
        itemName = in.readString();
        totalPrice = in.readDouble();
        quantitySold = in.readInt();
        purchaseDate = in.readString();
    }

    public static final Creator<PurchaseHistory> CREATOR = new Creator<PurchaseHistory>() {
        @Override
        public PurchaseHistory createFromParcel(Parcel in) {
            return new PurchaseHistory(in);
        }

        @Override
        public PurchaseHistory[] newArray(int size) {
            return new PurchaseHistory[size];
        }
    };

    public void addItemName(String iName) {
        itemName = iName;
    }

    public void addTotal(double tPrice) {
        totalPrice = tPrice;
    }

    public void addQuantity(int qSold) {
        quantitySold = qSold;
    }

    public void addPurchaseDate(String pDate) {
        purchaseDate = pDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(itemName);
        parcel.writeDouble(totalPrice);
        parcel.writeInt(quantitySold);
        parcel.writeString(purchaseDate);
    }
}
