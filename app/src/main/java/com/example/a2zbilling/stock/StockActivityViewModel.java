package com.example.a2zbilling.stock;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.a2zbilling.db.Repository;
import com.example.a2zbilling.db.entities.Stock;

import java.util.ArrayList;
import java.util.List;

public class StockActivityViewModel extends AndroidViewModel {


    private Repository repository;
    private LiveData<List<Stock>> allItems;
    private ArrayList<Stock> newlyAddedStockList = new ArrayList<Stock>();
    private MutableLiveData<ArrayList<Stock>> newlyAddedStocks = new MutableLiveData<ArrayList<Stock>>();

    public StockActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allItems = repository.getAllItems();
        this.newlyAddedStocks.setValue(newlyAddedStockList);
    }

    public LiveData<ArrayList<Stock>> getNewlyAddedStocks() {

        return newlyAddedStocks;
    }

    public void addNewlyAddedStock(Stock newlyAddedStock) {


        this.newlyAddedStocks.getValue().add(newlyAddedStock);

    }

    public void insert(Stock stock) {
        repository.insert(stock);
    }

    public void delete(Stock stock) {
        repository.delete(stock);
    }

    public void update(Stock stock) {
        repository.update(stock);
    }

    public LiveData<List<Stock>> getAllItems() {
        return allItems;
    }

    public  ArrayList<Stock> getTemproryItemList(){
        return newlyAddedStockList;
    }
}
