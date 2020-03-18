package com.example.a2zbilling.stock.RFU;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.a2zbilling.ExpensesActivity2;
import com.example.a2zbilling.ExpensesAdapter;
import com.example.a2zbilling.R;
import com.example.a2zbilling.counter.BillList.ShowBillingHistoryFragments;
import com.example.a2zbilling.databinding.FragmentForFutherscopeBinding;
import com.example.a2zbilling.db.entities.Expenses;
import com.example.a2zbilling.db.entities.Purchase;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.StockActivityViewModel;

import java.util.List;

public class RFUStockFragment extends Fragment {
    private FragmentForFutherscopeBinding fragmentForFutherscopeBinding;
    FurtherScopeAdapter adepter;
    private StockActivityViewModel stockActivityViewModel;
    double total=0;

    public RFUStockFragment(StockActivityViewModel stockActivityViewModel) {
        this.stockActivityViewModel = stockActivityViewModel;
    }

    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentForFutherscopeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_for_futherscope, container, false);
        fragmentForFutherscopeBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentForFutherscopeBinding.recyclerView.setHasFixedSize(true);
        adepter = new FurtherScopeAdapter();
        fragmentForFutherscopeBinding.recyclerView.setAdapter(adepter);

         stockActivityViewModel.getAllPurchase().observe(getActivity(), new Observer<List<Purchase>>() {
          @Override
          public void onChanged(List<Purchase> purchases) {
              adepter.setPurchases(purchases);
              }
         });

//
        adepter.setOnItemRecyclerViewlistener(new FurtherScopeAdapter.OnItemRecyclerViewListener() {
            @Override
            public void onItemClick(Purchase purchase) {
                stockActivityViewModel.setPurchase(purchase);
                fragmentForFutherscopeBinding.recyclerView.setVisibility(View.GONE);
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                PurchaseHistoryFragments purchaseHistoryFragments=new PurchaseHistoryFragments(stockActivityViewModel);
                fragmentTransaction.replace(R.id.fragment_conterner1,purchaseHistoryFragments);
                fragmentTransaction.commit();
            }
        });

         return fragmentForFutherscopeBinding.getRoot();
    }
}
