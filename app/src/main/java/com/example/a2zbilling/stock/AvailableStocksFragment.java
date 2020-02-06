package com.example.a2zbilling.stock;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.tables.Items;
import com.example.a2zbilling.tables.ItemsViewModel;

import java.util.List;

public class AvailableStocksFragment extends Fragment {

    private ItemsViewModel itemsViewModel;
    RecyclerView recyclerView;
    AdepterForAvailableItems adepter;
    SearchView searchView;



    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_for_availablestocks,container,false);

        recyclerView=view.findViewById(R.id.recycler_view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adepter=new AdepterForAvailableItems();
        recyclerView.setAdapter(adepter);


        itemsViewModel= ViewModelProviders.of(this).get(ItemsViewModel.class);
        itemsViewModel.getAllItems().observe(getViewLifecycleOwner(), new Observer<List<Items>>() {

            @Override
            public void onChanged(List<Items> items) {
                Toast.makeText(getContext(),"items",Toast.LENGTH_SHORT).show();
                adepter.setItems(items);
            }
        });

        searchView=view.findViewById(R.id.search_view_By_name1);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adepter.getFilter().filter(newText);
                return false;
            }
        });

        return view;
    }


}
