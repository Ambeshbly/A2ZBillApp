package com.example.a2zbilling.db.entities;

import android.widget.TextView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "stock_table")
public class Stock extends BaseObservable implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int itemId;
    private int itemImage;
    private int purchaseId;
    private String itemName;
    private double itemQuentity;
    private String itemUnit;
    private String itemPurchasePerUnit;
    private String itemPuchaseTotal;
    private String itemSalePerUnit;
    private String itemSaleTotal;
    private String pc;


    public Stock(String itemName, double itemQuentity, String itemPurchasePerUnit, String itemPuchaseTotal, String itemSalePerUnit, String itemSaleTotal) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemQuentity = itemQuentity;
        this.itemUnit = itemUnit;
        this.itemPurchasePerUnit = itemPurchasePerUnit;
        this.itemPuchaseTotal = itemPuchaseTotal;
        this.itemSalePerUnit = itemSalePerUnit;
        this.itemSaleTotal = itemSaleTotal;
    }


    @Ignore
    public Stock() {
    }

    @BindingAdapter("android:text")
    public static void setText(TextView view, double value) {

        if (view.getText().toString().isEmpty() == false) {
            double tvValue = Double.parseDouble(view.getText().toString());
            if (tvValue != value) {
                view.setText(Double.toString(value));
            }
        }
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static double getText(TextView view) {
        return Double.parseDouble(view.getText().toString());
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
    public double getItemQuentity() {
        return itemQuentity;
    }

    public void setItemQuentity(double itemQuentity) {
        this.itemQuentity = itemQuentity;
        notifyPropertyChanged(com.example.a2zbilling.BR.itemQuentity);
    }


    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;

    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    @Bindable
    public String getItemPurchasePerUnit() {
        return itemPurchasePerUnit;
    }

    public void setItemPurchasePerUnit(String itemPurchasePerUnit) {
        this.itemPurchasePerUnit = itemPurchasePerUnit;
        if (itemPurchasePerUnit.isEmpty() || itemQuentity == 0) {
            itemPuchaseTotal = Integer.toString(0);
        } else {
            itemPuchaseTotal = Double.toString(Integer.parseInt(itemPurchasePerUnit) * itemQuentity);
        }
        notifyPropertyChanged(com.example.a2zbilling.BR.itemPuchaseTotal);
        notifyPropertyChanged(com.example.a2zbilling.BR.itemPurchasePerUnit);
    }


    public void setItemPurchase(String itemPurchase) {
        this.itemPurchasePerUnit = itemPurchase;
    }

    public void setItemSale(String itemsale) {
        this.itemSalePerUnit = itemsale;

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
        if (itemSalePerUnit.isEmpty() || itemQuentity == 0) {
            itemSaleTotal = Integer.toString(0);
        } else {
            itemSaleTotal = Double.toString(Integer.parseInt(itemSalePerUnit) * itemQuentity);
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

    @Override
    public String toString() {
        return itemId+"    "+itemName;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }
}
