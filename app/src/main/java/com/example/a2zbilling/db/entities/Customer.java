package com.example.a2zbilling.db.entities;

import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.a2zbilling.BR;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "customer")
public class Customer extends BaseObservable implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int custId;
    private String customerName="";
    private String customerAddress="";
    private String customerPhoneNo="";
    private String debt="0" ;

    public Customer(String customerName, String customerPhoneNo, String customerAddress,String debt) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhoneNo = customerPhoneNo;
        this.debt=debt;
    }
    @Ignore
    public Customer() {
    }

   @Override
   public String toString() {
       return custId+"    "+customerName;
    }
    @Bindable
    public String getDebt() {
        return debt;
    }
    public void setDebt(String debt) {
        this.debt = debt;
        notifyPropertyChanged(BR.debt);
    }
    public void setCustId(int custId) {
        this.custId = custId;
        notifyPropertyChanged(BR.custId);
    }
    @Bindable
    public int getCustId() {
        return custId;
    }

    @Bindable
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
        notifyPropertyChanged(BR.customerName);
    }
    @Bindable
    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
        notifyPropertyChanged(BR.customerAddress);
    }
    @Bindable
    public String getCustomerPhoneNo() {
        return customerPhoneNo;
    }
    public void setCustomerPhoneNo(String customerPhoneNo) {
        this.customerPhoneNo = customerPhoneNo;
        notifyPropertyChanged(BR.customerPhoneNo);
    }

}
