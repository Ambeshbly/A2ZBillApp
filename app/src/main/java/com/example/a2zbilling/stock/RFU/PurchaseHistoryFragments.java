package com.example.a2zbilling.stock.RFU;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.counter.BillList.BillHistoryActivityViewModel;
import com.example.a2zbilling.counter.BillList.ShowBillingHistoryFragmentAdapter;
import com.example.a2zbilling.customer.ShowCustomerTransactionDetailActivityViewModel;
import com.example.a2zbilling.db.entities.Purchase;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.AvailableStock.CloudAvailableStockAdapter;
import com.example.a2zbilling.stock.StockActivityViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;


public class PurchaseHistoryFragments extends Fragment {
    private StockActivityViewModel stockActivityViewModel;
    RecyclerView recyclerView;
    private CloudPurchaseHistoryFragmentAdapter cloudPurchaseHistoryFragmentAdapter;

    public PurchaseHistoryFragments(StockActivityViewModel stockActivityViewModel) {
        this.stockActivityViewModel = stockActivityViewModel;
    }

    //just for test
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //get userCurrent id
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String userId=currentUser.getUid();
    private CollectionReference usersRef=db.collection("users").document(userId).collection("stocks");


    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_for_purchase_history, container, false);
       recyclerView = view.findViewById(R.id.recyclerView_for_salesDetail);


        //just for test
        Query query=usersRef.whereEqualTo("purchaseId",stockActivityViewModel.getPurchase().getPurchaseId());
        FirestoreRecyclerOptions<Stock> options=new FirestoreRecyclerOptions.Builder<Stock>().setQuery(query,Stock.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        cloudPurchaseHistoryFragmentAdapter=new CloudPurchaseHistoryFragmentAdapter(options,getContext());
        recyclerView.setAdapter(cloudPurchaseHistoryFragmentAdapter);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        cloudPurchaseHistoryFragmentAdapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        cloudPurchaseHistoryFragmentAdapter.startListening();

    }
}
