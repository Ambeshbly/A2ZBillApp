package com.example.a2zbilling.db.entities;

import androidx.databinding.BaseObservable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ExpensesCategory extends BaseObservable implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int expenseCategoryid;
    private int expenseId;
    private String expenseName;
    private String expenseCategoryTotal;
    private String expenseCategoryPaymentMode;
    private String expenseCategoryDescription;

    public ExpensesCategory(int expenseId, String expenseCategoryTotal, String expenseCategoryPaymentMode, String expenseCategoryDescription,String expensesName) {
        this.expenseId = expenseId;
        this.expenseCategoryTotal = expenseCategoryTotal;
        this.expenseCategoryPaymentMode = expenseCategoryPaymentMode;
        this.expenseCategoryDescription = expenseCategoryDescription;
        this.expenseName=expensesName;
    }

    public ExpensesCategory() {
    }

    public int getExpenseCategoryid() {
        return expenseCategoryid;
    }

    public void setExpenseCategoryid(int expenseCategoryid) {
        this.expenseCategoryid = expenseCategoryid;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseCategoryTotal() {
        return expenseCategoryTotal;
    }

    public void setExpenseCategoryTotal(String expenseCategoryTotal) {
        this.expenseCategoryTotal = expenseCategoryTotal;
    }

    public String getExpenseCategoryPaymentMode() {
        return expenseCategoryPaymentMode;
    }

    public void setExpenseCategoryPaymentMode(String expenseCategoryPaymentMode) {
        this.expenseCategoryPaymentMode = expenseCategoryPaymentMode;
    }

    public String getExpenseCategoryDescription() {
        return expenseCategoryDescription;
    }

    public void setExpenseCategoryDescription(String expenseCategoryDescription) {
        this.expenseCategoryDescription = expenseCategoryDescription;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }
}
