package com.example.a2zbilling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.a2zbilling.databinding.ActivityExpensesBinding;
import com.example.a2zbilling.db.entities.Expenses;
import java.util.List;

public class ExpensesActivity extends AppCompatActivity {
    private ExpensesActivityViewModel expensesActivityViewModel;
    ExpensesAdapter adepter;
    private ActivityExpensesBinding activityExpensesBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        setTitle("Expenses");
        activityExpensesBinding= DataBindingUtil.setContentView(this,R.layout.activity_expenses);
        OnClickListener listener=new OnClickListener();
        activityExpensesBinding.setClickListener(listener);
        activityExpensesBinding.recyclerViewExpenses.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        activityExpensesBinding.recyclerViewExpenses.setHasFixedSize(true);
        adepter = new ExpensesAdapter();
        activityExpensesBinding.recyclerViewExpenses.setAdapter(adepter);
        expensesActivityViewModel = ViewModelProviders.of(this).get(ExpensesActivityViewModel.class);
        expensesActivityViewModel.getAllExpenses().observe(this, new Observer<List<Expenses>>() {
            @Override
            public void onChanged(List<Expenses> expenses) {
                adepter.setExpenses(expenses);
            }
        });
        adepter.setOnItemRecyclerViewlistener(new ExpensesAdapter.OnItemRecyclerViewListener() {
            @Override
            public void onItemClick(Expenses expenses) {
                Intent intent = new Intent(getBaseContext(), ExpensesActivity2.class);
                intent.putExtra("expenses",  expenses);
                startActivity(intent);
            }
        });
    }
    public class OnClickListener{
        public void openExpensesBottomSheetDialog(View view){
            ExpensesBottomSheetDialog expensesBottomSheetDialog=new ExpensesBottomSheetDialog(expensesActivityViewModel);
            expensesBottomSheetDialog.show(getSupportFragmentManager(),"Expenses Buttom Sheet");

        }
    }

}
