package com.example.a2zbilling;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.a2zbilling.db.Repository;
import com.example.a2zbilling.db.entities.Expenses;
import com.example.a2zbilling.db.entities.ExpensesCategory;
import com.example.a2zbilling.db.entities.SaleDeatial;

import java.util.List;

public class ExpensesActivity2ViewModel extends AndroidViewModel {


    private Repository repository;
    private LiveData<List<ExpensesCategory>> allExpensesCategory;
    private ExpensesCategory expensesCategory;

    public ExpensesActivity2ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void insertExpensesCategory(ExpensesCategory expensesCategory) {
        repository.insertExpensesCategory(expensesCategory);
    }

    public LiveData<List<ExpensesCategory>> getAllExpensesCategory(int expenseId){
        allExpensesCategory=repository.getAllExpenseCategoryBaseOnExpenseId(expenseId);
        return allExpensesCategory;
    }

    public ExpensesCategory getExpensesCategory() {
        return expensesCategory;
    }

    public void setExpensesCategory(ExpensesCategory expensesCategory) {
        this.expensesCategory = expensesCategory;
    }
}
