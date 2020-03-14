package com.example.a2zbilling;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.a2zbilling.db.Repository;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.ShopDetail;
import com.example.a2zbilling.db.entities.Stock;

import java.util.List;

public class ShopSettingActivityViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<ShopDetail>> allShopDetail;

    public ShopSettingActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allShopDetail=repository.getAllShopDetail();

    }
    public void insertShopetail(ShopDetail shopDetail) {
        repository.insertShopDetail(shopDetail);
    }

    public LiveData<List<ShopDetail>> getAllShopDetail(){
        return allShopDetail;
    }
}
