package com.example.a2zbilling.counter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.a2zbilling.R;

public class WaitList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_list);
        getSupportActionBar().setTitle("Wait List");
    }
}
