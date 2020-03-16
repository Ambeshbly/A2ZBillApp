package com.example.a2zbilling.counter.BillList;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.db.entities.Sales;

import java.util.List;

public class BillHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BillHistoryActivityAdapter adepter;
    private BillHistoryActivityViewModel billHistoryActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conform_list);
        getSupportActionBar().setTitle("Transactional List");

        recyclerView = findViewById(R.id.recycler_view_billHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setHasFixedSize(true);

        adepter = new BillHistoryActivityAdapter(this);
        recyclerView.setAdapter(adepter);

        billHistoryActivityViewModel = ViewModelProviders.of(this).get(BillHistoryActivityViewModel.class);


        billHistoryActivityViewModel.getAllSales().observe(this, new Observer<List<Sales>>() {

            @Override
            public void onChanged(List<Sales> sales) {
                Toast.makeText(getBaseContext(), "Sales ", Toast.LENGTH_SHORT).show();
                adepter.setItems(sales);
            }
        });

      adepter.setOnItemRecyclerViewlistener(new BillHistoryActivityAdapter.OnItemRecyclerViewListener() {
          @Override
          public void onItemClick(Sales sales) {
              billHistoryActivityViewModel.setSales(sales);

              String id=Integer.toString(sales.getSaleId());
              Toast.makeText(getBaseContext(), id, Toast.LENGTH_SHORT).show();


              recyclerView.setVisibility(View.GONE);
              FragmentManager fm=getSupportFragmentManager();
              FragmentTransaction fragmentTransaction = fm.beginTransaction();
              ShowBillingHistoryFragments showBillingHistoryFragments=new ShowBillingHistoryFragments(billHistoryActivityViewModel);
              fragmentTransaction.replace(R.id.fragment_conterner1,showBillingHistoryFragments);
              fragmentTransaction.commit();


          }
      });


    }
}
