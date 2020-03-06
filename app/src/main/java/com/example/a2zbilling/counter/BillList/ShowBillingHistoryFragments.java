package com.example.a2zbilling.counter.BillList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.counter.BillList.BillHistoryActivityViewModel;
import com.example.a2zbilling.counter.BillList.ShowBillingHistoryFragmentAdapter;
import com.example.a2zbilling.customer.ShowCustomerTransactionDetailActivityViewModel;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;

import java.util.List;


public class ShowBillingHistoryFragments extends Fragment {

    RecyclerView recyclerView;
    ShowBillingHistoryFragmentAdapter adepter;
    private BillHistoryActivityViewModel billHistoryActivityViewModel;
    TextView textViewTotal;
    private int cheak;
    private double grandTotal;

    private ShowCustomerTransactionDetailActivityViewModel showCustomerTransactionDetailActivityViewModel;

    public ShowBillingHistoryFragments(BillHistoryActivityViewModel billHistoryActivityViewModel) {
        this.billHistoryActivityViewModel=billHistoryActivityViewModel;
    }

    public ShowBillingHistoryFragments(ShowCustomerTransactionDetailActivityViewModel showCustomerTransactionDetailActivityViewModel,int cheak) {
        this.showCustomerTransactionDetailActivityViewModel = showCustomerTransactionDetailActivityViewModel;
        this.cheak=cheak;
    }

    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_for_show_billing_history, container, false);

        textViewTotal=view.findViewById(R.id.TextView_grandTotal);

        recyclerView = view.findViewById(R.id.recyclerView_for_salesDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adepter = new ShowBillingHistoryFragmentAdapter();
        recyclerView.setAdapter(adepter);
        if (cheak==1){

            Sales sales=showCustomerTransactionDetailActivityViewModel.getSales();
            int salesId=sales.getSaleId();
            showCustomerTransactionDetailActivityViewModel.getAllSaleDetail(salesId).observe(getViewLifecycleOwner(), new Observer<List<SaleDeatial>>() {
                @Override
                public void onChanged(List<SaleDeatial> saleDeatials) {
                    adepter.setSaleDeatials(saleDeatials);
                    for (int i = 0; i < saleDeatials.size(); i++) {
                        SaleDeatial saleDeatial = saleDeatials.get(i);
                        grandTotal=grandTotal+Double.parseDouble(saleDeatial.getSalingPrice());
                        textViewTotal.setText(""+grandTotal+" \u20B9");
                    }
                }
            });
        }else{

            Sales sales=billHistoryActivityViewModel.getSales();
            int salesId=sales.getSaleId();
            billHistoryActivityViewModel.getAllSaleDetail(salesId).observe(getViewLifecycleOwner(), new Observer<List<SaleDeatial>>() {
                @Override
                public void onChanged(List<SaleDeatial> saleDeatials) {
                    adepter.setSaleDeatials(saleDeatials);
                    for (int i = 0; i < saleDeatials.size(); i++) {
                        SaleDeatial saleDeatial = saleDeatials.get(i);
                        grandTotal=grandTotal+Double.parseDouble(saleDeatial.getSalingPrice());
                        textViewTotal.setText(""+grandTotal+" \u20B9");
                    }
                }
            });
        }

        return view;
    }




}
