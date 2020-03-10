package com.example.a2zbilling.stock.RFU;

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
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.StockActivityViewModel;

import java.util.List;


public class PurchaseHistoryFragments extends Fragment {
    private StockActivityViewModel stockActivityViewModel;
    RecyclerView recyclerView;
    PurchaseHistoryFragmentAdapter adepter;

    public PurchaseHistoryFragments(StockActivityViewModel stockActivityViewModel) {
        this.stockActivityViewModel = stockActivityViewModel;
    }

    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_for_purchase_history, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_for_salesDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adepter = new PurchaseHistoryFragmentAdapter();
        recyclerView.setAdapter(adepter);


       /* Sales sales=showCustomerTransactionDetailActivityViewModel.getSales();
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
        });*/

        stockActivityViewModel.getAllStockBaseOnPurchaseId(stockActivityViewModel.getPurchase().getPurchaseId()).observe(getViewLifecycleOwner(), new Observer<List<Stock>>() {
            @Override
            public void onChanged(List<Stock> stocks) {
                adepter.setStocks(stocks);
            }
        });

        return view;
    }

}
