package com.example.a2zbilling.counter.SuspendedBills;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.counter.Selling.CloudAddToCartAdapter;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.db.entities.Suspend;
import com.example.a2zbilling.stock.AvailableStock.CloudAvailableStockAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SuspendedTransactionListActivity extends AppCompatActivity {



    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //get userCurrent id
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String userId=currentUser.getUid();
    private CollectionReference usersRef=db.collection("users").document(userId).collection("suspend");

    private RecyclerView waitList;
    private CloudSuspendListAdapter cloudSuspendListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_list);
        getSupportActionBar().setTitle("Suspended Transactional List");
        waitList=findViewById(R.id.waitListRecyclerView);

        //just for test
        Query query=usersRef.orderBy("suspendId",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Suspend> options=new FirestoreRecyclerOptions.Builder<Suspend>().setQuery(query,Suspend.class).build();
        waitList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        waitList.setHasFixedSize(true);
        cloudSuspendListAdapter=new CloudSuspendListAdapter(options,this);
        waitList.setAdapter(cloudSuspendListAdapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        cloudSuspendListAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cloudSuspendListAdapter.startListening();
    }
}
