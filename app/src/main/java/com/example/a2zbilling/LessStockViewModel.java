package com.example.a2zbilling;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.a2zbilling.db.Repository;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.Expenses;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;

import java.util.List;

public class LessStockViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Stock>> allItems;


    public LessStockViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allItems = repository.getAllItems();

    }

    public LiveData<List<Stock>> getAllItems() {
        return allItems;
    }
}
