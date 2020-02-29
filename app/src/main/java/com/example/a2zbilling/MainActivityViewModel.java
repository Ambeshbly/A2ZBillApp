package com.example.a2zbilling;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.a2zbilling.db.Repository;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {


    private Repository repository;

    private Sales sales;
    private LiveData<List<Sales>> allSales;

    private double saleTotal;

    private LiveData<List<Customer>> allcustomer;

    private MutableLiveData<ArrayList<Stock>> newlyAddedStocks = new MutableLiveData<ArrayList<Stock>>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);

        allSales = repository.getAllSales();
        allcustomer = repository.getAllCustomer();

        ArrayList<Stock> newlyAddedStockList = new ArrayList<Stock>();
        this.newlyAddedStocks.setValue(newlyAddedStockList);

    }


    public double getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(double saleTotal) {
        this.saleTotal = saleTotal;
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
    public void insertCustomer(Customer customer) {
        repository.insertCustomer(customer);
    }
    public void updateCustomer(Customer customer) {
        repository.updateCustomer(customer);
    }


    public LiveData<List<Sales>> getAllSales() {
        return allSales;
    }
    public LiveData<List<Customer>> getAllCustomer() {
        return allcustomer;
    }


    public void insertSaleDetail(SaleDeatial saleDeatial) {
        repository.insertSaleDeatail(saleDeatial);
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
    public Sales getSales() {
        return sales;
    }
    public void setSales(Sales sales) {
        this.sales = sales;
    }


}
