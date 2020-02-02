package com.example.a2zbilling.tables;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemsRepository {
    private ItemsDao itemsDao;
    private LiveData<List<Items>> allItems;

    public ItemsRepository(Application application) {
        ItemDatabase database=ItemDatabase.getInstance(application);
        itemsDao=database.itemsDao();
        allItems=itemsDao.getAllItems();
    }

    public void insert(Items items){
        new InsertItemsAsynckTask(itemsDao).execute(items);
    }

    public void delete(Items items){
        new DeleteItemsAsynckTask(itemsDao).execute(items);
    }

    public void update(Items items){
        new UpdateItemsAsynckTask(itemsDao).execute(items);
    }

    public LiveData<List<Items>> getAllItems(){
        return allItems;
    }

   private static class InsertItemsAsynckTask extends AsyncTask<Items,Void,Void>{
        private ItemsDao itemsDao;
       public InsertItemsAsynckTask(ItemsDao itemsDao) {
           this.itemsDao=itemsDao;
       }
       @Override
       protected Void doInBackground(Items... items) {
           itemsDao.insert(items[0]);
           return null;
       }
   }

    private static class DeleteItemsAsynckTask extends AsyncTask<Items,Void,Void>{
        private ItemsDao itemsDao;
        public DeleteItemsAsynckTask(ItemsDao itemsDao) {
            this.itemsDao=itemsDao;
        }
        @Override
        protected Void doInBackground(Items... items) {
            itemsDao.delete(items[0]);
            return null;
        }
    }

    private static class UpdateItemsAsynckTask extends AsyncTask<Items,Void,Void>{
        private ItemsDao itemsDao;
        public UpdateItemsAsynckTask(ItemsDao itemsDao) {
            this.itemsDao=itemsDao;
        }
        @Override
        protected Void doInBackground(Items... items) {
            itemsDao.update(items[0]);
            return null;
        }
    }
}
