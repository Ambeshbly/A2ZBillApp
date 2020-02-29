package com.example.a2zbilling;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.a2zbilling.db.entities.Expenses;
import com.example.a2zbilling.db.entities.ExpensesCategory;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class Expenses2BottomSheetDialog extends BottomSheetDialogFragment {

    private EditText editTextTotal,editTextDescription;
    private Spinner spinner;
    private String paymentMode,expensescategory;
    private TextView textViewExpensesCategory;
    private int expenseId;
    private ExpensesActivity2ViewModel expensesActivity2ViewModel;

    public Expenses2BottomSheetDialog(String expensescategory ,int expenseId, ExpensesActivity2ViewModel expensesActivity2ViewModel) {
        this.expensescategory = expensescategory;
        this.expenseId=expenseId;
        this.expensesActivity2ViewModel=expensesActivity2ViewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.expenses2_botton_sheet_dialog, container, false);
        editTextTotal=v.findViewById(R.id.edit_expenses_total);
        editTextDescription=v.findViewById(R.id.expenses_description);
        textViewExpensesCategory=v.findViewById(R.id.text_expense);
        textViewExpensesCategory.setText(expensescategory);
        CardView cardViewSaveExpenses=v.findViewById(R.id.save_expenses);
        spinner=v.findViewById(R.id.payment_spinner);
        List<String> list = new ArrayList<String>();
        list.add("Cash");
        list.add("chaque");
        list.add("paytm");
        list.add("google pay");
        list.add("debt");
        list.add("other");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        cardViewSaveExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String total=editTextTotal.getText().toString();
                String description=editTextDescription.getText().toString();
                ExpensesCategory expensesCategory=new ExpensesCategory(expenseId,total,paymentMode,description,expensescategory);
                expensesActivity2ViewModel.insertExpensesCategory(expensesCategory);
                Toast.makeText(getContext(),"sucessful",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        return v;
    }
}
