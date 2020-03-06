package com.example.a2zbilling.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.a2zbilling.Expenses2BottomSheetDialog;
import com.example.a2zbilling.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CustomerPaymentHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_payment_history);
        FloatingActionButton floatingActionButton=findViewById(R.id.bt_float2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Expenses2BottomSheetDialog expensesBottomSheetDialog=new Expenses2BottomSheetDialog(expenses.getExpenseCategory(),expenses.getExpenseId(),expensesActivity2ViewModel);
//                expensesBottomSheetDialog.show(getSupportFragmentManager(),"Expenses Buttom Sheet");
                CustomerPaymentBottomSheetDialog customerPaymentBottomSheetDialog=new CustomerPaymentBottomSheetDialog();
                customerPaymentBottomSheetDialog.show(getSupportFragmentManager(),"customer Payment");
            }
        });
    }
}
