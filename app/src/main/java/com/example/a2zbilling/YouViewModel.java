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

public class YouViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Customer>> allCustomer;
    private LiveData<List<Stock>> allItems;
    private LiveData<List<Expenses>> allExpenses;

    public YouViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allCustomer = repository.getAllCustomerLiveData();
        allItems = repository.getAllItems();
        allExpenses=repository.getAllExpenses();
    }
    public LiveData<List<Customer>> getAllCustomer() {
        return allCustomer;
    }
    public LiveData<List<Stock>> getAllItems() {
        return allItems;
    }
    public List<Sales> getSalesFromToFrom(long startDate,long endDate){
        return repository.getAllFromToFrom(startDate,endDate);
    }
    public List<SaleDeatial> getAllSAlesList(int saleId){
        return repository.getSaleDeatialList(saleId);
    }
    public LiveData<List<Expenses>> getAllExpenses() {
        return allExpenses;
    }
}
