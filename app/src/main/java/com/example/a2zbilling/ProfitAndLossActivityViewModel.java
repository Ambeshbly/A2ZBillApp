package com.example.a2zbilling;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.a2zbilling.db.Repository;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Stock;

import java.util.ArrayList;
import java.util.List;

public class ProfitAndLossActivityViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Stock>> allItems;
    private LiveData<List<SaleDeatial>> allSaleDetail;
    public ProfitAndLossActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allItems = repository.getAllItems();
        allSaleDetail=repository.getSalesDeatilBaseOnItemId1();
    }

    public LiveData<List<Stock>> getAllItems() {
        return allItems;
    }

    public LiveData<List<SaleDeatial>> getSalesDeatilBaseOnItemId1() {
        return allSaleDetail;
    }
}
