package com.example.a2zbilling;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.a2zbilling.db.Repository;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;

import java.util.List;

public class YouViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Customer>> allCustomer;

    public YouViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allCustomer = repository.getAllCustomerLiveData();
    }
    public LiveData<List<Customer>> getAllCustomer() {
        return allCustomer;
    }
}
