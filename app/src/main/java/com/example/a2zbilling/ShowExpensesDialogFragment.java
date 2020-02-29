package com.example.a2zbilling;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.a2zbilling.R;
import com.example.a2zbilling.customer.CustomerActivityViewModel;
import com.example.a2zbilling.db.entities.Expenses;
import com.example.a2zbilling.db.entities.ExpensesCategory;

public class ShowExpensesDialogFragment extends AppCompatDialogFragment {


    private ExpensesActivity2ViewModel expensesActivity2ViewModel;
    private TextView textViewPrice,textViewPaymentMode,textViewDescription,textViewExpenseCategory;
    private int cheak;
    private Expenses expenses;

    public ShowExpensesDialogFragment(ExpensesActivity2ViewModel expensesActivity2ViewModel,int cheak) {
        this.expensesActivity2ViewModel = expensesActivity2ViewModel;
        this.cheak=cheak;
    }

    public ShowExpensesDialogFragment(Expenses  expenses) {
        this.expenses=expenses;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.show_expenses_dialog_fragment, null);
        textViewPrice=view.findViewById(R.id.show_expenses_expenses_price);
        textViewPaymentMode=view.findViewById(R.id.show_expenses_expenses_payemnt_mode);
        textViewDescription=view.findViewById(R.id.show_expenses_expenses_description);
        textViewExpenseCategory=view.findViewById(R.id.show_expenses_expenses_category);
        if(cheak==1){
            ExpensesCategory expensesCategory=expensesActivity2ViewModel.getExpensesCategory();
            textViewPrice.setText(expensesCategory.getExpenseCategoryTotal());
            textViewPaymentMode.setText(expensesCategory.getExpenseCategoryPaymentMode());
            textViewDescription.setText(expensesCategory.getExpenseCategoryDescription());
            textViewExpenseCategory.setText(expensesCategory.getExpenseName());
        }else{
            textViewPrice.setText(expenses.getExpenseTotal());
            textViewPaymentMode.setText(expenses.getPaymentMode());
            textViewDescription.setText(expenses.getDescription());
            textViewExpenseCategory.setText(expenses.getExpenseCategory());
        }
        builder.setView(view);
        return builder.create();
    }
}
