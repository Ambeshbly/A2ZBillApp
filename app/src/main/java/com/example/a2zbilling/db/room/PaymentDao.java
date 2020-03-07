package com.example.a2zbilling.db.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.a2zbilling.db.entities.Payment;
import java.util.List;

@Dao
public interface PaymentDao {
    @Insert
    void insert(Payment payment);

    @Query("SELECT * FROM payment")
    LiveData<List<Payment>> getAllPayment();

}
