package com.example.a2zbilling.counter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.a2zbilling.R;
import com.example.a2zbilling.stock.AdepterForAvailableItems;
import com.example.a2zbilling.tables.Items;
import com.example.a2zbilling.tables.ItemsViewModel;

import java.util.List;

public class WaitList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_list);
        getSupportActionBar().setTitle("Wait List");


    }
}
