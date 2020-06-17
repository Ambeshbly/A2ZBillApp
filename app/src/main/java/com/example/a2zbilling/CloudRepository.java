package com.example.a2zbilling;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.Expenses;
import com.example.a2zbilling.db.entities.ExpensesCategory;
import com.example.a2zbilling.db.entities.Purchase;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.db.entities.Suspend;
import com.example.a2zbilling.db.entities.SuspendDetail;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class CloudRepository {

    private int maxId=0,maxCustomer=0;

    //get database refrence of fireStore Database
    private FirebaseFirestore db= FirebaseFirestore.getInstance();

    CollectionReference stockRef;

    private Context context;

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public String getUserId() {
        return userId;
    }

    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String   userId = currentUser.getUid();

    //just for test
    CollectionReference customerRef;


    //empty custrouctor
    public CloudRepository(Context context) {
        this.context=context;
        //get userCurrent id



            stockRef = db.collection("users").document(userId).collection("stocks");
            stockRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    maxId = queryDocumentSnapshots.getDocuments().size();
                }
            });

            customerRef = db.collection("users").document(userId).collection("customers");
            customerRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    maxCustomer = queryDocumentSnapshots.getDocuments().size();
                }
            });


    }

    //function for adding stock in FireStore
    public void insertStock(Stock stock){

        stock.setId(maxId+1);
        //get user Referance from fireStore database
        DocumentReference stockDoc = stockRef.document(Integer.toString(maxId+1));

        stockDoc.set(stock).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public void updateStock(Stock stock,String docement){
        //get user Referance from fireStore database
        DocumentReference stockRef= db.collection("users").document(userId).collection("stocks").document(docement);
        stockRef.set(stock).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"update sucessfull",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"fail",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void insertPurchase(Purchase purchase){
        //get user Referance from fireStore database
        DocumentReference purchaseRef= db.collection("users").document(userId).collection("purchase").document();
        purchaseRef.set(purchase).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"purchase sucessfull",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"fail",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cloudInsertCustomer(Customer customer){

        //get user Referance from fireStore database
        DocumentReference customerRef= db.collection("users").document(userId).collection("customers").document(Integer.toString(maxCustomer+1));
        customerRef.set(customer).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"customer sucessfull",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"fail",Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void cloudUpdateCustomer(Customer customer){

        //get user Referance from fireStore database
        DocumentReference customerRef= db.collection("users").document(userId).collection("customers").document(Integer.toString(customer.getCustId()));
        customerRef.set(customer).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"customer update sucessfull",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"fail",Toast.LENGTH_SHORT).show();
            }
        });

    }



    public  void cloudInsertSale(Sales sales){
        //get user Referance from fireStore database
        DocumentReference saleRef= db.collection("users").document(userId).collection("sales").document();
        saleRef.set(sales).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"sales sucessfull",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"fail",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cloudInsertSaleDetail(SaleDeatial saleDeatial){
        //get user Referance from fireStore database
        DocumentReference saleDetailRef= db.collection("users").document(userId).collection("sale Detail").document();
        saleDetailRef.set(saleDeatial).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"sales Detail sucessfull",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"fail",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void cloudInsertExpenses(Expenses expenses){
        //get user Referance from fireStore database
        DocumentReference saleDetailRef= db.collection("users").document(userId).collection("expeses").document();
        saleDetailRef.set(expenses).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"expeses insert sucessfull",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"fail",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void  cloudExpesesCategory(ExpensesCategory expensesCategory){
        //get user Referance from fireStore database
        DocumentReference saleDetailRef= db.collection("users").document(userId).collection("expeses category").document();
        saleDetailRef.set(expensesCategory).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"expeses category insert sucessfull",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"fail",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addSuspendStock(Suspend suspend){
        //get user Referance from fireStore database
        DocumentReference saleDetailRef= db.collection("users").document(userId).collection("suspend").document();
        saleDetailRef.set(suspend).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"suspend insert sucessfull",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"fail",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void addSuspendDetail(SuspendDetail suspendDetail){
        //get user Referance from fireStore database
        DocumentReference saleDetailRef= db.collection("users").document(userId).collection("suspend detail").document();
        saleDetailRef.set(suspendDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"suspend insert sucessfull",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"fail",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateCustomerDebt(String debt,Customer customer){
        //get user Referance from fireStore database
        DocumentReference customerRef= db.collection("users").document(userId).collection("customers").document(Integer.toString(customer.getCustId()));
        customerRef.update("debt",debt).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"customer debt update sussesfully",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
