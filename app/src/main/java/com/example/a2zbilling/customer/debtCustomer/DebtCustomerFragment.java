package com.example.a2zbilling.customer.debtCustomer;

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
import com.example.a2zbilling.customer.AllCustomer.AllCustomerAdapter;
import com.example.a2zbilling.customer.CustomerActivityViewModel;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.Stock;

import java.util.ArrayList;
import java.util.List;

public class DebtCustomerFragment extends Fragment {
    RecyclerView recyclerView;
    debtCustomerAdapter adepter;
    private CustomerActivityViewModel customerActivityViewModel;

    public DebtCustomerFragment(CustomerActivityViewModel customerActivityViewModel) {
        this.customerActivityViewModel = customerActivityViewModel;
    }

    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.debt_customer,container,false);
        recyclerView=view.findViewById(R.id.recycler_view_for_debt_customer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adepter=new debtCustomerAdapter(getContext(),customerActivityViewModel);
        recyclerView.setAdapter(adepter);
        customerActivityViewModel.getAllCustomer().observe(getViewLifecycleOwner(), new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                Toast.makeText(getContext(), " debt customers", Toast.LENGTH_SHORT).show();
                List<Customer> customerList=new ArrayList<Customer>();
                for (int i = 0; i < customers.size(); i++) {
                    Customer customer = customers.get(i);
                    if(customer.getDebt().equals("no debt")) {
                    }else{
                        customerList.add(customer);
                    }

                }
                adepter.setCustomers(customerList);
            }
        });
      return view;
    }
}
