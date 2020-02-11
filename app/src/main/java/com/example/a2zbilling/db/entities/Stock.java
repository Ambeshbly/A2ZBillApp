package com.example.a2zbilling.db.entities;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "stock_table")
public class Stock extends BaseObservable implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int itemId;
    private int itemImage;
    private String itemName;
    private String itemQuentity;
    private String itemUnit;
    private String itemPurchasePerUnit;
    private String itemPuchaseTotal;
    private String itemSalePerUnit;
    private String itemSaleTotal;


    public Stock(String itemName, String itemQuentity, String itemPurchasePerUnit, String itemPuchaseTotal, String itemSalePerUnit, String itemSaleTotal) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemQuentity = itemQuentity;
        this.itemUnit = itemUnit;
        this.itemPurchasePerUnit = itemPurchasePerUnit;
        this.itemPuchaseTotal = itemPuchaseTotal;
        this.itemSalePerUnit = itemSalePerUnit;
        this.itemSaleTotal = itemSaleTotal;
    }


    public Stock() {
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemImage() {
        return itemImage;
    }

    public void setItemImage(int itemImage) {
        this.itemImage = itemImage;
    }

    @Bindable
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
        notifyPropertyChanged(com.example.a2zbilling.BR.itemName);
    }

    @Bindable
    public String getItemQuentity() {
        return itemQuentity;
    }

    public void setItemQuentity(String itemQuentity) {
        this.itemQuentity = itemQuentity;
        notifyPropertyChanged(com.example.a2zbilling.BR.itemQuentity);
    }


    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;

    }

    @Bindable
    public String getItemPurchasePerUnit() {
        return itemPurchasePerUnit;
    }

    public void setItemPurchasePerUnit(String itemPurchasePerUnit) {
        this.itemPurchasePerUnit = itemPurchasePerUnit;
        if (itemPurchasePerUnit.isEmpty() || itemQuentity.isEmpty()) {
            itemPuchaseTotal = Integer.toString(0);
        } else {
            itemPuchaseTotal = Integer.toString(Integer.parseInt(itemPurchasePerUnit) * Integer.parseInt(itemQuentity));
        }
        notifyPropertyChanged(com.example.a2zbilling.BR.itemPuchaseTotal);
        notifyPropertyChanged(com.example.a2zbilling.BR.itemPurchasePerUnit);
    }

    @Bindable
    public String getItemPuchaseTotal() {
        return itemPuchaseTotal;
    }

    public void setItemPuchaseTotal(String itemPuchaseTotal) {
        this.itemPuchaseTotal = itemPuchaseTotal;
        notifyPropertyChanged(com.example.a2zbilling.BR.itemPuchaseTotal);
    }

    @Bindable
    public String getItemSalePerUnit() {
        return itemSalePerUnit;
    }

    public void setItemSalePerUnit(String itemSalePerUnit) {
        this.itemSalePerUnit = itemSalePerUnit;
        if (itemSalePerUnit.isEmpty() || itemQuentity.isEmpty()) {
            itemSaleTotal = Integer.toString(0);
        } else {
            itemSaleTotal = Integer.toString(Integer.parseInt(itemSalePerUnit) * Integer.parseInt(itemQuentity));
        }
        notifyPropertyChanged(com.example.a2zbilling.BR.itemSaleTotal);
        notifyPropertyChanged(com.example.a2zbilling.BR.itemSalePerUnit);
    }

    @Bindable
    public String getItemSaleTotal() {
        return itemSaleTotal;
    }

    public void setItemSaleTotal(String itemSaleTotal) {
        this.itemSaleTotal = itemSaleTotal;
    }


}
