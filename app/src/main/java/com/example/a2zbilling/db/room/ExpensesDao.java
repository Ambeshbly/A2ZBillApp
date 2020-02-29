package com.example.a2zbilling.db.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.a2zbilling.db.entities.Expenses;
import java.util.List;

@Dao
public interface ExpensesDao {

    @Insert
    void insert(Expenses expenses);

    @Query("SELECT * FROM Expenses ORDER BY expenseId DESC")
    LiveData<List<Expenses>> getAllExpenses();
}
