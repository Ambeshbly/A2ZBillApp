package com.example.a2zbilling;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.a2zbilling.db.Repository;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.Expenses;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;

import java.util.ArrayList;
import java.util.List;

public class ExpensesActivityViewModel extends AndroidViewModel {


    private Repository repository;
    private LiveData<List<Expenses>> allExpenses;

    //just for test
    private CloudRepository cloudRepository;

    public ExpensesActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allExpenses=repository.getAllExpenses();

        //just for test
        cloudRepository=new CloudRepository(application);
    }
    public LiveData<List<Expenses>> getAllExpenses() {
        return allExpenses;
    }
    public void insertExpenses(Expenses expenses) {
        repository.insertExpenses(expenses);
    }

    public  void cloudInsertExpenes(Expenses expenses){
        cloudRepository.cloudInsertExpenses(expenses);
    }



 }
