package com.example.a2zbilling.db.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.a2zbilling.db.entities.Stock;

import java.util.List;

@Dao
public interface StockDao {

    @Insert
    void insert(Stock stock);

    @Update
    void update(Stock stock);

    @Delete
    void delete(Stock stock);

    @Query("SELECT * FROM stock_table")
    LiveData<List<Stock>> getAllItems();
}
