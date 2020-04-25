package com.example.a2zbilling;


import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.a2zbilling.db.entities.Stock;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CloudRepository {

    //get database refrence of fireStore Database
    private FirebaseFirestore db= FirebaseFirestore.getInstance();

    //get userCurrent id
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String userId=currentUser.getUid();

    private Context context;

    //empty custrouctor
    public CloudRepository(Context context) {
        this.context=context;
    }

    //function for adding stock in FireStore
    public void insertStock(Stock stock){

        //get user Referance from fireStore database
        DocumentReference userRef= db.collection("users").document(userId).collection("stocks").document(stock.getName());

        userRef.set(stock).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"sucessfullty added",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"failer",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
