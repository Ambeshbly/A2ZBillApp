package com.example.a2zbilling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.a2zbilling.databinding.ActivityExpenses2Binding;
import com.example.a2zbilling.db.entities.Expenses;
import com.example.a2zbilling.db.entities.ExpensesCategory;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class ExpensesActivity2 extends AppCompatActivity {
    private ExpensesActivity2ViewModel expensesActivity2ViewModel;
    private ActivityExpenses2Binding activityExpenses2Binding;
    Expenses2Adapter adepter;
    Toolbar toolbar;
    private Expenses expenses;
    private  int total=0;

    private CloudExpenses2Adapter cloudExpenses2Adapter;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //get userCurrent id
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String userId=currentUser.getUid();
    private CollectionReference usersRef=db.collection("users").document(userId).collection("expeses category");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses2);
        activityExpenses2Binding= DataBindingUtil.setContentView(this,R.layout.activity_expenses2);
        expensesActivity2ViewModel = ViewModelProviders.of(this).get(ExpensesActivity2ViewModel.class);
        setSupportActionBar(toolbar);
        OnClickListener listener=new OnClickListener();
        activityExpenses2Binding.setClickListener(listener);

 /*       activityExpenses2Binding.recyclerViewExpenses1.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        activityExpenses2Binding.recyclerViewExpenses1.setHasFixedSize(true);
        adepter = new Expenses2Adapter(this,expensesActivity2ViewModel);
        activityExpenses2Binding.recyclerViewExpenses1.setAdapter(adepter);*/

        Intent intent = getIntent();
        expenses = (Expenses) intent.getSerializableExtra("expenses");
        activityExpenses2Binding.setExpenses(expenses);
        activityExpenses2Binding.toolbar.titleBar.setText(expenses.getExpenseCategory());



        Query query=usersRef.whereEqualTo("expenseId",expenses.getExpenseId());
        FirestoreRecyclerOptions<ExpensesCategory> options=new FirestoreRecyclerOptions.Builder<ExpensesCategory>().setQuery(query,ExpensesCategory.class).build();
        activityExpenses2Binding.recyclerViewExpenses1.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        activityExpenses2Binding.recyclerViewExpenses1.setHasFixedSize(true);
        cloudExpenses2Adapter=new CloudExpenses2Adapter(options,this,expensesActivity2ViewModel);
        activityExpenses2Binding.recyclerViewExpenses1.setAdapter(cloudExpenses2Adapter);




       /* expensesActivity2ViewModel.getAllExpensesCategory(expenses.getExpenseId()).observe(this, new Observer<List<ExpensesCategory>>() {
            @Override
            public void onChanged(List<ExpensesCategory> expensesCategories) {
                adepter.setExpensesCategories(expensesCategories);
                for (int i = 0; i < expensesCategories.size(); i++) {
                    ExpensesCategory expensesCategory = expensesCategories.get(i);
                    total=total+Integer.parseInt(expensesCategory.getExpenseCategoryTotal());
                }
                activityExpenses2Binding.toolbar.toolbarTotal.setText( ""+Integer.toString(Integer.parseInt(expenses.getExpenseTotal()) + total)+"\u20B9");
            }
        });*/
    }
    public class OnClickListener{
        public void openExpensesDetail(View view){
            ShowExpensesDialogFragment dialogFragementforunit=new ShowExpensesDialogFragment(expenses);
            dialogFragementforunit.show(getSupportFragmentManager(),"exampledialog");
        }
        public void openBottomSheetDialog(View view){
            Expenses2BottomSheetDialog expensesBottomSheetDialog=new Expenses2BottomSheetDialog(expenses.getExpenseCategory(),expenses.getExpenseId(),expensesActivity2ViewModel);
            expensesBottomSheetDialog.show(getSupportFragmentManager(),"Expenses Buttom Sheet");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        cloudExpenses2Adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cloudExpenses2Adapter.stopListening();
    }
}
