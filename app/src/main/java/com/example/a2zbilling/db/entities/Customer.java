package com.example.a2zbilling.db.entities;

import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.a2zbilling.BR;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "customer")
public class Customer extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private int custId;
    private String customerName;
    private String customerAddress;
    private String customerPhoneNo;
    private String debt;

    public Customer(String customerName, String customerPhoneNo, String customerAddress,String debt) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhoneNo = customerPhoneNo;
        this.debt=debt;
    }
    public Customer() {
    }

   @Override
   public String toString() {
       return custId+"    "+customerName;
    }
    public String getDebt() {
        return debt;
    }
    public void setDebt(String debt) {
        this.debt = debt;
    }
    public void setCustId(int custId) {
        this.custId = custId;
    }
    public int getCustId() {
        return custId;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
    public String getCustomerPhoneNo() {
        return customerPhoneNo;
    }
    public void setCustomerPhoneNo(String customerPhoneNo) {
        this.customerPhoneNo = customerPhoneNo;
    }

}
