package com.example.a2zbilling;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.example.a2zbilling.databinding.Expenses2BottonSheetDialogBinding;
import com.example.a2zbilling.db.entities.ExpensesCategory;
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

public class Expenses2BottomSheetDialog extends BottomSheetDialogFragment {

    private String paymentMode,expensescategory;
    private int expenseId;
    private int maxExpesesCategory=0;
    private ExpensesActivity2ViewModel expensesActivity2ViewModel;
    private Expenses2BottonSheetDialogBinding expenses2BottonSheetDialogBinding;

    //just for test
    CollectionReference expensesCategoryRef;
    //get database refrence of fireStore Database
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //get userCurrent id
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String userId=currentUser.getUid();

    public Expenses2BottomSheetDialog(String expensescategory ,int expenseId, ExpensesActivity2ViewModel expensesActivity2ViewModel) {
        this.expensescategory = expensescategory;
        this.expenseId=expenseId;
        this.expensesActivity2ViewModel=expensesActivity2ViewModel;

        expensesCategoryRef= db.collection("users").document(userId).collection("expeses category");
        expensesCategoryRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                maxExpesesCategory = queryDocumentSnapshots.getDocuments().size();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        expenses2BottonSheetDialogBinding = DataBindingUtil.inflate(inflater, R.layout.expenses2_botton_sheet_dialog, container, false);
       OnClickListener listener=new OnClickListener();
       expenses2BottonSheetDialogBinding.setClickListener(listener);
       ExpensesCategory expensesCategory=new ExpensesCategory();
       expenses2BottonSheetDialogBinding.setExpensesCategory(expensesCategory);
       expenses2BottonSheetDialogBinding.getExpensesCategory().setExpenseName(expensescategory);
       List<String> list = new ArrayList<String>();
        list.add("Cash");
        list.add("chaque");
        list.add("paytm");
        list.add("google pay");
        list.add("debt");
        list.add("other");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenses2BottonSheetDialogBinding.paymentSpinner.setAdapter(arrayAdapter);
        expenses2BottonSheetDialogBinding.paymentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        return expenses2BottonSheetDialogBinding.getRoot();
    }

    public class OnClickListener{
        public void insertExpensesCategory(View view){
            Calendar calendar=Calendar.getInstance();
            String selecteddate= DateFormat.getDateInstance().format(calendar.getTime());
            expenses2BottonSheetDialogBinding.getExpensesCategory().setDate(selecteddate);
            expenses2BottonSheetDialogBinding.getExpensesCategory().setExpenseCategoryPaymentMode(paymentMode);
            expenses2BottonSheetDialogBinding.getExpensesCategory().setExpenseId(expenseId);
          //  expensesActivity2ViewModel.insertExpensesCategory(expenses2BottonSheetDialogBinding.getExpensesCategory());
            expenses2BottonSheetDialogBinding.getExpensesCategory().setExpenseCategoryid(maxExpesesCategory+1);
            expensesActivity2ViewModel.cloudInsertExpenseCategory(expenses2BottonSheetDialogBinding.getExpensesCategory());
            dismiss();
        }
    }
}
