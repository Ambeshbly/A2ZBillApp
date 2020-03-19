package com.example.a2zbilling.db.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.ShopDetail;

import java.util.List;

@Dao
public interface ShopDetailDao {

    @Insert
    long insert(ShopDetail shopDetail);

    @Update
    void update(ShopDetail shopDetail);

    @Delete
    void delete(ShopDetail shopDetail);

    @Query("SELECT * FROM shopDetail")
    LiveData<List<ShopDetail>> getAllShopDetail();


    @Query("SELECT * FROM shopDetail")
    List<ShopDetail> getAllShopDetail1();


}
