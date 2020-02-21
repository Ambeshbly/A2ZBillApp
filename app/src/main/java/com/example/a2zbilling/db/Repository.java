package com.example.a2zbilling.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.db.room.CustomerDao;
import com.example.a2zbilling.db.room.SaleDeatailDao;
import com.example.a2zbilling.db.room.SalesDao;
import com.example.a2zbilling.db.room.SqlLiteDatabase;
import com.example.a2zbilling.db.room.StockDao;

import java.util.List;

public class Repository {
    private StockDao stockDao;
    private LiveData<List<Stock>> allItems;

    private SalesDao salesDao;
    private LiveData<List<Sales>> allSales;

    private CustomerDao customerDao;
    private LiveData<List<Customer>> allCustomer;

    private LiveData<List<SaleDeatial>> alldetail;
    private List<SaleDeatial> saleDeatialList;
    private SaleDeatailDao saleDeatailDao;


    public Repository(Application application) {
        SqlLiteDatabase database = SqlLiteDatabase.getInstance(application);
        stockDao = database.itemsDao();
        allItems = stockDao.getAllItems();

        salesDao = database.salesDao();
        allSales = salesDao.getAllSales();

        customerDao=database.customerDao();
        allCustomer=customerDao.getAllCustomer();

        saleDeatailDao = database.saleDeatailDao();
        //alldetail=saleDeatailDao.getSaleDetail(salesId);


    }


    public void insert(Stock stock) {
        new InsertItemsAsynckTask(stockDao).execute(stock);
    }

    public void delete(Stock stock) {
        new DeleteItemsAsynckTask(stockDao).execute(stock);
    }

    public void update(Stock stock) {
        new UpdateItemsAsynckTask(stockDao).execute(stock);
    }

    public LiveData<List<Stock>> getAllItems() {
        return allItems;
    }


    public void insertSales(Sales sales) {
        new InsertSalesAsynckTask(salesDao).execute(sales);
    }

    public LiveData<List<Sales>> getAllSales() {
        return allSales;
    }


    public void insertSaleDeatail(SaleDeatial saleDeatial) {
        new InsertSalesDeatilAsynckTask(saleDeatailDao).execute(saleDeatial);
    }

    public LiveData<List<SaleDeatial>> getSalesDeatil(int salesId) {
        alldetail = saleDeatailDao.getSaleDetail(salesId);
        return alldetail;
    }

    public List<SaleDeatial> getSaleDeatialList(int saleId){
        saleDeatialList=saleDeatailDao.getAllDetailList(saleId);
        return saleDeatialList;
    }

    public void insertCustomer(Customer customer) {
        new InsertCustomersAsynckTask(customerDao).execute(customer);
    }
    public void updateCustomer(Customer customer) {
        new UpdateCustomerAsynckTask(customerDao).execute(customer);
    }

    private static class UpdateCustomerAsynckTask extends AsyncTask<Customer, Void, Void> {
        private CustomerDao customerDao;

        public UpdateCustomerAsynckTask(CustomerDao customerDao) {
            this.customerDao = customerDao;
        }

        @Override
        protected Void doInBackground(Customer... customers) {
            customerDao.update(customers[0]);
            return null;
        }
    }


    public LiveData<List<Customer>> getAllCustomer() {
        return allCustomer;
    }

    private static class InsertCustomersAsynckTask extends AsyncTask<Customer, Void, Void> {
        private CustomerDao customerDao;

        public InsertCustomersAsynckTask(CustomerDao customerDao) {
            this.customerDao = customerDao;
        }

        @Override
        protected Void doInBackground(Customer... customers) {
            customerDao.insert(customers[0]);
            return null;
        }
    }





    private static class InsertSalesDeatilAsynckTask extends AsyncTask<SaleDeatial, Void, Void> {
        private SaleDeatailDao saleDeatailDao;

        public InsertSalesDeatilAsynckTask(SaleDeatailDao saleDeatailDao) {
            this.saleDeatailDao = saleDeatailDao;
        }

        @Override
        protected Void doInBackground(SaleDeatial... saleDeatials) {
            saleDeatailDao.insert(saleDeatials[0]);
            return null;
        }
    }


    private static class InsertSalesAsynckTask extends AsyncTask<Sales, Void, Void> {
        private SalesDao salesDao;

        public InsertSalesAsynckTask(SalesDao salesDao) {
            this.salesDao = salesDao;
        }

        @Override
        protected Void doInBackground(Sales... sales) {
            sales[0].setSaleId((int) salesDao.insert(sales[0]));
            return null;
        }
    }


    private static class InsertItemsAsynckTask extends AsyncTask<Stock, Void, Void> {
        private StockDao stockDao;

        public InsertItemsAsynckTask(StockDao stockDao) {
            this.stockDao = stockDao;
        }

        @Override
        protected Void doInBackground(Stock... items) {
            stockDao.insert(items[0]);
            return null;
        }
    }

    private static class DeleteItemsAsynckTask extends AsyncTask<Stock, Void, Void> {
        private StockDao stockDao;

        public DeleteItemsAsynckTask(StockDao stockDao) {
            this.stockDao = stockDao;
        }

        @Override
        protected Void doInBackground(Stock... items) {
            stockDao.delete(items[0]);
            return null;
        }
    }

    private static class UpdateItemsAsynckTask extends AsyncTask<Stock, Void, Void> {
        private StockDao stockDao;

        public UpdateItemsAsynckTask(StockDao stockDao) {
            this.stockDao = stockDao;
        }

        @Override
        protected Void doInBackground(Stock... items) {
            stockDao.update(items[0]);
            return null;
        }
    }
}
