package com.example.a2zbilling.stock.addUpdate;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.a2zbilling.CloudRepository;
import com.example.a2zbilling.db.Repository;
import com.example.a2zbilling.db.entities.Stock;

public class AddUpdateStockActivityViewModel extends AndroidViewModel {


    private Repository repository;
    //just for test
    private CloudRepository cloudRepository;


    public AddUpdateStockActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);

        //just for test
        cloudRepository=new CloudRepository(application);
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

    //just for test
    public void insertStock(Stock stock) {
        cloudRepository.insertStock(stock);
    }
    public void updatetStock(Stock stock,String document) {
        cloudRepository.updateStock(stock,document);
    }


}
