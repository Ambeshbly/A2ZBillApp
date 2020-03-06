package com.example.a2zbilling.customer.AllCustomer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.customer.CustomerActivityViewModel;
import com.example.a2zbilling.customer.ShowCustomerTransactionDetailActivity;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.AvailableStock.AvailableStockAdapter;
import com.example.a2zbilling.stock.addUpdate.AddUpdateStockActivity;

import java.util.List;

public class AllCustomerFragment extends Fragment {
    RecyclerView recyclerView;
    AllCustomerAdapter adepter;
    private CustomerActivityViewModel customerActivityViewModel;

    public AllCustomerFragment(CustomerActivityViewModel customerActivityViewModel) {
        this.customerActivityViewModel = customerActivityViewModel;
    }

    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.all_customer,container,false);
        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adepter=new AllCustomerAdapter(getContext(),customerActivityViewModel);
        recyclerView.setAdapter(adepter);
        customerActivityViewModel.getAllCustomer().observe(getViewLifecycleOwner(), new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                Toast.makeText(getContext(), "customers", Toast.LENGTH_SHORT).show();
                adepter.setCustomers(customers);
            }
        });

        adepter.setOnItemRecyclerViewlistener(new AllCustomerAdapter.OnItemRecyclerViewListener() {
            @Override
            public void onItemClick(Customer customer) {
                Intent intent = new Intent(getContext(), ShowCustomerTransactionDetailActivity.class);
                intent.putExtra("customer_transaction",customer);
                startActivity(intent);
            }
        });
        return view;
    }
}
