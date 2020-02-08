package com.example.a2zbilling.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.db.room.SqlLiteDatabase;
import com.example.a2zbilling.db.room.StockDao;

import java.util.List;

public class Repository {
    private StockDao stockDao;
    private LiveData<List<Stock>> allItems;

    public Repository(Application application) {
        SqlLiteDatabase database= SqlLiteDatabase.getInstance(application);
        stockDao =database.itemsDao();
        allItems= stockDao.getAllItems();
    }

    public void insert(Stock stock){
        new InsertItemsAsynckTask(stockDao).execute(stock);
    }

    public void delete(Stock stock){
        new DeleteItemsAsynckTask(stockDao).execute(stock);
    }

    public void update(Stock stock){
        new UpdateItemsAsynckTask(stockDao).execute(stock);
    }

    public LiveData<List<Stock>> getAllItems(){
        return allItems;
    }

   private static class InsertItemsAsynckTask extends AsyncTask<Stock,Void,Void>{
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

    private static class DeleteItemsAsynckTask extends AsyncTask<Stock,Void,Void>{
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

    private static class UpdateItemsAsynckTask extends AsyncTask<Stock,Void,Void>{
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
