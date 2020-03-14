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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.a2zbilling.R;
import com.example.a2zbilling.YouFragment;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.AddStock.AddStockFragment;
import com.example.a2zbilling.stock.AvailableStock.AvailableStockFragment;
import com.example.a2zbilling.stock.RFU.RFUStockFragment;
import com.example.a2zbilling.stock.addUpdate.AddUpdateStockActivity;
import com.example.a2zbilling.stock.addUpdate.dialogFragementforunit;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class StockActivity extends AppCompatActivity {

    public static final int ADD_NEW_STOCK_REQ_CODE = 1;
    MediaPlayer mediaPlayer;



    //declration of floating button for add item
    FloatingActionButton floatingActionButton;
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
        floatingActionButton = findViewById(R.id.bt_float);

        stockActivityViewModel = ViewModelProviders.of(this).get(StockActivityViewModel.class);
        mediaPlayer= MediaPlayer.create(getBaseContext(),R.raw.simple);

        //set tittle in the action bar
        getSupportActionBar().setTitle("Stocks");

        //add action listener in floatin button
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Toast.makeText(getBaseContext(), "floating click", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), AddUpdateStockActivity.class);
                startActivityForResult(intent, ADD_NEW_STOCK_REQ_CODE);
            }
        });

        //set selection navigation items
        bottomNavigationView.setOnNavigationItemSelectedListener(nav_listener);

        //set which fragment is show whenever user com in additem activity
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conterner_for_addstocks, new AddStockFragment(stockActivityViewModel)).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NEW_STOCK_REQ_CODE) {
            Stock stock = (Stock) data.getSerializableExtra("stock");
            String itemName = stock.getItemName();
            stockActivityViewModel.addNewlyAddedStock(stock);
        }

    }

    @Override
    public void onBackPressed() {
        ArrayList<Stock> stockList= stockActivityViewModel.getTemproryItemList();
        if(!stockList.isEmpty()) {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Save Stock").setMessage("Are you want to save stock?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getBaseContext(), "save", Toast.LENGTH_SHORT).show();

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
