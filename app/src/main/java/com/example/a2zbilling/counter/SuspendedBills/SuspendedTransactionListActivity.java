package com.example.a2zbilling.counter.SuspendedBills;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2zbilling.R;

public class SuspendedTransactionListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_list);
        getSupportActionBar().setTitle("Suspended Transactional List");


    }
}
