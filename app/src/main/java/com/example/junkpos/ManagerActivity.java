package com.example.junkpos;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class ManagerActivity extends AppCompatActivity {

    Button historyButton, restockButton;

    ArrayList<PurchaseHistory> pHistory;
    ArrayList<Catalogue> itList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        historyButton = findViewById(R.id.historyButton);
        restockButton = findViewById(R.id.restockButton);

        Intent mIntent = getIntent();
        pHistory = mIntent.getParcelableArrayListExtra("history");
        itList = mIntent.getParcelableArrayListExtra("catalogList");


        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistory();
            }
        });

        restockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRestock();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(ManagerActivity.this, HistoryActivity.class);
                intent.putParcelableArrayListExtra("history", pHistory);

                setResult(RESULT_OK, intent);
                finish();

            }
        };

        ManagerActivity.this.getOnBackPressedDispatcher().addCallback(this, callback);

    }

    /*
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putParcelableArrayListExtra("history", pHistory);

        setResult(RESULT_OK, intent);
        finish();

    }*/

    public void openHistory() {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putParcelableArrayListExtra("history", pHistory);
        startActivity(intent);
    }

    public void openRestock() {
        Intent intent = new Intent(this, RestockActivity.class);
        intent.putParcelableArrayListExtra("catalogList", itList);
        startActivity(intent);
    }
}