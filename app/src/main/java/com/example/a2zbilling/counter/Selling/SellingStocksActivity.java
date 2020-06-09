package com.example.a2zbilling.counter.Selling;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.a2zbilling.R;
import com.example.a2zbilling.ScannerActivity;
import com.example.a2zbilling.databinding.ActivityAddToCardBinding;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.AvailableStock.AvailableStockAdapter;
import com.example.a2zbilling.stock.AvailableStock.CloudAvailableStockAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class SellingStocksActivity extends AppCompatActivity {

    AddToCartAdapter adepter;
    private SellingActivityViewModel sellingActivityViewModel;
    private ActivityAddToCardBinding activityAddToCardBinding;
    private CloudAddToCartAdapter cloudAddToCartAdapter;


    //just for test
    private CloudAvailableStockAdapter cloudAvailableStockAdapter;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //get userCurrent id
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String userId=currentUser.getUid();
    private CollectionReference usersRef=db.collection("users").document(userId).collection("stocks");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_card);
        activityAddToCardBinding= DataBindingUtil.setContentView(this,R.layout.activity_add_to_card);
/*        activityAddToCardBinding.recyclerView4.setLayoutManager(new LinearLayoutManager(this));
        activityAddToCardBinding.recyclerView4.setHasFixedSize(true);
        adepter = new AddToCartAdapter();
        activityAddToCardBinding.recyclerView4.setAdapter(adepter);*/
        sellingActivityViewModel = ViewModelProviders.of(this).get(SellingActivityViewModel.class);

     /*   sellingActivityViewModel.getAllItems().observe(this, new Observer<List<Stock>>() {
            @Override
            public void onChanged(List<Stock> stocks) {
                adepter.setItems(stocks);
            }
        });
*/


        //just for test
        Query query=usersRef.orderBy("name",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Stock> options=new FirestoreRecyclerOptions.Builder<Stock>().setQuery(query,Stock.class).build();
        activityAddToCardBinding.recyclerView4.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        activityAddToCardBinding.recyclerView4.setHasFixedSize(true);
        cloudAddToCartAdapter=new CloudAddToCartAdapter(options,this,sellingActivityViewModel);
        activityAddToCardBinding.recyclerView4.setAdapter(cloudAddToCartAdapter);


        //set tittle bar for add to card activity
        getSupportActionBar().setTitle("Billing Counter");
       /* adepter.setOnItemRecyclerViewlistener(new AvailableStockAdapter.OnItemRecyclerViewListener() {
            @Override
            public void onItemClick(Stock stock) {
                String name = stock.getName();
                sellingActivityViewModel.setStock(stock);
                DialogFragmentForAddToCart ialogFragementforunit = new DialogFragmentForAddToCart(stock);
                ialogFragementforunit.show(getSupportFragmentManager(), "exampledialog");

            }
        });*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK){
            String barCode=data.getExtras().getString("satva");
          //  adepter.getFilter().filter(barCode);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.scan:
                Intent intent=new Intent(SellingStocksActivity.this, ScannerActivity.class);
                startActivityForResult(intent,1);
                return true;

            case R.id.search_view_By:
                SearchView searchView=(SearchView)item.getActionView();
                searchView.setQueryHint("search Item");
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                      //  adepter.getFilter().filter(newText);
                        return false;
                    }
                });
                default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        cloudAddToCartAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cloudAddToCartAdapter.stopListening();
    }
}
