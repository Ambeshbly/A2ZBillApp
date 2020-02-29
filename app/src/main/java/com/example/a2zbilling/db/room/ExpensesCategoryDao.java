package com.example.a2zbilling.db.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.a2zbilling.db.entities.Expenses;
import com.example.a2zbilling.db.entities.ExpensesCategory;

import java.util.List;

@Dao
public interface ExpensesCategoryDao {

    @Insert
    void insert(ExpensesCategory expensesCategory);

    @Query("SELECT * FROM ExpensesCategory  WHERE expenseId==:expenseId")
    LiveData<List<ExpensesCategory>> getAllExpensesCategory(int expenseId);
}
