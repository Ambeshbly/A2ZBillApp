package com.example.a2zbilling.db.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.a2zbilling.db.entities.Purchase;
import java.util.List;

@Dao
public interface PurchaseDao {

    @Insert
    long insert(Purchase purchase);

    @Query("SELECT * From Purchase")
    LiveData<List<Purchase>> getAllPurchase();

}
