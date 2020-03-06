package com.example.a2zbilling.customer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.a2zbilling.db.Repository;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;

import java.util.List;

public class ShowCustomerTransactionDetailActivityViewModel extends AndroidViewModel {


    private Repository repository;
    private LiveData<List<Sales>> allSales;
    private LiveData<List<Sales>> allSalesForcustomer;

    private LiveData<List<SaleDeatial>> allSaleDetail;
    private List<SaleDeatial> saleDeatialList;
    private Sales sales;
    private Customer customer;


    public ShowCustomerTransactionDetailActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allSales = repository.getAllSales();


    }



    public LiveData<List<Sales>>  getAllsaleForcustomer(int custId){
        return repository.getAllSalesForcustomer(custId);
    }



    public void insertSales(Sales sales) {
        repository.insertSales(sales);
    }
    public LiveData<List<Sales>> getAllSales() {
        return allSales;
    }

    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }


    public LiveData<List<SaleDeatial>> getAllSaleDetail(int saleId){
        allSaleDetail=repository.getSalesDeatil(saleId);
        return allSaleDetail;
    }

    public List<SaleDeatial> getSaleDeatialList(int saleId) {
        saleDeatialList = repository.getSaleDeatialList(saleId);
        return saleDeatialList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
