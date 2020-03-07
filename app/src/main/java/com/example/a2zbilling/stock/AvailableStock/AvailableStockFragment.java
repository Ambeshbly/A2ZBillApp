package com.example.a2zbilling.stock.AvailableStock;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.a2zbilling.R;
import com.example.a2zbilling.databinding.FragmentForAvailablestocksBinding;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.StockActivityViewModel;
import com.example.a2zbilling.stock.addUpdate.AddUpdateStockActivity;


import java.util.List;

public class AvailableStockFragment extends Fragment {
    AvailableStockAdapter adepter;
    private StockActivityViewModel stockActivityViewModel;
    private FragmentForAvailablestocksBinding fragmentForAvailablestocksBinding;
    public AvailableStockFragment(StockActivityViewModel stockActivityViewModel) {
        this.stockActivityViewModel = stockActivityViewModel;
    }
    public AvailableStockFragment() {
    }

    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentForAvailablestocksBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_for_availablestocks, container, false);
        fragmentForAvailablestocksBinding.recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentForAvailablestocksBinding.recyclerView1.setHasFixedSize(true);
        adepter = new AvailableStockAdapter();
        fragmentForAvailablestocksBinding.recyclerView1.setAdapter(adepter);
        setHasOptionsMenu(true);
        stockActivityViewModel.getAllItems().observe(getViewLifecycleOwner(), new Observer<List<Stock>>() {

            @Override
            public void onChanged(List<Stock> items) {
                Toast.makeText(getContext(), "items", Toast.LENGTH_SHORT).show();
                adepter.setItems(items);
            }
        });
        adepter.setOnItemRecyclerViewlistener(new AvailableStockAdapter.OnItemRecyclerViewListener() {
            @Override
            public void onItemClick(Stock stock) {
               Intent intent = new Intent(getContext(), AddUpdateStockActivity.class);
               intent.putExtra("stock_object",stock);
               startActivity(intent);
            }
        });
        return fragmentForAvailablestocksBinding.getRoot();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_search_menu, menu);
        MenuItem menuItem=menu.findItem(R.id.search_view_By);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setQueryHint("search Item");
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
        super.onCreateOptionsMenu(menu, inflater);
    }
}
