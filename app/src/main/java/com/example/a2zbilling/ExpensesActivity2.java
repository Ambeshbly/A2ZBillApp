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
import java.util.List;

public class ExpensesActivity2 extends AppCompatActivity {
    private ExpensesActivity2ViewModel expensesActivity2ViewModel;
    private ActivityExpenses2Binding activityExpenses2Binding;
    Expenses2Adapter adepter;
    Toolbar toolbar;
    private Expenses expenses;
    private  int total=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses2);
        activityExpenses2Binding= DataBindingUtil.setContentView(this,R.layout.activity_expenses2);
        expensesActivity2ViewModel = ViewModelProviders.of(this).get(ExpensesActivity2ViewModel.class);
        setSupportActionBar(toolbar);
        OnClickListener listener=new OnClickListener();
        activityExpenses2Binding.setClickListener(listener);
        activityExpenses2Binding.recyclerViewExpenses1.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        activityExpenses2Binding.recyclerViewExpenses1.setHasFixedSize(true);
        adepter = new Expenses2Adapter(this,expensesActivity2ViewModel);
        activityExpenses2Binding.recyclerViewExpenses1.setAdapter(adepter);
        Intent intent = getIntent();
        expenses = (Expenses) intent.getSerializableExtra("expenses");
        activityExpenses2Binding.setExpenses(expenses);
        activityExpenses2Binding.toolbar.titleBar.setText(expenses.getExpenseCategory());

        expensesActivity2ViewModel.getAllExpensesCategory(expenses.getExpenseId()).observe(this, new Observer<List<ExpensesCategory>>() {
            @Override
            public void onChanged(List<ExpensesCategory> expensesCategories) {
                adepter.setExpensesCategories(expensesCategories);
                for (int i = 0; i < expensesCategories.size(); i++) {
                    ExpensesCategory expensesCategory = expensesCategories.get(i);
                    total=total+Integer.parseInt(expensesCategory.getExpenseCategoryTotal());
                }
                activityExpenses2Binding.toolbar.toolbarTotal.setText( ""+Integer.toString(Integer.parseInt(expenses.getExpenseTotal()) + total)+"\u20B9");
            }
        });
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
}
