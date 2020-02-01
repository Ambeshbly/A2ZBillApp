package com.example.a2zbilling.stock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.a2zbilling.YouFragment;
import com.example.a2zbilling.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DefaultItemList extends AppCompatActivity {

    //declartion for naviagtion bar
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_item_list2);

        //finding the navigation bar in the Xml file
        bottomNavigationView = findViewById(R.id.bottom_navigation_default_items);

        //selection of fragment in navigation bar
        bottomNavigationView.setOnNavigationItemSelectedListener(nav_listener);

        //which fragment is show wheneve user come in the activity
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conterner_for_defaultItems,new DefaultNewItemsFragments()).commit();
    }

    //selection of navigation fragment by the switch
    private BottomNavigationView.OnNavigationItemSelectedListener nav_listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = new YouFragment();
            switch (menuItem.getItemId()) {
                case R.id.nav_defaultaddnewitems:
                    selectedFragment = new DefaultNewItemsFragments();
                    break;
                case R.id.nav_defaultalreadyadditems:
                    selectedFragment = new DefaultAleardyAddItemsFragments();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conterner_for_defaultItems, selectedFragment).commit();
            return true;
        }
    };
}
