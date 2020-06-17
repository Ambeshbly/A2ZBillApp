package com.example.a2zbilling.customer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.a2zbilling.CloudRepository;
import com.example.a2zbilling.db.Repository;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.Payment;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;

import java.util.List;

public class ShowCustomerTransactionDetailActivityViewModel extends AndroidViewModel {


    private Repository repository;
    private LiveData<List<Sales>> allSales;
    private LiveData<List<Sales>> allSalesForcustomer;

    private CloudRepository cloudRepository;

    private LiveData<List<SaleDeatial>> allSaleDetail;
    private List<SaleDeatial> saleDeatialList;
    private Sales sales;
    private Customer customer;
    private LiveData<List<Payment>> allPayments;


    public ShowCustomerTransactionDetailActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allSales = repository.getAllSales();
        allPayments = repository.getAllPayment();

        //just for test
        cloudRepository=new CloudRepository(application);


    }



    public LiveData<List<Sales>>  getAllsaleForcustomer(int custId){
        return repository.getAllSalesForcustomer(custId);
    }



    public void insertSales(Sales sales) {
        repository.insertSales(sales);
    }

    //just for test
    public void cloudInsertSales(Sales sales){
        cloudRepository.cloudInsertSale(sales);
    }

    public void updateCustomerDebt(String debt,Customer customer){
        cloudRepository.updateCustomerDebt(debt,customer);
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
    public LiveData<List<Payment>> getAllPayments() {
        return allPayments;
    }
    public void insertPayment(Payment payment) {
        repository.insertPayment(payment);
    }
}
