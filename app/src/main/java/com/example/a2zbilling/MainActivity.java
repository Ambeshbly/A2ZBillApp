package com.example.a2zbilling;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.a2zbilling.counter.CounterFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    //bottom navigation declaration
    private BottomNavigationView bottomNavigationView;
    private MainActivityViewModel addToCartActivityViewModel;
    //bottom navigation action by switch
    private BottomNavigationView.OnNavigationItemSelectedListener nav_listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = new YouFragment();
            switch (menuItem.getItemId()) {
                case R.id.nav_counter:

                    mediaPlayer.start();
                    selectedFragment = new CounterFragment(addToCartActivityViewModel);
                    break;
                case R.id.nav_deshboad:

                    mediaPlayer.start();
                    selectedFragment = new Dashboard();
                    break;
                case R.id.nav_me:
                    mediaPlayer.start();
                    selectedFragment = new YouFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conterner, selectedFragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bottom navigation finding in XML file
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.simple);

        //bottom navigation selection listener
        bottomNavigationView.setOnNavigationItemSelectedListener(nav_listener);

        addToCartActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);



        //which fragment is show whenever app is open
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conterner, new CounterFragment(addToCartActivityViewModel)).commit();

    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("A2ZBill").setMessage("Are you want to exit?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        }).setNegativeButton("No", null).show();
    }

}
