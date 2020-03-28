package com.example.a2zbilling;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.a2zbilling.db.Repository;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.ShopDetail;
import com.example.a2zbilling.db.entities.Stock;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {


    private Repository repository;

    private Sales sale;
    private LiveData<List<Sales>> allSales;
    private LiveData<List<ShopDetail>> allShopDetail;

    private String totalString;


    private LiveData<List<Customer>> allcustomer;

    private MutableLiveData<ArrayList<Stock>> newlyAddedStocks = new MutableLiveData<ArrayList<Stock>>();
    private ArrayList<Stock> soldStocksList = new ArrayList<Stock>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);

        allSales = repository.getAllSales();
        allcustomer = repository.getAllCustomerLiveData();
        allShopDetail=repository.getAllShopDetail();


        ArrayList<Stock> newlyAddedStockList = new ArrayList<Stock>();
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

    public Sales getSale() {
        return sale;
    }

    public String getTotalString() {
        return totalString;
    }

    public void setTotalString(String totalString) {
        this.totalString = totalString;
    }

    public void setSale(Sales sale) {
        this.sale = sale;
    }
    public LiveData<List<ShopDetail>> getAllShopDetail(){
        return allShopDetail;
    }

    public ArrayList<Stock> getSoldStocksList() {
        return soldStocksList;
    }
}
