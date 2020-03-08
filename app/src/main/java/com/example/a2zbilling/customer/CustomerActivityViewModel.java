package com.example.a2zbilling.customer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.a2zbilling.db.Repository;
import com.example.a2zbilling.db.entities.Customer;

import java.util.List;


public class CustomerActivityViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Customer>> allCustomer;
    private Customer customer;

    public CustomerActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allCustomer = repository.getAllCustomerLiveData();
    }

    public LiveData<List<Customer>> getAllCustomer() {
        return allCustomer;
    }
    public void insertCustomer(Customer customer) {
        repository.insertCustomer(customer);
    }
    public void updateCustomer(Customer customer) {
        repository.updateCustomer(customer);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;

    }
}
