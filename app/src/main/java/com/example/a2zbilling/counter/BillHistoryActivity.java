package com.example.a2zbilling.counter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2zbilling.R;

public class BillHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conform_list);
        getSupportActionBar().setTitle("Transaction List");
    }
}
