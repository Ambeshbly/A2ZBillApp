package com.example.a2zbilling.db.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;

import java.util.List;

@Dao
public interface SalesDao {


    @Insert
    long insert(Sales sales);

    @Query("SELECT * From Sales")
    LiveData<List<Sales>> getAllSales();

    @Query("SELECT * FROM sales WHERE saleId==:saleId")
    LiveData<List<Sales>> getSales(int saleId);

    @Query("SELECT * FROM sales WHERE salescustId==:custId")
    LiveData<List<Sales>> getSalesOfCustomer(int custId);


}
