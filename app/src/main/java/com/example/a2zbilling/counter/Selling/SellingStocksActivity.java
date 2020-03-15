package com.example.a2zbilling.counter.Selling;

import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.databinding.ActivityAddToCardBinding;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.AvailableStock.AvailableStockAdapter;

import java.util.List;

public class SellingStocksActivity extends AppCompatActivity {

    AddToCartAdapter adepter;
    private SellingActivityViewModel sellingActivityViewModel;
    private ActivityAddToCardBinding activityAddToCardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_card);
        activityAddToCardBinding= DataBindingUtil.setContentView(this,R.layout.activity_add_to_card);
        activityAddToCardBinding.recyclerView4.setLayoutManager(new LinearLayoutManager(this));
        activityAddToCardBinding.recyclerView4.setHasFixedSize(true);
        adepter = new AddToCartAdapter();
        activityAddToCardBinding.recyclerView4.setAdapter(adepter);
        sellingActivityViewModel = ViewModelProviders.of(this).get(SellingActivityViewModel.class);

        sellingActivityViewModel.getAllItems().observe(this, new Observer<List<Stock>>() {
            @Override
            public void onChanged(List<Stock> stocks) {
                Toast.makeText(getBaseContext(), "ovaerver", Toast.LENGTH_SHORT).show();
                adepter.setItems(stocks);
            }
        });
        activityAddToCardBinding.searchViewOfAddtocart.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        //set tittle bar for add to card activity
        getSupportActionBar().setTitle("Billing Counter");
        adepter.setOnItemRecyclerViewlistener(new AvailableStockAdapter.OnItemRecyclerViewListener() {
            @Override
            public void onItemClick(Stock stock) {
                String name = stock.getItemName();
                Toast.makeText(getBaseContext(), name, Toast.LENGTH_SHORT).show();
                sellingActivityViewModel.setStock(stock);
                DialogFragmentForAddToCart ialogFragementforunit = new DialogFragmentForAddToCart(stock);
                ialogFragementforunit.show(getSupportFragmentManager(), "exampledialog");

            }
        });
    }

    //override method for cart icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart, menu);
        return true;
    }


}
