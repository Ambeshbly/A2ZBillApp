package com.example.a2zbilling.stock;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.a2zbilling.R;
import com.example.a2zbilling.YouFragment;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.AddStock.AddStockFragment;
import com.example.a2zbilling.stock.AvailableStock.AvailableStockFragment;
import com.example.a2zbilling.stock.RFU.RFUStockFragment;
import com.example.a2zbilling.stock.addUpdate.AddUpdateStockActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class StockActivity extends AppCompatActivity {


    MediaPlayer mediaPlayer;
    StockActivityViewModel stockActivityViewModel;
    //declartion of Bottom navigation
    private BottomNavigationView bottomNavigationView;
    //selection on bottom navigation by switch
    private BottomNavigationView.OnNavigationItemSelectedListener nav_listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = new YouFragment();
            switch (menuItem.getItemId()) {
                case R.id.nav_addstocks:
                    mediaPlayer.start();
                    selectedFragment = new AddStockFragment(stockActivityViewModel);
                    break;
                case R.id.nav_availablestocks:
                    mediaPlayer.start();
                    selectedFragment = new AvailableStockFragment(stockActivityViewModel);
                    break;
                case R.id.nav_futhurescope:
                    mediaPlayer.start();
                    selectedFragment = new RFUStockFragment(stockActivityViewModel);
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conterner_for_addstocks, selectedFragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //finding bottom navigation in xml file
        bottomNavigationView = findViewById(R.id.bottom_navigation_for_addstocks);

        //finding floating button in Xml file


        stockActivityViewModel = ViewModelProviders.of(this).get(StockActivityViewModel.class);
        mediaPlayer= MediaPlayer.create(getBaseContext(),R.raw.simple);

        //set tittle in the action bar
        getSupportActionBar().setTitle("Stocks");

        //set selection navigation items
        bottomNavigationView.setOnNavigationItemSelectedListener(nav_listener);

        //set which fragment is show whenever user com in additem activity
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conterner_for_addstocks, new AddStockFragment(stockActivityViewModel)).commit();
    }

    @Override
    public void onBackPressed() {
        ArrayList<Stock> stockList= stockActivityViewModel.getTemproryItemList();
        if(!stockList.isEmpty()) {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Save Stock").setMessage("Are you want to save stock?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList<Stock> stockList = stockActivityViewModel.getTemproryItemList();

                    for (int i = 0; i < stockList.size(); i++) {
                        Stock stock = stockList.get(i);
                        stockActivityViewModel.insert(stock);
                    }

                    finish();
                }

            }).setNegativeButton("No", null).show();
        }else {
            finish();
        }
    }
}
