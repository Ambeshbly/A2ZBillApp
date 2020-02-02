package com.example.a2zbilling.tables;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.a2zbilling.MainActivity;

import java.util.List;

public class ItemsViewModel extends AndroidViewModel {

    private ItemsRepository repository;
    private LiveData<List<Items>> allItems;

    public ItemsViewModel(@NonNull Application application) {
        super(application);
        repository=new ItemsRepository(application);
        allItems=repository.getAllItems();
    }
    public void insert(Items items){
        repository.insert(items);
    }
    public void delete(Items items){
        repository.delete(items);
    }
    public void update(Items items){
        repository.update(items);
    }

    public LiveData<List<Items>> getAllItems(){
        return allItems;
    }
}
