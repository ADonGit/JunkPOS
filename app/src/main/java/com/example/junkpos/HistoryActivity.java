package com.example.junkpos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ArrayList<PurchaseHistory> pHistory;
    ArrayList<String> sList = new ArrayList<>();
    ArrayAdapter<String> historyAdapter;

    ListView cata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        Intent mIntent = getIntent();
        pHistory =  mIntent.getParcelableArrayListExtra("history");

        cata = findViewById(R.id.historyList);


        if (pHistory != null) {
            addString(pHistory);
        }


        historyAdapter = new ArrayAdapter<>(this, R.layout.item_row_layout, R.id.cata_row, sList);

        cata.setAdapter(historyAdapter);

        cata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(HistoryActivity.this, HistoryDetailActivity.class);
                intent.putParcelableArrayListExtra("history", pHistory);
                intent.putExtra("clicked_item", i);
                startActivity(intent);
            }
        });


    }

    void addString(ArrayList<PurchaseHistory> entryHistory) {

        for (int i = 0; i < entryHistory.size(); i++) {
            sList.add(String.format("%-14s %14s %14s",
                    (entryHistory.get(i).itemName),
                    ("$" + (entryHistory.get(i).totalPrice)),
                    ("Qty: " + entryHistory.get(i).quantitySold)));
        }
    }

}