package com.example.a2zbilling.stock.AddStock;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.StockActivityViewModel;

import java.util.ArrayList;


public class AddStockFragment extends Fragment {

    private static Stock stock1;
    RecyclerView recyclerView;
    AddStockAdapter adepter;

    SearchView searchView;
    private StockActivityViewModel stockActivityViewModel;

    public AddStockFragment(StockActivityViewModel stockActivityViewModel) {
        this.stockActivityViewModel = stockActivityViewModel;
    }

    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_for_addstocks, container, false);


        recyclerView = view.findViewById(R.id.recycler_view_for_addStock);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adepter = new AddStockAdapter();
        recyclerView.setAdapter(adepter);
        stockActivityViewModel.getNewlyAddedStocks().observe(getViewLifecycleOwner(), new Observer<ArrayList<Stock>>() {
            @Override
            public void onChanged(ArrayList<Stock> stocks) {
                adepter.setItems(stocks);
            }
        });


        return view;
    }
}
