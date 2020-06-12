package com.example.a2zbilling.counter.SuspendedBills;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.IntentCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.MainActivity;
import com.example.a2zbilling.R;
import com.example.a2zbilling.counter.CounterFragment;
import com.example.a2zbilling.counter.DialogFragmentForUpdateCounterStock;
import com.example.a2zbilling.counter.Selling.CloudAddToCartAdapter;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.db.entities.Suspend;
import com.example.a2zbilling.db.entities.SuspendDetail;
import com.example.a2zbilling.stock.AvailableStock.CloudAvailableStockAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class SuspendedTransactionListActivity extends AppCompatActivity implements Serializable     {



    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //get userCurrent id
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String userId=currentUser.getUid();
    private CollectionReference usersRef=db.collection("users").document(userId).collection("suspend");

    private RecyclerView waitList;
    private CloudSuspendListAdapter cloudSuspendListAdapter;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
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







        //---------------------------delete the item from the counter by swiped---------------------------

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
              //  ArrayList<Stock> stockList = mainActivityViewModel.getNewlyAddedStocks().getValue();

                if(direction==ItemTouchHelper.LEFT)//swiped to left
                {
                    Toast.makeText(getBaseContext(),"remove",Toast.LENGTH_SHORT).show();
                }
                if(direction==ItemTouchHelper.RIGHT)//swiped to right
                {



                   /*--------------making list of the suspend Details which name is transfer Deatils-------*/

                   Suspend suspend=cloudSuspendListAdapter.getItem(viewHolder.getAdapterPosition());
                   final int suspendId=suspend.getSuspendId();
                   final List<SuspendDetail> suspendTransfer = new ArrayList<>();
                    CollectionReference suspendDeteailRef= db.collection("users").document(userId).collection("suspend detail");
                    suspendDeteailRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            final List<SuspendDetail> suspendDetailList = queryDocumentSnapshots.toObjects(SuspendDetail.class);
                            for (int i = 0; i <suspendDetailList.size() ; i++) {
                                SuspendDetail suspendDetail=suspendDetailList.get(i);
                                if(suspendDetail.getSuspendId()==suspendId){
                                    suspendTransfer.add(suspendDetail);
                                    Toast.makeText(getBaseContext(),"fetch",Toast.LENGTH_SHORT).show();
                                }
                            }



                            /*-----------get teh availabe stock list ---------------*/
                            CollectionReference stockRef= db.collection("users").document(userId).collection("stocks");
                            stockRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    List<Stock> availableStocksList = queryDocumentSnapshots.toObjects(Stock.class);
                                    final List<Stock> stocksList = new ArrayList<>();
                                    final List<Stock> soldStocksList = new ArrayList<>();
                                    for (int i = 0; i <suspendTransfer.size() ; i++) {
                                        for (int j = 0; j <availableStocksList.size() ; j++) {
                                            if(suspendTransfer.get(i).getItemId()==availableStocksList.get(j).getId()){

                                                Stock stock=new Stock(availableStocksList.get(j));
                                                stock.setPrimaryQuant(suspendTransfer.get(i).getQnty());
                                                stock.setPriamryUnit(suspendTransfer.get(i).getUnit());
                                                stock.setSalePerUnit(suspendTransfer.get(i).getSalingPrice());
                                                stock.setSaleTotal(Double.toString(suspendTransfer.get(i).getQnty()*Double.parseDouble(suspendTransfer.get(i).getSalingPrice())));
                                                stocksList.add(stock);


                                                Stock soldStock=new Stock(availableStocksList.get(j));
                                                soldStock.setPrimaryQuant(soldStock.getPrimaryQuant()-suspendTransfer.get(i).getQnty());
                                                soldStocksList.add(soldStock);

                                            }

                                        }

                                    }

                                    Intent intent=new Intent(getBaseContext(), MainActivity.class);
                                    intent.putExtra("LIST", (Serializable) stocksList);
                                    intent.putExtra("OLDLIST", (Serializable) soldStocksList);
                                    Toast.makeText(getBaseContext(),"transfer to the counter fragment" ,Toast.LENGTH_SHORT).show();
                                    startActivity(intent);



                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getBaseContext(),"available stock is not fetch",Toast.LENGTH_SHORT).show();
                                }
                            });



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getBaseContext(),""+e,Toast.LENGTH_SHORT).show();
                        }
                    });
                    Toast.makeText(getBaseContext(),"add the couneter",Toast.LENGTH_SHORT).show();
                }

            }








            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                if(dX>0)//swiped to right
                {
                    new RecyclerViewSwipeDecorator.Builder(getBaseContext(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            .addBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.limeGreen))
                            .addActionIcon(R.drawable.ic_update)
                            .addSwipeRightLabel("update")
                            .create()
                            .decorate();
                }
                if(dX<0)//swiped to left
                {
                    new RecyclerViewSwipeDecorator.Builder(getBaseContext(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            .addBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.red))
                            .addActionIcon(R.drawable.ic_delete)
                            .addSwipeLeftLabel("remove")
                            .create()
                            .decorate();

                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(waitList);




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
