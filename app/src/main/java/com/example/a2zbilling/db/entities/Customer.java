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





    public Customer(String customerName, String customerAddress, String customerPhoneNo,String debt) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhoneNo = customerPhoneNo;
        this.debt=debt;
    }



    public Customer() {
    }

    @Bindable
    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
        notifyPropertyChanged(com.example.a2zbilling.BR.debt);
    }

    public void setCustId(int custId) {
        this.custId = custId;


    }


    public int getCustId() {
        return custId;
    }

//    public void setCustId(int custId, List<Customer> customers) {
//        this.custId = custId;
//        for (int i = 0; i < customers.size(); i++) {
//            Customer customer = customers.get(i);
//            if(custId==customer.getCustId()){
//                customerName=customer.getCustomerName();
//                customerAddress=customer.getCustomerName();
//                customerPhoneNo=customer.getCustomerName();
//            }
//        }
//
//        notifyPropertyChanged(com.example.a2zbilling.BR.customerName);
//        notifyPropertyChanged(com.example.a2zbilling.BR.customerAddress);
//        notifyPropertyChanged(com.example.a2zbilling.BR.customerPhoneNo);
   // }

    @Bindable
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
        notifyPropertyChanged(com.example.a2zbilling.BR.customerName);
    }



    @Bindable
    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
        notifyPropertyChanged(com.example.a2zbilling.BR.customerAddress);
    }



    @Bindable
    public String getCustomerPhoneNo() {
        return customerPhoneNo;
    }

    public void setCustomerPhoneNo(String customerPhoneNo) {
        this.customerPhoneNo = customerPhoneNo;
        notifyPropertyChanged(com.example.a2zbilling.BR.customerPhoneNo);
    }

}
