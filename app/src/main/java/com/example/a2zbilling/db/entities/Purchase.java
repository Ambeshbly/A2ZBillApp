package com.example.a2zbilling.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Purchase {

    @PrimaryKey(autoGenerate = true)
    private int purchaseId;
    private String itemNumber;
    private String date;
    private String Total;


    public Purchase(String itemNumber, String date,String total) {
        this.itemNumber = itemNumber;
        this.date = date;
        this.Total=total;
    }

    public Purchase() {
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
