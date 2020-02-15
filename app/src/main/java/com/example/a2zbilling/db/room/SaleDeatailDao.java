package com.example.a2zbilling.db.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Stock;

import java.util.List;

@Dao
public interface SaleDeatailDao {

    @Insert
    void insert (SaleDeatial saleDeatial);


    @Query("select * from SaleDeatial where saleDetailId=:saleId")
    LiveData<List<SaleDeatial>> getSaleDetail(int saleId);
}
