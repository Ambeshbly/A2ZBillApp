package com.example.a2zbilling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2zbilling.stock.addUpdate.UnitDialogFragement;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private TextInputLayout email,password;
    private Button logIn;
    private ProgressDialog regProgress;
    private FirebaseAuth mAuth;
    private TextView forgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        email=findViewById(R.id.reg_email);
        password=findViewById(R.id.reg_password);
        logIn=findViewById(R.id.LogIn);
        forgetPassword=findViewById(R.id.forget_password);

        regProgress=new ProgressDialog(this);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !validateEmail()|| !validatePassword()){
                    return;
                }
                regProgress.setTitle("Logging User");
                regProgress.setMessage("please Wait...");
                regProgress.setCanceledOnTouchOutside(false);
                regProgress.show();
                String email1 = email.getEditText().getText().toString().trim();
                String password1 = password.getEditText().getText().toString().trim();
                logInUser(email1,password1);
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPasswordDialogFragment forgetPasswordDialogFragment = new ForgetPasswordDialogFragment();
                forgetPasswordDialogFragment.show(getSupportFragmentManager(), "exampledialog");
            }
        });
    }

    private void logInUser(String email1, String password1) {
        mAuth.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    regProgress.dismiss();
                    Intent intent=new Intent(LogInActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    regProgress.hide();
                    Toast.makeText(LogInActivity.this,"please cheak email or password !",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    //method for validation on email
    private boolean validateEmail() {
        String email1 = email.getEditText().getText().toString().trim();
        if (email1.isEmpty()) {
            email.setError("Email can't be empty");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    //method for validation on Password
    private boolean validatePassword() {
        String password1 = password.getEditText().getText().toString().trim();
        if (password1.isEmpty()) {
            password.setError("Password can't be empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }
}
