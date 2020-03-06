package com.example.a2zbilling.db.entities;

import androidx.databinding.BaseObservable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Expenses extends BaseObservable implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int expenseId;
    private String expenseCategory;
    private String expenseTotal;
    private String paymentMode;
    private String description;
    private String date;

    public Expenses(String expenseCategory, String expenseTotal, String paymentMode,String description,String date) {
        this.expenseCategory = expenseCategory;
        this.expenseTotal = expenseTotal;
        this.paymentMode = paymentMode;
        this.description=description;
        this.date=date;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public String getExpenseTotal() {
        return expenseTotal;
    }

    public void setExpenseTotal(String expenseTotal) {
        this.expenseTotal = expenseTotal;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
