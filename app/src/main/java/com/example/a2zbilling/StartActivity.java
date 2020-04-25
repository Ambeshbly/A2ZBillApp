package com.example.a2zbilling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button alreadyHaveAccount,createANewAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        alreadyHaveAccount=findViewById(R.id.already_have_account);
        createANewAccount=findViewById(R.id.create_a_new_account);

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StartActivity.this,LogInActivity.class);
                startActivity(intent);
            }
        });

        createANewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StartActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }
}
