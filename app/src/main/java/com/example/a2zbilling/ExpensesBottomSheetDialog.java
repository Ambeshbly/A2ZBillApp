package com.example.a2zbilling;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import com.example.a2zbilling.databinding.ExpensesBottonSheetDialogBinding;
import com.example.a2zbilling.db.entities.Expenses;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExpensesBottomSheetDialog extends BottomSheetDialogFragment {

    private ExpensesActivityViewModel expensesActivityViewModel;
    private String paymentMode;
    private ExpensesBottonSheetDialogBinding expensesBottonSheetDialogBinding;
    private int maxExpenses;

    //just for test
    CollectionReference expensesRef;
    //get database refrence of fireStore Database
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //get userCurrent id
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String userId=currentUser.getUid();

    public ExpensesBottomSheetDialog(ExpensesActivityViewModel expensesActivityViewModel) {
        this.expensesActivityViewModel = expensesActivityViewModel;

        expensesRef= db.collection("users").document(userId).collection("expeses");
        expensesRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                maxExpenses = queryDocumentSnapshots.getDocuments().size();
            }
        });
    }
    public ExpensesBottomSheetDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        expensesBottonSheetDialogBinding = DataBindingUtil.inflate(inflater, R.layout.expenses_botton_sheet_dialog, container, false);
        OnClickListener listener=new OnClickListener();
        expensesBottonSheetDialogBinding.setClickListener(listener);
        expensesBottonSheetDialogBinding.textInput.setVisibility(View.INVISIBLE);
        final Expenses expenses=new Expenses();
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

        List<String> list1 = new ArrayList<String>();
        list1.add("             select");
        list1.add("Salary");
        list1.add("electricity bill");
        list1.add("maintenance");
        list1.add("furniture");
        list1.add("instrument");
        list1.add("other");
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expensesBottonSheetDialogBinding.paymentSpinner3.setAdapter(arrayAdapter1);
        expensesBottonSheetDialogBinding.paymentSpinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        break;
                    case 1:
                        expensesBottonSheetDialogBinding.expensesCategoryText.setVisibility(View.INVISIBLE);
                        expensesBottonSheetDialogBinding.paymentSpinner3.setVisibility(View.INVISIBLE);
                        expensesBottonSheetDialogBinding.textInput.setVisibility(View.VISIBLE);
                        expensesBottonSheetDialogBinding.getExpenses().setExpenseCategory("Salary");
                        break;
                    case 2:
                        expensesBottonSheetDialogBinding.expensesCategoryText.setVisibility(View.INVISIBLE);
                        expensesBottonSheetDialogBinding.paymentSpinner3.setVisibility(View.INVISIBLE);
                        expensesBottonSheetDialogBinding.textInput.setVisibility(View.VISIBLE);
                        expensesBottonSheetDialogBinding.getExpenses().setExpenseCategory("electricity bill");
                        break;
                    case 3:
                        expensesBottonSheetDialogBinding.expensesCategoryText.setVisibility(View.INVISIBLE);
                        expensesBottonSheetDialogBinding.paymentSpinner3.setVisibility(View.INVISIBLE);
                        expensesBottonSheetDialogBinding.textInput.setVisibility(View.VISIBLE);
                        expensesBottonSheetDialogBinding.getExpenses().setExpenseCategory("maintenance");
                        break;
                    case 4:
                        expensesBottonSheetDialogBinding.expensesCategoryText.setVisibility(View.INVISIBLE);
                        expensesBottonSheetDialogBinding.paymentSpinner3.setVisibility(View.INVISIBLE);
                        expensesBottonSheetDialogBinding.textInput.setVisibility(View.VISIBLE);
                        expensesBottonSheetDialogBinding.getExpenses().setExpenseCategory("furniture");
                        break;
                    case 5:
                        expensesBottonSheetDialogBinding.expensesCategoryText.setVisibility(View.INVISIBLE);
                        expensesBottonSheetDialogBinding.paymentSpinner3.setVisibility(View.INVISIBLE);
                        expensesBottonSheetDialogBinding.textInput.setVisibility(View.VISIBLE);
                        expensesBottonSheetDialogBinding.getExpenses().setExpenseCategory("instrument");
                        break;
                    case 6:
                        expensesBottonSheetDialogBinding.expensesCategoryText.setVisibility(View.INVISIBLE);
                        expensesBottonSheetDialogBinding.paymentSpinner3.setVisibility(View.INVISIBLE);
                        expensesBottonSheetDialogBinding.textInput.setVisibility(View.VISIBLE);
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
            expenses1.setExpenseId(maxExpenses+1);
           // expensesActivityViewModel.insertExpenses(expenses1);
            expensesActivityViewModel.cloudInsertExpenes(expenses1);
            dismiss();
        }
    }
}
