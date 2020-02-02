package com.example.a2zbilling.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items_table")
public class Items {

    @PrimaryKey(autoGenerate = true)
    private int itemId;
    private int itemImage;
    private String itemName;
    private  int itemQuentity;
    private int itemUnit;
    private int itemPurchasePerUnit;
    private int itemPuchaseTotal;
    private int itemSalePerUnit;
    private int itemSaleTotal;

    public Items(String itemName, int itemQuentity,  int itemPurchasePerUnit, int itemPuchaseTotal, int itemSalePerUnit, int itemSaleTotal) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemQuentity = itemQuentity;
        this.itemUnit = itemUnit;
        this.itemPurchasePerUnit = itemPurchasePerUnit;
        this.itemPuchaseTotal = itemPuchaseTotal;
        this.itemSalePerUnit = itemSalePerUnit;
        this.itemSaleTotal = itemSaleTotal;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuentity() {
        return itemQuentity;
    }

    public void setItemQuentity(int itemQuentity) {
        this.itemQuentity = itemQuentity;
    }

    public int getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(int itemUnit) {
        this.itemUnit = itemUnit;
    }

    public int getItemPurchasePerUnit() {
        return itemPurchasePerUnit;
    }

    public void setItemPurchasePerUnit(int itemPurchasePerUnit) {
        this.itemPurchasePerUnit = itemPurchasePerUnit;
    }

    public int getItemPuchaseTotal() {
        return itemPuchaseTotal;
    }

    public void setItemPuchaseTotal(int itemPuchaseTotal) {
        this.itemPuchaseTotal = itemPuchaseTotal;
    }

    public int getItemSalePerUnit() {
        return itemSalePerUnit;
    }

    public void setItemSalePerUnit(int itemSalePerUnit) {
        this.itemSalePerUnit = itemSalePerUnit;
    }

    public int getItemSaleTotal() {
        return itemSaleTotal;
    }

    public void setItemSaleTotal(int itemSaleTotal) {
        this.itemSaleTotal = itemSaleTotal;
    }
}
