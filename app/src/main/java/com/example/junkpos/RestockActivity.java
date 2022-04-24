package com.example.junkpos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RestockActivity extends AppCompatActivity {

    ArrayList<String> sList = new ArrayList<>();
    ArrayList<Catalogue> itList = new ArrayList<>();
    ArrayAdapter<String> stockAdapter;

    ListView cata;

    TextView itemText;
    Button restockOK, restockCancel;
    EditText quantEntry;

    int option = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);

        Intent mIntent = getIntent();
        itList = mIntent.getParcelableArrayListExtra("catalogList");
        cata = findViewById(R.id.restockList);
        itemText = findViewById(R.id.selectedItem);
        restockOK = findViewById(R.id.restockOK);
        restockCancel = findViewById(R.id.restockCancel);
        quantEntry = findViewById(R.id.quantityEntry);


        if (itList != null) {
            addString(itList);
        }

        stockAdapter = new ArrayAdapter<>(this, R.layout.item_row_layout, R.id.cata_row, sList);

        cata.setAdapter(stockAdapter);

        cata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemText.setText(itList.get(i).itemName);
                option = i;
            }
        });

        restockOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((quantEntry.getText().toString().equals(""))) {
                    Toast.makeText(RestockActivity.this, "Enter something!", Toast.LENGTH_LONG).show();
                } else if (option < 0 || option > itList.size()-1) {
                    Toast.makeText(RestockActivity.this, "Select an item!", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(quantEntry.getText().toString()) < 0) {
                    Toast.makeText(RestockActivity.this, "Enter a value greater than 0!", Toast.LENGTH_LONG).show();
                } else if ((Integer.parseInt(quantEntry.getText().toString()) > 1000000)) {
                    Toast.makeText(RestockActivity.this, "Too large of an inventory!", Toast.LENGTH_LONG).show();
                } else {
                    itList.get(option).quantityAvailable = Integer.parseInt(quantEntry.getText().toString());
                    sList.clear();
                    addString(itList);
                }

                stockAdapter.notifyDataSetChanged();
            }
        });

        restockCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestockActivity.this, ManagerActivity.class);
                intent.putParcelableArrayListExtra("catalogList", itList);
                
                startActivityForResult(intent, 1);
            }
        });

    }

    void addString(ArrayList<Catalogue> cataList) {
        for (int i = 0; i < cataList.size(); i++) {
            sList.add(String.format("%-14s %14s %14s",
                    (cataList.get(i).itemName),
                    ("$" + (cataList.get(i).itemPrice)),
                    ("Qty: " + cataList.get(i).quantityAvailable)));
        }
    }
}