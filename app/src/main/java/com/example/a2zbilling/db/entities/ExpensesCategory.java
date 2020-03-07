package com.example.a2zbilling.db.entities;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.a2zbilling.BR;

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
    private String date;

    public ExpensesCategory(int expenseId, String expenseCategoryTotal, String expenseCategoryPaymentMode, String expenseCategoryDescription,String expensesName,String date) {
        this.expenseId = expenseId;
        this.expenseCategoryTotal = expenseCategoryTotal;
        this.expenseCategoryPaymentMode = expenseCategoryPaymentMode;
        this.expenseCategoryDescription = expenseCategoryDescription;
        this.expenseName=expensesName;
        this.date=date;
    }

    public ExpensesCategory() {
    }

    @Bindable
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }

    public int getExpenseCategoryid() {
        return expenseCategoryid;
    }

    public void setExpenseCategoryid(int expenseCategoryid) {
        this.expenseCategoryid = expenseCategoryid;
    }

    @Bindable
    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
        notifyPropertyChanged(BR.expenseId);
    }

    @Bindable
    public String getExpenseCategoryTotal() {
        return expenseCategoryTotal;
    }

    public void setExpenseCategoryTotal(String expenseCategoryTotal) {
        this.expenseCategoryTotal = expenseCategoryTotal;
        notifyPropertyChanged(BR.expenseCategoryTotal);
    }

    @Bindable
    public String getExpenseCategoryPaymentMode() {
        return expenseCategoryPaymentMode;
    }

    public void setExpenseCategoryPaymentMode(String expenseCategoryPaymentMode) {
        this.expenseCategoryPaymentMode = expenseCategoryPaymentMode;
        notifyPropertyChanged(BR.expenseCategoryPaymentMode);
    }

    @Bindable
    public String getExpenseCategoryDescription() {
        return expenseCategoryDescription;
    }

    public void setExpenseCategoryDescription(String expenseCategoryDescription) {
        this.expenseCategoryDescription = expenseCategoryDescription;
        notifyPropertyChanged(BR.expenseCategoryDescription);
    }

    @Bindable
    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
        notifyPropertyChanged(BR.expenseName);
    }
}
