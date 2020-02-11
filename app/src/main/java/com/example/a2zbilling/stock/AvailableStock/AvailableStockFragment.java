package com.example.a2zbilling.stock.AvailableStock;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.StockActivityViewModel;
import com.example.a2zbilling.stock.addUpdate.AddUpdateStockActivity;

import java.util.List;

public class AvailableStockFragment extends Fragment {

    RecyclerView recyclerView;
    AvailableStockAdapter adepter;
    SearchView searchView;
    private StockActivityViewModel stockActivityViewModel;

    public AvailableStockFragment(StockActivityViewModel stockActivityViewModel) {
        this.stockActivityViewModel = stockActivityViewModel;
    }

    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_for_availablestocks, container, false);

        recyclerView = view.findViewById(R.id.recycler_view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adepter = new AvailableStockAdapter();
        recyclerView.setAdapter(adepter);


        stockActivityViewModel.getAllItems().observe(getViewLifecycleOwner(), new Observer<List<Stock>>() {

            @Override
            public void onChanged(List<Stock> items) {
                Toast.makeText(getContext(), "items", Toast.LENGTH_SHORT).show();
                adepter.setItems(items);
            }
        });

        searchView = view.findViewById(R.id.search_view_By_name1);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adepter.getFilter().filter(newText);
                return false;
            }
        });

        adepter.setOnItemRecyclerViewlistener(new AvailableStockAdapter.OnItemRecyclerViewListener() {
            @Override
            public void onItemClick(Stock stock) {
                Intent intent = new Intent(getContext(), AddUpdateStockActivity.class);
                intent.putExtra("Item_id", stock.getItemId());
                intent.putExtra("Item_name", stock.getItemName());
                intent.putExtra("Item_quentity", stock.getItemQuentity());
                // intent.putExtra("Item_unit",stock.getItemUnit());
                intent.putExtra("Item_purchasePrice", stock.getItemPurchasePerUnit());
                intent.putExtra("Item_purchaseTotal", stock.getItemPuchaseTotal());
                intent.putExtra("Item_salePrice", stock.getItemSalePerUnit());
                intent.putExtra("Item_saleTotal", stock.getItemSaleTotal());
                startActivity(intent);
            }
        });

        return view;
    }


}
