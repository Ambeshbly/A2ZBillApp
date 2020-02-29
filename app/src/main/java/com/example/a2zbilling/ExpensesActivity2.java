package com.example.a2zbilling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.example.a2zbilling.db.entities.Expenses;
import com.example.a2zbilling.db.entities.ExpensesCategory;
import com.example.a2zbilling.db.entities.Stock;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ExpensesActivity2 extends AppCompatActivity {
    private ExpensesActivity2ViewModel expensesActivity2ViewModel;
    RecyclerView recyclerView;
    Expenses2Adapter adepter;
    Toolbar toolbar;
    private TextView textViewtitle,textViewToolbarTotal;
    private  int total=0;
    private TextView textViewTotal,textViewPaymentMode;
    private CardView cardViewEye;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses2);
        textViewTotal=findViewById(R.id.text_expenses1_total);
        textViewPaymentMode=findViewById(R.id.text_expeses1_payment_mode);
        toolbar=findViewById(R.id.myToolbar);
        textViewToolbarTotal=findViewById(R.id.toolbar_total);
        cardViewEye=findViewById(R.id.cardView_eye);
        setSupportActionBar(toolbar);
        textViewtitle=findViewById(R.id.title_bar);
        FloatingActionButton floatingActionButton=findViewById(R.id.bt_float1);

        expensesActivity2ViewModel = ViewModelProviders.of(this).get(ExpensesActivity2ViewModel.class);

        recyclerView = findViewById(R.id.recycler_viewExpenses1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setHasFixedSize(true);

        adepter = new Expenses2Adapter(this,expensesActivity2ViewModel);
        recyclerView.setAdapter(adepter);


        Intent intent = getIntent();
        final Expenses expenses = (Expenses) intent.getSerializableExtra("expenses");
        textViewTotal.setText(expenses.getExpenseTotal()+"\u20B9");
        textViewPaymentMode.setText("payment mode : "+expenses.getPaymentMode());
        textViewtitle.setText(expenses.getExpenseCategory());

        expensesActivity2ViewModel.getAllExpensesCategory(expenses.getExpenseId()).observe(this, new Observer<List<ExpensesCategory>>() {
            @Override
            public void onChanged(List<ExpensesCategory> expensesCategories) {
                adepter.setExpensesCategories(expensesCategories);
                for (int i = 0; i < expensesCategories.size(); i++) {
                    ExpensesCategory expensesCategory = expensesCategories.get(i);
                    total=total+Integer.parseInt(expensesCategory.getExpenseCategoryTotal());
                }
                textViewToolbarTotal.setText("Total Expenses : "+Integer.toString(Integer.parseInt(expenses.getExpenseTotal()) + total)+"\u20B9");
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expenses2BottomSheetDialog expensesBottomSheetDialog=new Expenses2BottomSheetDialog(expenses.getExpenseCategory(),expenses.getExpenseId(),expensesActivity2ViewModel);
                expensesBottomSheetDialog.show(getSupportFragmentManager(),"Expenses Buttom Sheet");
            }
        });


        cardViewEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowExpensesDialogFragment dialogFragementforunit=new ShowExpensesDialogFragment(expenses);
                dialogFragementforunit.show(getSupportFragmentManager(),"exampledialog");
            }
        });
    }
}
