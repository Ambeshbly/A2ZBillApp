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
import com.example.a2zbilling.ScannerActivity;
import com.example.a2zbilling.counter.Selling.SellingStocksActivity;
import com.example.a2zbilling.databinding.FragmentForAvailablestocksBinding;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.StockActivityViewModel;
import com.example.a2zbilling.stock.addUpdate.AddUpdateStockActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AvailableStockFragment extends Fragment {
    AvailableStockAdapter adepter;
    private StockActivityViewModel stockActivityViewModel;
    private FragmentForAvailablestocksBinding fragmentForAvailablestocksBinding;

    public AvailableStockFragment(StockActivityViewModel stockActivityViewModel) {
        this.stockActivityViewModel = stockActivityViewModel;
    }
    public AvailableStockFragment() {
    }



    //just for test
    private CloudAvailableStockAdapter cloudAvailableStockAdapter;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //get userCurrent id
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String userId=currentUser.getUid();
    private CollectionReference usersRef=db.collection("users").document(userId).collection("stocks");


    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentForAvailablestocksBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_for_availablestocks, container, false);
     /*   fragmentForAvailablestocksBinding.recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentForAvailablestocksBinding.recyclerView1.setHasFixedSize(true);
        adepter = new AvailableStockAdapter(getContext());
        fragmentForAvailablestocksBinding.recyclerView1.setAdapter(adepter);
        setHasOptionsMenu(true);*/

        //just for test
        setHasOptionsMenu(true);
        Query query=usersRef.orderBy("name",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Stock> options=new FirestoreRecyclerOptions.Builder<Stock>().setQuery(query,Stock.class).build();
        fragmentForAvailablestocksBinding.recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentForAvailablestocksBinding.recyclerView1.setHasFixedSize(true);
        cloudAvailableStockAdapter=new CloudAvailableStockAdapter(options,getContext());
        fragmentForAvailablestocksBinding.recyclerView1.setAdapter(cloudAvailableStockAdapter);








//        stockActivityViewModel.getAllItems().observe(getViewLifecycleOwner(), new Observer<List<Stock>>() {
//
//            @Override
//            public void onChanged(List<Stock> items) {
//                adepter.setItems(items);
//            }
//        });
       /* adepter.setOnItemRecyclerViewlistener(new AvailableStockAdapter.OnItemRecyclerViewListener() {
            @Override
            public void onItemClick(Stock stock) {
//                Intent intent = new Intent(getContext(), AddUpdateStockActivity.class);
//                intent.putExtra("stock_object", stock);
//                startActivity(intent);
            }
        });*/
        return fragmentForAvailablestocksBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK){
            String barCode=data.getExtras().getString("satva");
           // adepter.getFilter().filter(barCode);
            cloudAvailableStockAdapter.getFilter().filter(barCode);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
                        cloudAvailableStockAdapter.getFilter().filter(newText);
                        return false;
                    }
                });
                break;

            case R.id.search_scan:
                Intent intent=new Intent(getContext(), ScannerActivity.class);
                startActivityForResult(intent,1);
                break;
        }
        return true;

    }

    @Override
    public void onStart() {
        super.onStart();
        cloudAvailableStockAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        cloudAvailableStockAdapter.stopListening();
    }
}