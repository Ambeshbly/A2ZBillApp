package com.example.a2zbilling.db.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;

@Database(entities = {Stock.class, Customer.class, SaleDeatial.class, Sales.class}, version = 43, exportSchema = false)
public abstract class SqlLiteDatabase extends RoomDatabase {

    private static SqlLiteDatabase instance;

    public static synchronized SqlLiteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), SqlLiteDatabase.class, "a2zBillDataBase43").fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract StockDao itemsDao();

    public abstract SaleDeatailDao saleDeatailDao();

    public abstract SalesDao salesDao();

    public abstract CustomerDao customerDao();
}
