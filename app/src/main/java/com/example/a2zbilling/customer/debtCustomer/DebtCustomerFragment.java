package com.example.a2zbilling.customer.debtCustomer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.a2zbilling.R;
import com.example.a2zbilling.customer.AllCustomer.CloudAllCustomerAdapter;
import com.example.a2zbilling.customer.CustomerActivityViewModel;
import com.example.a2zbilling.customer.ShowCustomerTransactionDetailActivity;
import com.example.a2zbilling.databinding.DebtCustomerBinding;
import com.example.a2zbilling.db.entities.Customer;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class DebtCustomerFragment extends Fragment {

    debtCustomerAdapter adepter;
    private DebtCustomerBinding debtCustomerBinding;
    private CustomerActivityViewModel customerActivityViewModel;

    public DebtCustomerFragment(CustomerActivityViewModel customerActivityViewModel) {
        this.customerActivityViewModel = customerActivityViewModel;
    }


    //just for test
    private CloudDebtCustomerAdapter cloudDebtCustomerAdapter;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //get userCurrent id
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String userId=currentUser.getUid();
    private CollectionReference usersRef=db.collection("users").document(userId).collection("customers");




    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        debtCustomerBinding = DataBindingUtil.inflate(inflater, R.layout.debt_customer, container, false);
        getActivity().setTitle("Debt Customer");
     /*   debtCustomerBinding.recyclerViewForDebtCustomer.setLayoutManager(new LinearLayoutManager(getContext()));
        debtCustomerBinding.recyclerViewForDebtCustomer.setHasFixedSize(true);
        adepter=new debtCustomerAdapter(getContext(),customerActivityViewModel);
        debtCustomerBinding.recyclerViewForDebtCustomer.setAdapter(adepter);
        customerActivityViewModel.getAllCustomer().observe(getViewLifecycleOwner(), new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                List<Customer> customerList=new ArrayList<Customer>();
                for (int i = 0; i < customers.size(); i++) {
                    Customer customer = customers.get(i);
                    if(customer.getDebt().equals("0")) {
                    }else{
                        customerList.add(customer);
                    }
                }
                adepter.setCustomers(customerList);
            }
        });*/
     /*   adepter.setOnItemRecyclerViewlistener(new debtCustomerAdapter.OnItemRecyclerViewListener() {
            @Override
            public void onItemClick(Customer customer) {
                Intent intent = new Intent(getContext(), ShowCustomerTransactionDetailActivity.class);
                intent.putExtra("customer_transaction",customer);
                startActivity(intent);
            }
        });*/


        Query query=usersRef.whereGreaterThan("debt","0.0");
        FirestoreRecyclerOptions<Customer> options=new FirestoreRecyclerOptions.Builder<Customer>().setQuery(query,Customer.class).build();
        debtCustomerBinding.recyclerViewForDebtCustomer.setLayoutManager(new LinearLayoutManager(getContext()));
        debtCustomerBinding.recyclerViewForDebtCustomer.setHasFixedSize(true);
        cloudDebtCustomerAdapter=new CloudDebtCustomerAdapter(options,getContext(),customerActivityViewModel);
        debtCustomerBinding.recyclerViewForDebtCustomer.setAdapter(cloudDebtCustomerAdapter);


        return debtCustomerBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        cloudDebtCustomerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        cloudDebtCustomerAdapter.stopListening();
    }
}
