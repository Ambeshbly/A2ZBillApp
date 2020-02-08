package com.example.a2zbilling.stock;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.a2zbilling.db.Repository;
import com.example.a2zbilling.db.entities.Stock;

import java.util.List;

public class AddUpdateStockActivityViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Stock>> allItems;

    public AddUpdateStockActivityViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
        allItems=repository.getAllItems();
    }
    public void insert(Stock stock){
        repository.insert(stock);
    }
    public void delete(Stock stock){
        repository.delete(stock);
    }
    public void update(Stock stock){
        repository.update(stock);
    }

    public LiveData<List<Stock>> getAllItems(){
        return allItems;
    }
}
