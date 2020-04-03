package com.example.a2zbilling.db.entities;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.a2zbilling.BR;

@Entity
public class Sales extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private int saleId;
    private String totalBillAmt="0";
    private int salescustId;
    private String salePode;
    private Long date;

    // private Date date;


    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
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

    @Bindable
    public String getTotalBillAmt() {
        return totalBillAmt;
    }

    public void setTotalBillAmt(String totalBillAmt) {
        this.totalBillAmt = totalBillAmt;
        notifyPropertyChanged(BR.totalBillAmt);
    }

    public int getSalescustId() {
        return salescustId;
    }

    public void setSalescustId(int salescustId) {
        this.salescustId = salescustId;
    }


}
