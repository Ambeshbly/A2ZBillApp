package com.example.a2zbilling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.a2zbilling.counter.MainActivityViewModel;
import com.example.a2zbilling.counter.CounterFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity  {
    //bottom navigation declaration
    private BottomNavigationView bottomNavigationView;

    private MainActivityViewModel addToCartActivityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bottom navigation finding in XML file
        bottomNavigationView=findViewById(R.id.bottom_navigation);

        //bottom navigation selection listener
        bottomNavigationView.setOnNavigationItemSelectedListener(nav_listener);


        addToCartActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        //which fragment is show whenever app is open
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conterner,new CounterFragment(addToCartActivityViewModel)).commit();

    }

    //bottom navigation action by switch
    private BottomNavigationView.OnNavigationItemSelectedListener nav_listener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment=new YouFragment();
            switch (menuItem.getItemId()){
                case R.id.nav_counter:
                selectedFragment=new CounterFragment(addToCartActivityViewModel);
                break;
                case R.id.nav_deshboad:
                    selectedFragment=new Dashboard();
                    break;
                case R.id.nav_me:
                    selectedFragment=new YouFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conterner,selectedFragment).commit();
            return true;
        }
    };
}
