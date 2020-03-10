package com.example.a2zbilling.stock.RFU;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.a2zbilling.db.Repository;
import com.example.a2zbilling.db.entities.Purchase;
import com.example.a2zbilling.db.entities.Stock;

import java.util.ArrayList;
import java.util.List;

public class FurchaseScopeViewModel extends AndroidViewModel {


    private Repository repository;
    private LiveData<List<Purchase>> allPurchase;

    public FurchaseScopeViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allPurchase = repository.getAllPurchase();

    }
    public void insert(Purchase purchase) {
        repository.insertPurchase(purchase);
    }
    public LiveData<List<Purchase>> getAllPurchase() {
        return allPurchase;
    }


}
