package com.example.a2zbilling.db.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.a2zbilling.db.entities.Customer;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface CustomerDao {

    @Insert
    long insert(Customer customer);

    @Update
    void update(Customer customer);

    @Delete
    void delete(Customer customer);

    @Query("SELECT * FROM customer")
    LiveData<List<Customer>> getAllCustomer();

    @Query("select * from customer where custId==:custID")
    Maybe<Customer> getCustomer(int custID);
}
