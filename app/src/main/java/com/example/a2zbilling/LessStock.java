package com.example.a2zbilling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.a2zbilling.db.entities.Stock;
import java.util.ArrayList;
import java.util.List;

public class LessStock extends AppCompatActivity {

    private LessStockViewModel lessStockViewModel;
    LessStockAdapter adepter;
    private RecyclerView recyclerView;
    List<Stock> list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_less_stock);
        setTitle("Less Stocks");
        recyclerView=findViewById(R.id.recycler_view9);
        lessStockViewModel = ViewModelProviders.of(this).get(LessStockViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setHasFixedSize(true);
        adepter = new LessStockAdapter(getBaseContext());
        recyclerView.setAdapter(adepter);
        lessStockViewModel.getAllItems().observe(this, new Observer<List<Stock>>() {
            @Override
            public void onChanged(List<Stock> stocks) {
                List<Stock> stockArrayList = new ArrayList<Stock>();
                for (int i = 0; i < stocks.size(); i++) {
                    Stock stock= stocks.get(i);
                    if(stock.getPrimaryQuant()<10){
                        stockArrayList.add(stock);
                    }
                }
                adepter.setItems(stockArrayList);
            }
        });
    }
}
