package com.example.a2zbilling.customer.AllCustomer;

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
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.Stock;

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
        return inflater.inflate(R.layout.all_customer,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView=getView().findViewById(R.id.recycler_view);
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
    }
}
