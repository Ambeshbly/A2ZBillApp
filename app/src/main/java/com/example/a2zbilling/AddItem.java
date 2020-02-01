package com.example.a2zbilling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddItem extends AppCompatActivity {

    //declartion of Bottom navigation
    private BottomNavigationView bottomNavigationView;

    //declration of floating button for add item
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        //finding bottom navigation in xml file
        bottomNavigationView = findViewById(R.id.bottom_navigation_for_addstocks);

        //finding floating button in Xml file
        floatingActionButton=findViewById(R.id.bt_float);

        //set tittle in the action bar
        getSupportActionBar().setTitle("Your Stocks");

        //add action listener in floatin button
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "floating click", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getBaseContext(),AddItemFloatingButton.class);
                startActivity(intent);
            }
        });

        //set selection navigation items
        bottomNavigationView.setOnNavigationItemSelectedListener(nav_listener);

        //set which fragment is show whenever user com in additem activity
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conterner_for_addstocks,new AddstocksFragment()).commit();
    }


    //selection on bottom navigation by switch
    private BottomNavigationView.OnNavigationItemSelectedListener nav_listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = new MeFragment();
            switch (menuItem.getItemId()) {
                case R.id.nav_addstocks:
                    selectedFragment = new AddstocksFragment();
                    break;
                case R.id.nav_availablestocks:
                    selectedFragment = new AvailableStocksFragment();
                    break;
                case R.id.nav_futhurescope:
                    selectedFragment = new FutherScopeFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conterner_for_addstocks, selectedFragment).commit();
            return true;
        }
    };
}
