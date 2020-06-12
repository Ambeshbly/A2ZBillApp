package com.example.a2zbilling.counter.SuspendedBills;

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
import com.example.a2zbilling.counter.BillList.CloudShowBillHistoryAdapter;
import com.example.a2zbilling.counter.BillList.ShowBillingHistoryFragmentAdapterForPriceQntyValue;
import com.example.a2zbilling.customer.ShowCustomerTransactionDetailActivityViewModel;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.SuspendDetail;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;


public class ShowSuspendDetailFragments extends Fragment {

    RecyclerView recyclerViewPriceQntyValue;
    private int suspendId;
    private TextView grendTotal;
    private String totalamount;


    //just for test
    private CloudShowSuspendDetailAdapter cloudShowSuspendDetailAdapter;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //get userCurrent id
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String userId=currentUser.getUid();
    private CollectionReference usersRef=db.collection("users").document(userId).collection("suspend detail");

    public ShowSuspendDetailFragments(int suspendId,String totalamount) {
        this.suspendId=suspendId;
        this.totalamount=totalamount;
    }

    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_for_show_suspend_detail, container, false);

        recyclerViewPriceQntyValue=view.findViewById(R.id.recyclerView_for_Price_qunty_value1);
        grendTotal=view.findViewById(R.id.TextView_grandTotal);

        Query query=usersRef.whereEqualTo("suspendId",suspendId);
        //Query query=usersRef.orderBy("quntity", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<SuspendDetail> options=new FirestoreRecyclerOptions.Builder<SuspendDetail>().setQuery(query,SuspendDetail.class).build();
        recyclerViewPriceQntyValue.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPriceQntyValue.setHasFixedSize(true);
        cloudShowSuspendDetailAdapter= new CloudShowSuspendDetailAdapter(options,getContext());
        recyclerViewPriceQntyValue.setAdapter(cloudShowSuspendDetailAdapter);

        grendTotal.setText(totalamount);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        cloudShowSuspendDetailAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        cloudShowSuspendDetailAdapter.stopListening();
    }
}
