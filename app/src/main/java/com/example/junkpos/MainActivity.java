package com.example.junkpos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buyButton, managerButton;
    TextView productType, quantityNum, totalNum;

    NumberPicker quantityPicker;

    ListView cata;

    ArrayList<Catalogue> itList = new ArrayList<>();
    ArrayList<String> sList = new ArrayList<>();

    ArrayAdapter<String> catalogueAdapter;

    AlertDialog.Builder builder;

    int currSel = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buyButton = findViewById(R.id.buyButton);
        buyButton.setOnClickListener(this);
        managerButton = findViewById(R.id.managerButton);
        managerButton.setOnClickListener(this);


        productType = findViewById(R.id.productTypeTextView);
        quantityNum = findViewById(R.id.quantityTextView);
        totalNum = findViewById(R.id.totalTextView);

        quantityPicker = findViewById(R.id.quantityPicker);

        quantityPicker.setMinValue(0);
        quantityPicker.setMaxValue(100);

        cata = findViewById(R.id.itemList);

        builder = new AlertDialog.Builder(this);


        quantityNum.setText(String.valueOf((quantityPicker.getValue())));

        itList.add(new Catalogue("Pants", 29.99, 50));
        itList.add(new Catalogue("Shirt", 19.95, 20));
        itList.add(new Catalogue("Hat", 39.99, 10));

        for (int i = 0; i < itList.size(); i++) {
            sList.add(String.format("%-14s %14s %14s", (itList.get(i).itemName), ("$" + itList.get(i).itemPrice), ("Qty: " + itList.get(i).quantityAvailable)));
        }

        catalogueAdapter = new ArrayAdapter<>(this, R.layout.item_row_layout, R.id.cata_row, sList);

        cata.setAdapter(catalogueAdapter);

        quantityPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {

                //Make this a function vvvvvv
                quantityNum.setText(String.valueOf((numberPicker.getValue())));
                if (!itList.isEmpty() && currSel >= 0) {
                    totalNum.setText(
                            String.valueOf("$" + String.format("%.2f", (itList.get(currSel).itemPrice * quantityPicker.getValue())))
                    );
                }

            }
        });



        cata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currSel = i;
                productType.setText(itList.get(currSel).itemName.toString());

                //Make this a function vvvvvv
                totalNum.setText(
                        "$" + String.format("%.2f", (itList.get(currSel).itemPrice * quantityPicker.getValue()))
                );
            }
        });

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.buyButton:
                if (currSel < 0) {
                    Toast.makeText(this, "No item selected!", Toast.LENGTH_LONG).show();
                } else if (quantityPicker.getValue() == 0) {
                    Toast.makeText(this, "Can't buy with quantity of 0!", Toast.LENGTH_LONG).show();
                } else if (itList.get(currSel).quantityAvailable < quantityPicker.getValue()) {
                    Toast.makeText(this, "Not enough in stock for purchase!", Toast.LENGTH_LONG).show();
                } else if (currSel >= 0) {
                    itList.get(currSel).quantityAvailable -= quantityPicker.getValue();

                    //Make this a function vvvvvv
                    sList.set(currSel, (String.format("%-14s %14s %14s", (itList.get(currSel).itemName), ("$" + itList.get(currSel).itemPrice), ("Qty: " + itList.get(currSel).quantityAvailable))));

                    catalogueAdapter.notifyDataSetChanged();

                    builder.setTitle("Thank you for your purchase");
                    builder.setMessage("Purchase of " + quantityPicker.getValue() + " " + itList.get(currSel).itemName);
                    builder.setCancelable(true);
                    builder.show();

                } else {
                    Toast.makeText(this, "Some error occurred", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}