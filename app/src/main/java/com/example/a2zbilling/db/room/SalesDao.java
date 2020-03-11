package com.example.a2zbilling.db.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.a2zbilling.db.entities.Sales;

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

    @Query("SELECT * FROM sales WHERE date BETWEEN :dayst AND :dayet")
    List<Sales> getFromTable(long dayst, long dayet);

    @Query("SELECT * FROM sales WHERE date==:date")
    List<Sales> getTodaysale(long date);





}
