package com.example.a2zbilling.counter.Selling;

import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.AvailableStock.AvailableStockAdapter;

import java.util.List;

public class SellingStocksActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AddToCartAdapter adepter;
    SearchView searchView1;
    private SellingActivityViewModel sellingActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_card);

        recyclerView = findViewById(R.id.recycler_view4);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adepter = new AddToCartAdapter();
        recyclerView.setAdapter(adepter);


        sellingActivityViewModel = ViewModelProviders.of(this).get(SellingActivityViewModel.class);

        sellingActivityViewModel.getAllItems().observe(this, new Observer<List<Stock>>() {
            @Override
            public void onChanged(List<Stock> stocks) {
                Toast.makeText(getBaseContext(), "ovaerver", Toast.LENGTH_SHORT).show();
                adepter.setItems(stocks);
            }
        });


        searchView1 = findViewById(R.id.search_view_of_addtocart);
        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

                // addToCartActivityViewModel.setStock(stock);


                DialogFragmentForAddToCart ialogFragementforunit = new DialogFragmentForAddToCart(sellingActivityViewModel);
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
