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
import com.example.a2zbilling.db.entities.Purchase;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.StockActivity;
import com.example.a2zbilling.stock.StockActivityViewModel;
import com.example.a2zbilling.stock.addUpdate.AddUpdateStockActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


public class AddStockFragment extends Fragment {

    AddStockAdapter adepter;
    private FragmentForAddstocksBinding fragmentForAddstocksBinding;
    //declration of floating button for add item
    FloatingActionButton floatingActionButton;
    public static final int ADD_NEW_STOCK_REQ_CODE = 1;

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
        fragmentForAddstocksBinding.btFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddUpdateStockActivity.class);
                startActivityForResult(intent, ADD_NEW_STOCK_REQ_CODE);
            }
        });
        adepter = new AddStockAdapter();
        fragmentForAddstocksBinding.recyclerViewForAddStock.setAdapter(adepter);

        stockActivityViewModel.getNewlyAddedStocks().observe(getViewLifecycleOwner(), new Observer<ArrayList<Stock>>() {
            @Override
            public void onChanged(ArrayList<Stock> stocks) {
                if(stocks.isEmpty()){
                }else {
                    fragmentForAddstocksBinding.defaulticon.setVisibility(View.INVISIBLE);
                    fragmentForAddstocksBinding.textdefault.setVisibility(View.INVISIBLE);
                }
                adepter.setItems(stocks);
            }
        });
        return fragmentForAddstocksBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NEW_STOCK_REQ_CODE && resultCode==RESULT_OK) {
            Stock stock = (Stock) data.getSerializableExtra("stock");
            String itemName = stock.getName();
            stockActivityViewModel.addNewlyAddedStock(stock);
        }
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
                ArrayList<Stock> stockList= stockActivityViewModel.getTemproryItemList();
                int totalItems=0;
                double total=0;
                for(int i = 0; i <stockList.size(); i++)
                {
                    Stock stock=stockList.get(i);
                    total=total+Double.parseDouble(stock.getPurchaseTotal());
                    totalItems=totalItems+1;
                }
                Calendar calendar=Calendar.getInstance();
                String selecteddate= DateFormat.getDateInstance().format(calendar.getTime());
                Purchase purchase=new Purchase(Integer.toString(totalItems),selecteddate,Double.toString(total));
                stockActivityViewModel.insert(purchase);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int i = 0; i <stockList.size(); i++)
                {
                    Stock stock=stockList.get(i);
                    stock.setPurchaseId(purchase.getPurchaseId());
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
