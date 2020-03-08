package com.example.a2zbilling.db.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.a2zbilling.db.entities.Customer;

import java.util.List;

@Dao
public interface CustomerDao {

    @Insert
    long insert(Customer customer);

    @Update
    void update(Customer customer);

    @Delete
    void delete(Customer customer);

    @Query("SELECT * FROM customer")
    LiveData<List<Customer>> getAllCustomerLiveData();

    @Query("select * from customer where custId==:custID")
    LiveData<Customer> getCustomer(int custID);
}
