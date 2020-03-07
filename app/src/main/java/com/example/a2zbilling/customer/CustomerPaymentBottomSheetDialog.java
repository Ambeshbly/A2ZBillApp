package com.example.a2zbilling.customer;

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

import com.example.a2zbilling.R;
import com.example.a2zbilling.db.entities.ExpensesCategory;
import com.example.a2zbilling.db.entities.Payment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CustomerPaymentBottomSheetDialog extends BottomSheetDialogFragment {

    private CustomerPaymentHistoryActivityViewModel customerPaymentHistoryActivityViewModel;
    private EditText editTotal,editDescription,editPaymentMode;
    private Spinner spinner;
    private String paymentMode;

    public CustomerPaymentBottomSheetDialog(CustomerPaymentHistoryActivityViewModel customerPaymentHistoryActivityViewModel) {
        this.customerPaymentHistoryActivityViewModel = customerPaymentHistoryActivityViewModel;
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

        cardViewSavePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String total=editTotal.getText().toString();
                String description=editDescription.getText().toString();
                Calendar calendar=Calendar.getInstance();
                String selecteddate= DateFormat.getDateInstance().format(calendar.getTime());
                Payment payment=new Payment(total,paymentMode,description,selecteddate);
                customerPaymentHistoryActivityViewModel.insertPayment(payment);
                Toast.makeText(getContext(),"sucessful",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return v;
    }
}
