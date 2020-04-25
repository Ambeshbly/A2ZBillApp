package com.example.a2zbilling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    // take authontication from google
    private FirebaseAuth mAuth;

    //get database refrence of fireStore Database
    private FirebaseFirestore db= FirebaseFirestore.getInstance();

    //create object of textInput
    private Button createNewUserAccount;
    private TextInputLayout reg_yourName;
    private TextInputLayout reg_email;
    private TextInputLayout reg_password;

    //create progress bar bacasuse it will take same time
    private ProgressDialog regProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //get instance of authontication
        mAuth = FirebaseAuth.getInstance();

        //finding id of textinputLayout
        reg_yourName=findViewById(R.id.reg_your_name);
        reg_email=findViewById(R.id.reg_email);
        reg_password=findViewById(R.id.reg_password);
        createNewUserAccount=findViewById(R.id.createAccount);

        //initialize the dialog process bar
        regProgress=new ProgressDialog(this);

        //onclickListener on create Account Button
        createNewUserAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //cheak validation of editText
                if(!validateYourName()|| !validateEmail()|| !validatePassword()){
                    return;
                }

                //show prograssBar to user
                regProgress.setTitle("Registring User");
                regProgress.setMessage("please Wait...");
                regProgress.setCanceledOnTouchOutside(false);
                regProgress.show();

                //get Text fron editText
                String shopName=reg_yourName.getEditText().getText().toString().trim();
                String email=reg_email.getEditText().getText().toString().trim();
                String password=reg_password.getEditText().getText().toString().trim();

                //pass all the edit text value to the createAccount Function
                createAccoubt(shopName,email,password);
            }
        });

    }

    private void createAccoubt(final String yourName, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //get userCurrent id
                    FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
                    String userId=currentUser.getUid();

                    //get user Referance from fireStore database
                    DocumentReference userRef= db.collection("users").document(userId);

                    //create a temp object
                    HashMap<String,String> userMap=new HashMap<>();
                    userMap.put("name",yourName);
                    userMap.put("image","default");
                    userMap.put("thumb_image","default");

                    //set object in the fireStroe database
                    userRef.set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            regProgress.dismiss();
                            Intent intent=new Intent(RegistrationActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            regProgress.hide();
                            Toast.makeText(RegistrationActivity.this,"Somethng wrong",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    regProgress.hide();
                    Toast.makeText(RegistrationActivity.this,"Can't sign in try again leater",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //method for validation on Name
    private boolean validateYourName() {
        String yourName = reg_yourName.getEditText().getText().toString().trim();
        if (yourName.isEmpty()) {
            reg_yourName.setError("Shop Name can't be empty");
            return false;
        } else {
            reg_yourName.setError(null);
            return true;
        }
    }

    //method for validation on email
    private boolean validateEmail() {
        String email = reg_email.getEditText().getText().toString().trim();
        if (email.isEmpty()) {
            reg_email.setError("Email or Phone No. can't be empty");
            return false;
        } else {
            reg_email.setError(null);
            return true;
        }
    }

    //method for validation on Password
    private boolean validatePassword() {
        String password = reg_password.getEditText().getText().toString().trim();
        if (password.isEmpty()) {
            reg_password.setError("Password can't be empty");
            return false;
        } else {
            reg_password.setError(null);
            return true;
        }
    }
}
