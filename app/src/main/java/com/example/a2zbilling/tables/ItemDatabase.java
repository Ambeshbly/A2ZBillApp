package com.example.a2zbilling.tables;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Items.class},version = 1)
public abstract class ItemDatabase extends RoomDatabase {

    private static ItemDatabase instance;
    public abstract ItemsDao itemsDao();

    public static synchronized ItemDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),ItemDatabase.class,"item_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }return instance;
    }
}
