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
import com.example.a2zbilling.counter.Selling.CloudAddToCartAdapter;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.AvailableStock.CloudAvailableStockAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class BillHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BillHistoryActivityAdapter adepter;
    private BillHistoryActivityViewModel billHistoryActivityViewModel;


    private CloudBillHistoryAdapter cloudBillHistoryAdapter;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //get userCurrent id
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String userId=currentUser.getUid();
    private CollectionReference usersRef=db.collection("users").document(userId).collection("sales");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conform_list);
        getSupportActionBar().setTitle("Transactional List");

        recyclerView = findViewById(R.id.recycler_view_billHistory);
        /*
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setHasFixedSize(true);
*/
        billHistoryActivityViewModel = ViewModelProviders.of(this).get(BillHistoryActivityViewModel.class);


        //just for test
        Query query=usersRef.orderBy("saleId",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Sales> options=new FirestoreRecyclerOptions.Builder<Sales>().setQuery(query,Sales.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setHasFixedSize(true);
        cloudBillHistoryAdapter=new CloudBillHistoryAdapter(options,this,billHistoryActivityViewModel,BillHistoryActivity.this);
        recyclerView.setAdapter(cloudBillHistoryAdapter);

       /* adepter = new BillHistoryActivityAdapter(this, BillHistoryActivity.this, billHistoryActivityViewModel);
        recyclerView.setAdapter(adepter);*/


        /*billHistoryActivityViewModel.getAllSales().observe(this, new Observer<List<Sales>>() {

            @Override
            public void onChanged(List<Sales> sales) {
                adepter.setItems(sales);
            }
        });*/

      /*adepter.setOnItemRecyclerViewlistener(new BillHistoryActivityAdapter.OnItemRecyclerViewListener() {
          @Override
          public void onItemClick(Sales sales) {
         //click listener code
          }
      });*/


    }

    @Override
    protected void onStart() {
        super.onStart();
        cloudBillHistoryAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cloudBillHistoryAdapter.stopListening();
    }
}
