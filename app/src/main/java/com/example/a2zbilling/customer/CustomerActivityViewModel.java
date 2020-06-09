package com.example.a2zbilling.customer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.a2zbilling.CloudRepository;
import com.example.a2zbilling.db.Repository;
import com.example.a2zbilling.db.entities.Customer;

import java.util.List;


public class CustomerActivityViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Customer>> allCustomer;
    private Customer customer;

    //just for test
    private CloudRepository cloudRepository;

    public CustomerActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        //just for test
        cloudRepository=new CloudRepository(application);
        allCustomer = repository.getAllCustomerLiveData();
    }

    public LiveData<List<Customer>> getAllCustomer() {
        return allCustomer;
    }
    public void insertCustomer(Customer customer) {
        repository.insertCustomer(customer);
    }

    //just for test
    public void cloudInsertCustomer(Customer customer){
        cloudRepository.cloudInsertCustomer(customer);
    }

    public void updateCustomer(Customer customer) {
        repository.updateCustomer(customer);
    }

    //just for test
    public void cloudUpdateCustomer(Customer customer){
        cloudRepository.cloudUpdateCustomer(customer);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;

    }
}
