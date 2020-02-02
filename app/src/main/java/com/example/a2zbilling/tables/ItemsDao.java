package com.example.a2zbilling.tables;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemsDao {

    @Insert
    void insert(Items items);

    @Update
    void update(Items items);

    @Delete
    void delete(Items items);

    @Query("SELECT * FROM items_table")
    LiveData<List<Items>> getAllItems();
}
