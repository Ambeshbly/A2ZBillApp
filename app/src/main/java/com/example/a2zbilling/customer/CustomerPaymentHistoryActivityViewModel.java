package com.example.a2zbilling.customer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.a2zbilling.db.Repository;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.Payment;

import java.util.List;


public class CustomerPaymentHistoryActivityViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Payment>> allPayments;

    public CustomerPaymentHistoryActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allPayments = repository.getAllPayment();
    }

    public LiveData<List<Payment>> getAllPayments() {
        return allPayments;
    }
    public void insertPayment(Payment payment) {
        repository.insertPayment(payment);
    }


}
