package com.example.a2zbilling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.example.a2zbilling.counter.CounterAdapter;
import com.example.a2zbilling.db.entities.Expenses;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.AvailableStock.AvailableStockAdapter;
import com.example.a2zbilling.stock.addUpdate.AddUpdateStockActivity;

import java.util.ArrayList;
import java.util.List;

public class ExpensesActivity extends AppCompatActivity {
    private ExpensesActivityViewModel expensesActivityViewModel;
    RecyclerView recyclerView;
    ExpensesAdapter adepter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        setTitle("Expenses");

        recyclerView = findViewById(R.id.recycler_viewExpenses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setHasFixedSize(true);

        adepter = new ExpensesAdapter();
        recyclerView.setAdapter(adepter);

        CardView cardViewOpenBottomSheet=findViewById(R.id.cardview_open_bottom_sheet);
        expensesActivityViewModel = ViewModelProviders.of(this).get(ExpensesActivityViewModel.class);
        cardViewOpenBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpensesBottomSheetDialog expensesBottomSheetDialog=new ExpensesBottomSheetDialog(expensesActivityViewModel);
                expensesBottomSheetDialog.show(getSupportFragmentManager(),"Expenses Buttom Sheet");
            }
        });

        expensesActivityViewModel.getAllExpenses().observe(this, new Observer<List<Expenses>>() {
            @Override
            public void onChanged(List<Expenses> expenses) {
                adepter.setExpenses(expenses);
            }
        });


        adepter.setOnItemRecyclerViewlistener(new ExpensesAdapter.OnItemRecyclerViewListener() {
            @Override
            public void onItemClick(Expenses expenses) {
                Intent intent = new Intent(getBaseContext(), ExpensesActivity2.class);
                intent.putExtra("expenses",  expenses);
                startActivity(intent);
            }
        });
    }

}
