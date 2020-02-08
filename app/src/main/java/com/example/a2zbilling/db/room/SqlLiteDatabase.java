package com.example.a2zbilling.db.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.a2zbilling.db.entities.Stock;

@Database(entities = {Stock.class}, version = 2, exportSchema = false)
public abstract class SqlLiteDatabase extends RoomDatabase {

    private static SqlLiteDatabase instance;

    public static synchronized SqlLiteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), SqlLiteDatabase.class, "a2zBillDataBase").fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract StockDao itemsDao();
}
