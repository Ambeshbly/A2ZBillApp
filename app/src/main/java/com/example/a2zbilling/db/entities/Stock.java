package com.example.a2zbilling.db.entities;

import android.view.View;
import android.widget.TextView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.a2zbilling.BR;
import com.example.a2zbilling.stock.addUpdate.Unit;

import java.io.Serializable;

@Entity(tableName = "stock_table")
public class Stock extends BaseObservable implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int itemId;
    private int itemImage;
    private int purchaseId;
    private String itemName="";
    private double itemQuentity;
    private String itemUnit = Unit.UNIT_DEFAULT;
    private String itemPurchasePerUnit="";
    private String itemPuchaseTotal="";
    private String itemSalePerUnit="";
    private String itemSaleTotal="";
    private String pc;
    private String barCode="0";

    @Ignore
    private static boolean setTextFlag = true;
    private static View lastView ;

    @Ignore
    public Stock(Stock oldStock){
        this.itemId = oldStock.itemId;
        this.itemImage = oldStock.itemImage;
        this.purchaseId = oldStock.purchaseId;
        this.itemName = oldStock.itemName;
        this.itemQuentity = oldStock.itemQuentity;
        this.itemUnit = oldStock.itemUnit;
        this.itemPurchasePerUnit = oldStock.itemPurchasePerUnit;
        this.itemPuchaseTotal = oldStock.itemPuchaseTotal;
        this.itemSalePerUnit = oldStock.itemSalePerUnit;
        this.itemSaleTotal = oldStock.itemSaleTotal;
        this.pc = oldStock.pc;
    }


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
    public static void setText(@org.jetbrains.annotations.NotNull TextView view, double value) {
        if(lastView != view){
            setTextFlag = true;
        }
        lastView = view;

        String quantityStr = view.getText().toString();
        if (view.getText().toString().isEmpty() == false) {
            setTextFlag = false;
            double tvValue = 0;
            if(quantityStr.contentEquals("-")){
                tvValue = 0;
            }else{
                tvValue = Double.parseDouble(view.getText().toString());
            }

            if (tvValue != value) {
                view.setText(Double.toString(value));
            }
        }else if(value != 0){
            if(setTextFlag){
                setTextFlag = false;
                view.setText(Double.toString(value));
            }

        }
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static double getText(TextView view) {
        String valueCharSeq = view.getText().toString();
        if(valueCharSeq.contentEquals("")
                ||valueCharSeq.contentEquals("-")){
            return 0;
        }
        return Double.parseDouble(valueCharSeq);
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


    @Bindable
    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
        notifyPropertyChanged(BR.itemUnit);

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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
