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
import com.example.a2zbilling.db.room.SalesDao;

import java.util.ArrayList;
import java.util.List;

public class TestViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Sales>> allSales;


    public TestViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);

    }
    public LiveData<List<Sales>> getAllSales() {
        return allSales;
    }
    public List<Sales> getTodaySales(long date){
        return repository.getTodaySales(date);
    }
    public List<Sales> getSalesFromToFrom(long startDate,long endDate){
        return repository.getAllFromToFrom(startDate,endDate);
    }
    public List<SaleDeatial> getAllSAlesList(int saleId){
        return repository.getSaleDeatialList(saleId);
    }

}
