package com.example.a2zbilling;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.a2zbilling.db.Repository;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {


    private Repository repository;
    private LiveData<List<Stock>> allItems;


    private Sales sales;
    private LiveData<List<Sales>> allSales;

    private SaleDeatial saleDeatial;
    private LiveData<List<SaleDeatial>> allSaledetail;


    private ArrayList<Stock> newlyAddedStockList = new ArrayList<Stock>();
    private MutableLiveData<ArrayList<Stock>> newlyAddedStocks = new MutableLiveData<ArrayList<Stock>>();


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allItems = repository.getAllItems();


        allSales = repository.getAllSales();


        this.newlyAddedStocks.setValue(newlyAddedStockList);

    }

    public LiveData<ArrayList<Stock>> getNewlyAddedStocks() {
        return newlyAddedStocks;
    }

    public void addNewlyAddedStock(Stock newlyAddedStock) {
        this.newlyAddedStocks.getValue().add(newlyAddedStock);

    }


    public void insertsales(Sales sales) {
        repository.insertSales(sales);
    }

    public LiveData<List<Sales>> getAllSales() {
        return allSales;
    }


    public void insertSaleDetail(SaleDeatial saleDeatial) {
        repository.insertSaleDeatail(saleDeatial);
    }

    public LiveData<List<SaleDeatial>> getAllSaledetail(int salesId) {
        allSaledetail = repository.getSalesDeatil(salesId);
        return allSaledetail;
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

    public ArrayList<Stock> getTemproryItemList() {
        return newlyAddedStockList;
    }
}
