package com.example.a2zbilling.customer.AllCustomer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;

public class AllCustomerFragment extends Fragment {
    RecyclerView recyclerView;
    AllCustomerAdapter adepter;

    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_customer,container,false);
    }

    @Override
   public void onStart() {
        super.onStart();
        recyclerView=getView().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adepter=new AllCustomerAdapter();
        recyclerView.setAdapter(adepter);

    }
}
