package com.example.a2zbilling.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.Expenses;
import com.example.a2zbilling.db.entities.ExpensesCategory;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.db.room.CustomerDao;
import com.example.a2zbilling.db.room.ExpensesCategoryDao;
import com.example.a2zbilling.db.room.ExpensesDao;
import com.example.a2zbilling.db.room.SaleDeatailDao;
import com.example.a2zbilling.db.room.SalesDao;
import com.example.a2zbilling.db.room.SqlLiteDatabase;
import com.example.a2zbilling.db.room.StockDao;

import java.util.List;

public class Repository {
    private StockDao stockDao;
    private SalesDao salesDao;
    private CustomerDao customerDao;
    private SaleDeatailDao saleDeatailDao;
    private ExpensesDao expensesDao;
    private ExpensesCategoryDao expensesCategoryDao;


    public Repository(Application application) {
        SqlLiteDatabase database = SqlLiteDatabase.getInstance(application);
        stockDao = database.itemsDao();
        salesDao = database.salesDao();
        customerDao = database.customerDao();
        saleDeatailDao = database.saleDeatailDao();
        expensesDao=database.expensesDao();
        expensesCategoryDao=database.expensesCategoryDao();
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
        return stockDao.getAllItems();
    }
    public LiveData<List<Expenses>> getAllExpenses() {
        return expensesDao.getAllExpenses();
    }
    public LiveData<List<ExpensesCategory>> getAllExpenseCategoryBaseOnExpenseId(int expenseId) {
        return expensesCategoryDao.getAllExpensesCategory(expenseId);
    }
    public void insertSales(Sales sales) {
        new InsertSalesAsynckTask(salesDao).execute(sales);
    }


    public void insertExpensesCategory(ExpensesCategory expensesCategory) {
        new InsertExpensesCategoryAsynckTask(expensesCategoryDao).execute(expensesCategory);
    }
    private static class InsertExpensesCategoryAsynckTask extends AsyncTask<ExpensesCategory, Void, Void> {
        private ExpensesCategoryDao expensesCategoryDao;

        public InsertExpensesCategoryAsynckTask(ExpensesCategoryDao expensesCategoryDao) {
            this.expensesCategoryDao = expensesCategoryDao;
        }

        @Override
        protected Void doInBackground(ExpensesCategory... expensesCategories) {
            expensesCategoryDao.insert(expensesCategories[0]);
            return null;
        }
    }




    public void insertExpenses(Expenses expenses) {
        new InsertExpensesAsynckTask(expensesDao).execute(expenses);
    }


    private static class InsertExpensesAsynckTask extends AsyncTask<Expenses, Void, Void> {
        private ExpensesDao expensesDao;

        public InsertExpensesAsynckTask(ExpensesDao expensesDao) {
            this.expensesDao = expensesDao;
        }

        @Override
        protected Void doInBackground(Expenses... expenses) {
            expensesDao.insert(expenses[0]);
            return null;
        }
    }


    public LiveData<List<Sales>> getAllSales() {
        return salesDao.getAllSales();
    }


    public void insertSaleDeatail(SaleDeatial saleDeatial) {
        new InsertSalesDeatilAsynckTask(saleDeatailDao).execute(saleDeatial);
    }

    public LiveData<List<SaleDeatial>> getSalesDeatil(int salesId) {
        return saleDeatailDao.getSaleDetail(salesId);
    }


    public LiveData<List<SaleDeatial>> getSalesDeatilBaseOnItemId1() {
        return saleDeatailDao.getSaleDetailBaseOnItem1();
    }

    public List<SaleDeatial> getSaleDeatialList(int saleId) {
        return saleDeatailDao.getAllDetailList(saleId);
    }

    public void insertCustomer(Customer customer) {
        new InsertCustomersAsynckTask(customerDao).execute(customer);
    }

    public void updateCustomer(Customer customer) {
        new UpdateCustomerAsynckTask(customerDao).execute(customer);
    }

    public LiveData<List<Customer>> getAllCustomer() {
        return customerDao.getAllCustomer();
    }

    public LiveData<Customer> getCustomer(int custId) {
        return customerDao.getCustomer(custId);
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

    private static class InsertCustomersAsynckTask extends AsyncTask<Customer, Void, Void> {
        private CustomerDao customerDao;

        public InsertCustomersAsynckTask(CustomerDao customerDao) {
            this.customerDao = customerDao;
        }

        @Override
        protected Void doInBackground(Customer... customers) {
            customers[0].setCustId((int) customerDao.insert(customers[0]));
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
