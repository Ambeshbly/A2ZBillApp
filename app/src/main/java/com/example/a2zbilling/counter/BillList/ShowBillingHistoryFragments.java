package com.example.a2zbilling.counter.BillList;

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
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.AvailableStock.CloudAvailableStockAdapter;
import com.example.a2zbilling.stock.RFU.CloudPurchaseHistoryFragmentAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class ShowBillingHistoryFragments extends Fragment {

    RecyclerView recyclerView;
    RecyclerView recyclerViewPriceQntyValue;
    //ShowBillingHistoryFragmentAdapterForStock adepter;
    ShowBillingHistoryFragmentAdapterForPriceQntyValue showBillingHistoryFragmentAdapterForPriceQntyValue;
    private BillHistoryActivityViewModel billHistoryActivityViewModel;
    TextView textViewTotal;
    private int cheak;
    private double grandTotal;



    //just for test
    private CloudShowBillHistoryAdapter cloudShowBillHistoryAdapter;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //get userCurrent id
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String userId=currentUser.getUid();
    private CollectionReference usersRef=db.collection("users").document(userId).collection("sale Detail");



    private ShowCustomerTransactionDetailActivityViewModel showCustomerTransactionDetailActivityViewModel;

    public ShowBillingHistoryFragments(BillHistoryActivityViewModel billHistoryActivityViewModel) {
        this.billHistoryActivityViewModel=billHistoryActivityViewModel;
    }

    public ShowBillingHistoryFragments(ShowCustomerTransactionDetailActivityViewModel showCustomerTransactionDetailActivityViewModel,int cheak) {
        this.showCustomerTransactionDetailActivityViewModel = showCustomerTransactionDetailActivityViewModel;
        this.cheak=cheak;
    }

    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_for_show_billing_history, container, false);

        textViewTotal=view.findViewById(R.id.TextView_grandTotal);

      /*  recyclerView = view.findViewById(R.id.recyclerView_for_salesDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);*/

        recyclerViewPriceQntyValue = view.findViewById(R.id.recyclerView_for_Price_qunty_value1);
       /* recyclerViewPriceQntyValue.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPriceQntyValue.setHasFixedSize(true);*/

       /* adepter = new ShowBillingHistoryFragmentAdapterForStock();
        recyclerView.setAdapter(adepter);*/

       /* showBillingHistoryFragmentAdapterForPriceQntyValue=new ShowBillingHistoryFragmentAdapterForPriceQntyValue();
        recyclerViewPriceQntyValue.setAdapter(showBillingHistoryFragmentAdapterForPriceQntyValue);*/






        if (cheak==1){

            Sales sales=showCustomerTransactionDetailActivityViewModel.getSales();
            int salesId=sales.getSaleId();
            showCustomerTransactionDetailActivityViewModel.getAllSaleDetail(salesId).observe(getViewLifecycleOwner(), new Observer<List<SaleDeatial>>() {
                @Override
                public void onChanged(List<SaleDeatial> saleDeatials) {
                   // adepter.setSaleDeatials(saleDeatials);
                    showBillingHistoryFragmentAdapterForPriceQntyValue.setSaleDeatials(saleDeatials);
                    for (int i = 0; i < saleDeatials.size(); i++) {
                        SaleDeatial saleDeatial = saleDeatials.get(i);
                        grandTotal=grandTotal+Double.parseDouble(saleDeatial.getSalingPrice());
                        textViewTotal.setText(" \u20B9 "+grandTotal);
                    }
                }
            });
        }else{

          /*  Sales sales=billHistoryActivityViewModel.getSales();
            int salesId=sales.getSaleId();
            billHistoryActivityViewModel.getAllSaleDetail(salesId).observe(getViewLifecycleOwner(), new Observer<List<SaleDeatial>>() {
                @Override
                public void onChanged(List<SaleDeatial> saleDeatials) {
                  //  adepter.setSaleDeatials(saleDeatials);
                    showBillingHistoryFragmentAdapterForPriceQntyValue.setSaleDeatials(saleDeatials);
                    for (int i = 0; i < saleDeatials.size(); i++) {
                        SaleDeatial saleDeatial = saleDeatials.get(i);
                        grandTotal=grandTotal+(Double.parseDouble(saleDeatial.getSalingPrice())*saleDeatial.getQuntity());
                        textViewTotal.setText(" \u20B9 "+grandTotal);
                    }
                }
            }); */



            //just for test
            Sales sales=billHistoryActivityViewModel.getSales();
            int salesId=sales.getSaleId();
            Query query=usersRef.whereEqualTo("saledetailsaleid",salesId);
            //Query query=usersRef.orderBy("quntity", Query.Direction.DESCENDING);
            FirestoreRecyclerOptions<SaleDeatial> options=new FirestoreRecyclerOptions.Builder<SaleDeatial>().setQuery(query,SaleDeatial.class).build();
            recyclerViewPriceQntyValue.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewPriceQntyValue.setHasFixedSize(true);
            cloudShowBillHistoryAdapter=new CloudShowBillHistoryAdapter(options,getContext());
            recyclerViewPriceQntyValue.setAdapter(cloudShowBillHistoryAdapter);


        }

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();


     /*  usersRef.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
               if(queryDocumentSnapshots.isEmpty()){
               }else {
                   List<DocumentSnapshot>  documentSnapshots=queryDocumentSnapshots.getDocuments();
                   for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                       DocumentSnapshot documentSnapshot=documentSnapshots.get(i);
                       SaleDeatial saleDeatial=documentSnapshot.toObject(SaleDeatial.class);
                       grandTotal=grandTotal+(Double.parseDouble(saleDeatial.getSalingPrice())*saleDeatial.getQuntity());
                       //textViewTotal.setText(" \u20B9 "+grandTotal);
                   }
               }
            }
        });*/

        cloudShowBillHistoryAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        cloudShowBillHistoryAdapter.stopListening();
    }
}
