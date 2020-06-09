package com.example.a2zbilling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.a2zbilling.counter.BillList.BillHistoryActivity;
import com.example.a2zbilling.counter.BillList.CloudBillHistoryAdapter;
import com.example.a2zbilling.databinding.ActivityExpensesBinding;
import com.example.a2zbilling.db.entities.Expenses;
import com.example.a2zbilling.db.entities.Sales;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class ExpensesActivity extends AppCompatActivity {
    private ExpensesActivityViewModel expensesActivityViewModel;
    ExpensesAdapter adepter;
    private ActivityExpensesBinding activityExpensesBinding;


    private CloudExpensesAdapter cloudExpensesAdapter;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //get userCurrent id
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String userId=currentUser.getUid();
    private CollectionReference usersRef=db.collection("users").document(userId).collection("expeses");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        setTitle("Expenses");
        activityExpensesBinding= DataBindingUtil.setContentView(this,R.layout.activity_expenses);
        OnClickListener listener=new OnClickListener();
        activityExpensesBinding.setClickListener(listener);

       /* activityExpensesBinding.recyclerViewExpenses.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        activityExpensesBinding.recyclerViewExpenses.setHasFixedSize(true);
        adepter = new ExpensesAdapter();
        activityExpensesBinding.recyclerViewExpenses.setAdapter(adepter);
        */
        expensesActivityViewModel = ViewModelProviders.of(this).get(ExpensesActivityViewModel.class);
       /* expensesActivityViewModel.getAllExpenses().observe(this, new Observer<List<Expenses>>() {
            @Override
            public void onChanged(List<Expenses> expenses) {
                adepter.setExpenses(expenses);
            }
        });*/
       /* adepter.setOnItemRecyclerViewlistener(new ExpensesAdapter.OnItemRecyclerViewListener() {
            @Override
            public void onItemClick(Expenses expenses) {
                Intent intent = new Intent(getBaseContext(), ExpensesActivity2.class);
                intent.putExtra("expenses",  expenses);
                startActivity(intent);
            }
        });*/


        //just for test
        Query query=usersRef.orderBy("expenseId",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Expenses> options=new FirestoreRecyclerOptions.Builder<Expenses>().setQuery(query,Expenses.class).build();
        activityExpensesBinding.recyclerViewExpenses.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        activityExpensesBinding.recyclerViewExpenses.setHasFixedSize(true);
        cloudExpensesAdapter=new CloudExpensesAdapter(options,this);
        activityExpensesBinding.recyclerViewExpenses.setAdapter(cloudExpensesAdapter);


    }
    public class OnClickListener{
        public void openExpensesBottomSheetDialog(View view){
            ExpensesBottomSheetDialog expensesBottomSheetDialog=new ExpensesBottomSheetDialog(expensesActivityViewModel);
            expensesBottomSheetDialog.show(getSupportFragmentManager(),"Expenses Buttom Sheet");

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        cloudExpensesAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cloudExpensesAdapter.stopListening();
    }
}
