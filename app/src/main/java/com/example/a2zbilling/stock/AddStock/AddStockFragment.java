package com.example.a2zbilling.stock.AddStock;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.a2zbilling.R;
import com.example.a2zbilling.databinding.FragmentForAddstocksBinding;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.StockActivity;
import com.example.a2zbilling.stock.StockActivityViewModel;
import java.util.ArrayList;


public class AddStockFragment extends Fragment {

    AddStockAdapter adepter;
    private FragmentForAddstocksBinding fragmentForAddstocksBinding;

    private StockActivityViewModel stockActivityViewModel;
    public AddStockFragment(StockActivityViewModel stockActivityViewModel) {
        this.stockActivityViewModel = stockActivityViewModel;
    }

    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentForAddstocksBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_for_addstocks, container, false);
        fragmentForAddstocksBinding.recyclerViewForAddStock.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentForAddstocksBinding.recyclerViewForAddStock.setHasFixedSize(true);
        adepter = new AddStockAdapter();
        fragmentForAddstocksBinding.recyclerViewForAddStock.setAdapter(adepter);
        stockActivityViewModel.getNewlyAddedStocks().observe(getViewLifecycleOwner(), new Observer<ArrayList<Stock>>() {
            @Override
            public void onChanged(ArrayList<Stock> stocks) {
                adepter.setItems(stocks);
            }
        });
        return fragmentForAddstocksBinding.getRoot();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_data_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
            case R.id.save:
                Toast.makeText(getContext(), "save", Toast.LENGTH_SHORT).show();
                ArrayList<Stock> stockList= stockActivityViewModel.getTemproryItemList();

                for(int i = 0; i <stockList.size(); i++)
                {
                    Stock stock=stockList.get(i);
                    stockActivityViewModel.insert(stock);
                }
                getActivity().finish();

                Intent intent=new Intent(getContext(), StockActivity.class);
                startActivity(intent);
                return false;
               default:
                break;
        }
        return false;
    }



}
