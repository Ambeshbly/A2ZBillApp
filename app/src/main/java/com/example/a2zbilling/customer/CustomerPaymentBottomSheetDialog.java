package com.example.a2zbilling.customer;

import android.content.Intent;
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

import com.example.a2zbilling.DateConverter;
import com.example.a2zbilling.R;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.ExpensesCategory;
import com.example.a2zbilling.db.entities.Payment;
import com.example.a2zbilling.db.entities.Sales;
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

public class CustomerPaymentBottomSheetDialog extends BottomSheetDialogFragment {

    private ShowCustomerTransactionDetailActivityViewModel showCustomerTransactionDetailActivityViewModel;
    private EditText editTotal,editDescription,editPaymentMode;
    private Spinner spinner;
    private String paymentMode;
    private Customer customer;
    private int maxId=0;


    //just for test
    CollectionReference saleRef,saleDetailRef,customerRef;
    //get database refrence of fireStore Database
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //get userCurrent id
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String userId=currentUser.getUid();

    public CustomerPaymentBottomSheetDialog(ShowCustomerTransactionDetailActivityViewModel showCustomerTransactionDetailActivityViewModel,Customer customer) {
        this.showCustomerTransactionDetailActivityViewModel = showCustomerTransactionDetailActivityViewModel;
        this.customer=customer;

        //just for test
        saleRef= db.collection("users").document(userId).collection("sales");
        saleRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                maxId = queryDocumentSnapshots.getDocuments().size();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.payment_history_botton_sheet_dialog, container, false);
        editTotal=v.findViewById(R.id.edit_expenses_total);
        editDescription=v.findViewById(R.id.expenses_description);
        spinner=v.findViewById(R.id.payment_spinner);
        CardView cardViewSavePayment=v.findViewById(R.id.save_expenses);
        List<String> list = new ArrayList<String>();
        list.add("Cash");
        list.add("chaque");
        list.add("paytm");
        list.add("google pay");
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

                    case 3:
                        paymentMode="Google Pay";
                        break;
                    case 4:
                        paymentMode="other";
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        cardViewSavePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Sales sale=new Sales();
                sale.setSaleId(maxId+1);
                String paymentAmount=editTotal.getText().toString();
                Calendar calendar=Calendar.getInstance();
                Long date= DateConverter.fromDate(calendar.getTime());
                sale.setDate(date);
                sale.setSalePode(paymentMode);
                sale.setSalescustId(customer.getCustId());
                sale.setTotalBillAmt(customer.getDebt());
                sale.setPymentAmount(paymentAmount);
                String debt= Double.toString(Double.parseDouble(customer.getDebt())-Double.parseDouble(paymentAmount));
                showCustomerTransactionDetailActivityViewModel.cloudInsertSales(sale);
                showCustomerTransactionDetailActivityViewModel.updateCustomerDebt(debt,customer);
                dismiss();


            }
        });

        return v;
    }

}
