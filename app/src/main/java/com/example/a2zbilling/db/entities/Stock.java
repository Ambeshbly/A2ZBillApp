package com.example.a2zbilling.db.entities;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
    private int id;
    private int image;
    private int purchaseId;
    private String name ="";
    private double primaryQuant;
    private double secondQuant;
    private String priamryUnit = Unit.UNIT_DEFAULT;
    private String secondUnit = Unit.UNIT_DEFAULT;
    private int pcPerBox = 1;
    private String purchasePerUnit ="";
    private String purchaseTotal ="";
    private String salePerUnit ="";
    private String saleTotal ="";

    private String barCode="0";

    @Ignore
    private static boolean setTextFlag = true;
    @Ignore
    private static View lastView ;

    @Ignore
    public Stock(Stock oldStock){
        this.id = oldStock.id;
        this.image = oldStock.image;
        this.purchaseId = oldStock.purchaseId;
        this.name = oldStock.name;
        this.primaryQuant = oldStock.primaryQuant;
        this.secondQuant = oldStock.secondQuant;
        this.priamryUnit = oldStock.priamryUnit;
        this.secondUnit = oldStock.secondUnit;
        this.pcPerBox = oldStock.pcPerBox;
        this.purchasePerUnit = oldStock.purchasePerUnit;
        this.purchaseTotal = oldStock.purchaseTotal;
        this.salePerUnit = oldStock.salePerUnit;
        this.saleTotal = oldStock.saleTotal;
        barCode=oldStock.barCode;

    }

    public Stock() {
    }

    @BindingAdapter("android:text")
    public static void setText(@NonNull TextView view, double value) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public double getPrimaryQuant() {
        return primaryQuant;
    }

    public void setPrimaryQuant(double primaryQuant) {
        this.primaryQuant = primaryQuant;
        notifyPropertyChanged(BR.primaryQuant);
    }

    public void updatePrimaryQuant(double primaryQuant){
        this.primaryQuant = primaryQuant;
        this.secondQuant = primaryQuant * pcPerBox;
        notifyPropertyChanged(BR.primaryQuant);
    }


    @Bindable
    public String getPriamryUnit() {
        return priamryUnit;
    }

    public void setPriamryUnit(String priamryUnit) {
        this.priamryUnit = priamryUnit;
        notifyPropertyChanged(BR.priamryUnit);

    }

    public double getSecondQuant() {
        return secondQuant;
    }

    public void setSecondQuant(double secondQuant) {
        this.secondQuant = secondQuant;


    }
    public void updateSecondQuant(double secondQuant){
        this.secondQuant = secondQuant;
        this.primaryQuant = secondQuant / pcPerBox;
        notifyPropertyChanged(BR.primaryQuant);
    }

    public String getSecondUnit() {
        return secondUnit;
    }

    public void setSecondUnit(String secondUnit) {
        this.secondUnit = secondUnit;
    }

    @Bindable
    public String getPurchasePerUnit() {
        return purchasePerUnit;
    }

    public void setPurchasePerUnit(String purchasePerUnit) {
        this.purchasePerUnit = purchasePerUnit;
        if (purchasePerUnit.isEmpty() || primaryQuant == 0) {
            purchaseTotal = Integer.toString(0);
        } else {
            purchaseTotal = Double.toString(Double.parseDouble(purchasePerUnit) * primaryQuant);
        }
        notifyPropertyChanged(BR.purchasePerUnit);
        notifyPropertyChanged(BR.purchaseTotal);
    }
    public void updatePurchasePerUnit(String purchasePerUnit){
        this.purchasePerUnit = purchasePerUnit;
    }


    public void setItemPurchase(String itemPurchase) {
        this.purchasePerUnit = itemPurchase;
    }

    public void setItemSale(String itemsale) {
        this.salePerUnit = itemsale;

    }


    @Bindable
    public String getPurchaseTotal() {
        return purchaseTotal;
    }

    public void setPurchaseTotal(String purchaseTotal) {
        this.purchaseTotal = purchaseTotal;
        notifyPropertyChanged(BR.purchaseTotal);
    }

    @Bindable
    public String getSalePerUnit() {
        return salePerUnit;
    }

    public void setSalePerUnit(String salePerUnit) {
        this.salePerUnit = salePerUnit;
        if (salePerUnit.isEmpty() || primaryQuant == 0) {
            saleTotal = Integer.toString(0);
        } else {
            saleTotal = Double.toString(Double.parseDouble(salePerUnit) * primaryQuant);
        }
        notifyPropertyChanged(BR.saleTotal);
        notifyPropertyChanged(BR.salePerUnit);
    }

    public void updateSalePerUnit(String salePerUnit){
        this.salePerUnit = salePerUnit;
    }

    @Bindable
    public String getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(String saleTotal) {
        this.saleTotal = saleTotal;
    }

    @Override
    public String toString() {
        return id +"    "+ name;
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

    public int getPcPerBox() {
        return pcPerBox;
    }

    public void setPcPerBox(int pcPerBox) {
        this.pcPerBox = pcPerBox;

    }
}
