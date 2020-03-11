package com.example.a2zbilling.db.entities;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Sales.class, parentColumns = "saleId", childColumns = "saledetailsaleid", onDelete = ForeignKey.CASCADE)

})
public class SaleDeatial {

    @PrimaryKey(autoGenerate = true)
    private int saleDetailId;
    private int saledetailsaleid;
    private int saleDetailitemId;
    private String saleDetailItemName;
    private double quntity;
    private String salingPrice;
    private String purchasePrice;

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public SaleDeatial(int saledetailsaleid, int saleDetailitemId, double quntity, String salingPrice) {
        this.saledetailsaleid = saledetailsaleid;
        this.saleDetailitemId = saleDetailitemId;
        this.quntity = quntity;
        this.salingPrice = salingPrice;
    }

    @Ignore
    public SaleDeatial() {
    }

    public String getSaleDetailItemName() {
        return saleDetailItemName;
    }

    public void setSaleDetailItemName(String saleDetailItemName) {
        this.saleDetailItemName = saleDetailItemName;
    }

    public int getSaleDetailId() {
        return saleDetailId;
    }

    public void setSaleDetailId(int saleDetailId) {
        this.saleDetailId = saleDetailId;
    }

    public int getSaledetailsaleid() {
        return saledetailsaleid;
    }

    public void setSaledetailsaleid(int saleId) {
        this.saledetailsaleid = saleId;
    }

    public int getSaleDetailitemId() {
        return saleDetailitemId;
    }

    public void setSaleDetailitemId(int saleDetailitemId) {
        this.saleDetailitemId = saleDetailitemId;
    }

    public double getQuntity() {
        return quntity;
    }

    public void setQuntity(double quntity) {
        this.quntity = quntity;
    }

    public String getSalingPrice() {
        return salingPrice;
    }

    public void setSalingPrice(String salingPrice) {
        this.salingPrice = salingPrice;
    }
}
