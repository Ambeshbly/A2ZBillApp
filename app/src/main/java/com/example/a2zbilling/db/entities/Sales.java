package com.example.a2zbilling.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Sales {

    @PrimaryKey(autoGenerate = true)
    private int saleId;
    private String totalBillAmt;
    private int salescustId;
    private String salePode;
    private String date;

    // private Date date;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSalePode() {
        return salePode;
    }

    public void setSalePode(String salePode) {
        this.salePode = salePode;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public String getTotalBillAmt() {
        return totalBillAmt;
    }

    public void setTotalBillAmt(String totalBillAmt) {
        this.totalBillAmt = totalBillAmt;
    }

    public int getSalescustId() {
        return salescustId;
    }

    public void setSalescustId(int salescustId) {
        this.salescustId = salescustId;
    }


}
