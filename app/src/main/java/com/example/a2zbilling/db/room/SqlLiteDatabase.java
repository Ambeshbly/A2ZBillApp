package com.example.a2zbilling.db.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.a2zbilling.DateConverter;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.Expenses;
import com.example.a2zbilling.db.entities.ExpensesCategory;
import com.example.a2zbilling.db.entities.Payment;
import com.example.a2zbilling.db.entities.Purchase;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.ShopDetail;
import com.example.a2zbilling.db.entities.Stock;

@Database(entities = {Stock.class, Customer.class, SaleDeatial.class, Sales.class, Expenses.class, ExpensesCategory.class, Payment.class, Purchase.class, ShopDetail.class}, version =78, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class SqlLiteDatabase extends RoomDatabase {

    private static SqlLiteDatabase instance;

    public static synchronized SqlLiteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), SqlLiteDatabase.class, "a2zBillDataBase78").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract StockDao itemsDao();

    public abstract SaleDeatailDao saleDeatailDao();

    public abstract SalesDao salesDao();

    public abstract CustomerDao customerDao();

    public abstract ExpensesDao expensesDao();

    public abstract PaymentDao paymentDao();

    public abstract ExpensesCategoryDao expensesCategoryDao();

    public abstract PurchaseDao purchaseDao();

    public abstract ShopDetailDao shopDetailDao();
}
