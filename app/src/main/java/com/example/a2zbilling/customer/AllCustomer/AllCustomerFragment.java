package com.example.a2zbilling.customer.AllCustomer;

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
import com.example.a2zbilling.customer.CustomerActivityViewModel;
import com.example.a2zbilling.customer.ShowCustomerTransactionDetailActivity;
import com.example.a2zbilling.databinding.AllCustomerBinding;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.AvailableStock.CloudAvailableStockAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class AllCustomerFragment extends Fragment {
    AllCustomerAdapter adepter;
    private CustomerActivityViewModel customerActivityViewModel;
    private AllCustomerBinding allCustomerBinding;

    public AllCustomerFragment(CustomerActivityViewModel customerActivityViewModel) {
        this.customerActivityViewModel = customerActivityViewModel;
    }


    //just for test
    private CloudAllCustomerAdapter cloudAllCustomerAdapter;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //get userCurrent id
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String userId=currentUser.getUid();
    private CollectionReference usersRef=db.collection("users").document(userId).collection("customers");



    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        allCustomerBinding = DataBindingUtil.inflate(inflater, R.layout.all_customer, container, false);
        getActivity().setTitle("All Customer");
       /* allCustomerBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        allCustomerBinding.recyclerView.setHasFixedSize(true);
        adepter=new AllCustomerAdapter(getContext(),customerActivityViewModel);
        allCustomerBinding.recyclerView.setAdapter(adepter);
        customerActivityViewModel.getAllCustomer().observe(getViewLifecycleOwner(), new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                adepter.setCustomers(customers);
            }
        });*/

      /*  adepter.setOnItemRecyclerViewlistener(new AllCustomerAdapter.OnItemRecyclerViewListener() {
            @Override
            public void onItemClick(Customer customer) {
                Intent intent = new Intent(getContext(), ShowCustomerTransactionDetailActivity.class);
                intent.putExtra("customer_transaction",customer);
                startActivity(intent);
            }
        });*/

        Query query=usersRef.orderBy("customerName",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Customer> options=new FirestoreRecyclerOptions.Builder<Customer>().setQuery(query,Customer.class).build();
        allCustomerBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        allCustomerBinding.recyclerView.setHasFixedSize(true);
        cloudAllCustomerAdapter=new CloudAllCustomerAdapter(options,getContext(),customerActivityViewModel);
        allCustomerBinding.recyclerView.setAdapter(cloudAllCustomerAdapter);
        return allCustomerBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        cloudAllCustomerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        cloudAllCustomerAdapter.stopListening();
    }
}
