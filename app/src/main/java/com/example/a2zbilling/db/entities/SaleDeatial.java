package com.example.a2zbilling.db.entities;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Sales.class, parentColumns = "saleId", childColumns = "saledetailsaleid", onDelete = ForeignKey.CASCADE)

})
public class SaleDeatial {

    @PrimaryKey(autoGenerate = true)
    private int saleDetailId;
    private int saledetailsaleid;
    private int saleDetailitemId;
    private String quntity;
    private String salingPrice;

    public SaleDeatial(int saledetailsaleid, int saleDetailitemId, String quntity, String salingPrice) {
        this.saledetailsaleid = saledetailsaleid;
        this.saleDetailitemId = saleDetailitemId;
        this.quntity = quntity;
        this.salingPrice = salingPrice;
    }

    public SaleDeatial() {
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

    public String getQuntity() {
        return quntity;
    }

    public void setQuntity(String quntity) {
        this.quntity = quntity;
    }

    public String getSalingPrice() {
        return salingPrice;
    }

    public void setSalingPrice(String salingPrice) {
        this.salingPrice = salingPrice;
    }
}
