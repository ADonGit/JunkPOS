package com.example.junkpos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buyButton, managerButton;
    TextView productType, quantityNum, totalNum;

    NumberPicker quantityPicker;

    ListView cata;

    ArrayList<Catalogue> itList = new ArrayList<>();
    ArrayList<String> sList = new ArrayList<>();
    ArrayList<PurchaseHistory> pHistory;

    ArrayAdapter<String> catalogueAdapter;

    AlertDialog.Builder builder;

    int currSel = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pHistory = new ArrayList<>();

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
                            String.valueOf(getTotal())
                    );
                }
            }
        });



        cata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currSel = i;
                productType.setText(itList.get(currSel).itemName.toString());

                totalNum.setText(getTotal());
            }
        });

        managerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openManager();
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

                    //Here we add to the history
                    PurchaseHistory tempHistory = new PurchaseHistory();
                    tempHistory.addItemName(itList.get(currSel).itemName);
                    tempHistory.addQuantity(quantityPicker.getValue());
                    tempHistory.addTotal(itList.get(currSel).itemPrice * quantityPicker.getValue());

                    //Getting the current date
                    tempHistory.addPurchaseDate(getDate());
                    pHistory.add(tempHistory);

                    sList.set(currSel, (String.format("%-14s %14s %14s",
                            (itList.get(currSel).itemName),
                            ("$" + itList.get(currSel).itemPrice),
                            ("Qty: " + itList.get(currSel).quantityAvailable))));

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

    String getDate() {
        Date cal = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("MMM/dd/yyyy", Locale.getDefault());
        String formattedDate = df.format(cal);
        return formattedDate;
    }

    public void openManager() {
        Intent intent = new Intent(this, ManagerActivity.class);
        //intent.putSerializable("history", pHistory);
        //((MyApp)getApplication()).hManager.pHis
        intent.putParcelableArrayListExtra("history", pHistory);
        intent.putParcelableArrayListExtra("catalogList", itList);

        //intent.putExtra("history", pHistory);
        startActivityForResult(intent, 1);
        //registerForActivityResult(intent, new );
    }

    String getTotal() {
        return ("$" + String.format("%.2f", (itList.get(currSel).itemPrice * quantityPicker.getValue())));
    }
}