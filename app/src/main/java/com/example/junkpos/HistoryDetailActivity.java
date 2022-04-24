package com.example.junkpos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryDetailActivity extends AppCompatActivity {

    ArrayList<PurchaseHistory> pHistory;

    TextView detailText;
    int clickedItem = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        Intent mIntent = getIntent();
        pHistory =  mIntent.getParcelableArrayListExtra("history");
        clickedItem = mIntent.getIntExtra("clicked_item", -1);

        detailText = findViewById(R.id.purchaseDetailsText);


        if (pHistory != null && clickedItem >= 0) {
            addString(pHistory, clickedItem);
        }


    }

    void addString(ArrayList<PurchaseHistory> entryHistory, int i) {
        detailText.setText(String.format("%4s \n%14s \n%14s \n%14s",
                (entryHistory.get(i).itemName),
                ("Total Price: $" + (entryHistory.get(i).totalPrice)),
                ("Quantity: " + entryHistory.get(i).quantitySold),
                ("Purchase Date: " + entryHistory.get(i).purchaseDate)
        ));
    }
}