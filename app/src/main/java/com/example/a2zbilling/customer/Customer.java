package com.example.a2zbilling.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.a2zbilling.YouFragment;
import com.example.a2zbilling.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Customer extends AppCompatActivity {
    //declartion of floatin action button
    FloatingActionButton floatingActionButton;

    //bottom navigation declaration
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        //finding floatin button in xml file
        floatingActionButton=findViewById(R.id.bt_float);

        //add Action Listener in the floating button
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


        //bottom navigation finding in XML file
        bottomNavigationView=findViewById(R.id.bottom_navigation);

        //bottom navigation selection listener
        bottomNavigationView.setOnNavigationItemSelectedListener(nav_listener);

        //which fragment is show whenever app is open
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conterner,new DeptCustomerFragment()).commit();


    }
    public void openDialog(){
        DialogFragmentForAddCustomer ialogFragementforunit=new DialogFragmentForAddCustomer();
        ialogFragementforunit.show(getSupportFragmentManager(),"exampledialog");
    }

    //bottom navigation action by switch
    private BottomNavigationView.OnNavigationItemSelectedListener nav_listener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment=new YouFragment();
            switch (menuItem.getItemId()){
                case R.id.nav_debt_customer:
                    selectedFragment=new DeptCustomerFragment();
                    break;
                case R.id.nav_all_customer:
                    selectedFragment=new AllCustomerFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conterner,selectedFragment).commit();
            return true;
        }
    };
}
