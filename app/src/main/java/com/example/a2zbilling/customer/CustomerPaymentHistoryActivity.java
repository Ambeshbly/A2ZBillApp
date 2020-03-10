package com.example.a2zbilling.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.a2zbilling.Expenses2Adapter;
import com.example.a2zbilling.Expenses2BottomSheetDialog;
import com.example.a2zbilling.ExpensesActivityViewModel;
import com.example.a2zbilling.ExpensesAdapter;
import com.example.a2zbilling.R;
import com.example.a2zbilling.db.entities.Expenses;
import com.example.a2zbilling.db.entities.Payment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CustomerPaymentHistoryActivity extends AppCompatActivity {

    private CustomerPaymentHistoryActivityViewModel customerPaymentHistoryActivityViewModel;

    RecyclerView recyclerView;
    CustomerPaymentAdapter adepter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_payment_history);
        FloatingActionButton floatingActionButton=findViewById(R.id.bt_float2);
        recyclerView = findViewById(R.id.recycler_viewPayment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setHasFixedSize(true);

        adepter = new CustomerPaymentAdapter();
        recyclerView.setAdapter(adepter);

        customerPaymentHistoryActivityViewModel = ViewModelProviders.of(this).get(CustomerPaymentHistoryActivityViewModel.class);

        customerPaymentHistoryActivityViewModel.getAllPayments().observe(this, new Observer<List<Payment>>() {
            @Override
            public void onChanged(List<Payment> payments) {
                adepter.setPayments(payments);
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CustomerPaymentBottomSheetDialog customerPaymentBottomSheetDialog=new CustomerPaymentBottomSheetDialog(customerPaymentHistoryActivityViewModel);
//                customerPaymentBottomSheetDialog.show(getSupportFragmentManager(),"customer Payment");
            }
        });
    }
}
