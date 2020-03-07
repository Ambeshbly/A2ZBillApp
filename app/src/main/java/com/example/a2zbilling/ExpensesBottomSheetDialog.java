package com.example.a2zbilling;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import com.example.a2zbilling.databinding.ExpensesBottonSheetDialogBinding;
import com.example.a2zbilling.db.entities.Expenses;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExpensesBottomSheetDialog extends BottomSheetDialogFragment {

    private ExpensesActivityViewModel expensesActivityViewModel;
    private String paymentMode;
    private ExpensesBottonSheetDialogBinding expensesBottonSheetDialogBinding;

    public ExpensesBottomSheetDialog(ExpensesActivityViewModel expensesActivityViewModel) {
        this.expensesActivityViewModel = expensesActivityViewModel;
    }
    public ExpensesBottomSheetDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        expensesBottonSheetDialogBinding = DataBindingUtil.inflate(inflater, R.layout.expenses_botton_sheet_dialog, container, false);
        OnClickListener listener=new OnClickListener();
        expensesBottonSheetDialogBinding.setClickListener(listener);
        Expenses expenses=new Expenses();
        expensesBottonSheetDialogBinding.setExpenses(expenses);
        List<String> list = new ArrayList<String>();
        list.add("Cash");
        list.add("chaque");
        list.add("paytm");
        list.add("google pay");
        list.add("debt");
        list.add("other");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expensesBottonSheetDialogBinding.paymentSpinner.setAdapter(arrayAdapter);
        expensesBottonSheetDialogBinding.paymentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        paymentMode="Cash";
                        break;
                    case 1:
                        paymentMode="Chaque";
                        break;
                    case 2:
                        paymentMode="Paytm";
                        break;
                    case 3:
                        paymentMode="Cash";
                        break;
                    case 4:
                        paymentMode="Google Pay";
                        break;
                    case 5:
                        paymentMode="other";
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return expensesBottonSheetDialogBinding.getRoot();
    }

    public class OnClickListener{
        public void insertExpenses(View view){
            Calendar calendar=Calendar.getInstance();
            String selecteddate= DateFormat.getDateInstance().format(calendar.getTime());
            expensesBottonSheetDialogBinding.getExpenses().setPaymentMode(paymentMode);
            expensesBottonSheetDialogBinding.getExpenses().setDate(selecteddate);
            Expenses expenses1=expensesBottonSheetDialogBinding.getExpenses();
            expensesActivityViewModel.insertExpenses(expenses1);
            Toast.makeText(getContext(),"expneses added",Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }
}
