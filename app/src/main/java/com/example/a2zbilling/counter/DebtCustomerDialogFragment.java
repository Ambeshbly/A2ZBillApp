package com.example.a2zbilling.counter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;

import com.example.a2zbilling.MainActivityViewModel;
import com.example.a2zbilling.R;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;

import java.util.ArrayList;

public class DebtCustomerDialogFragment extends AppCompatDialogFragment {
    private EditText editTextcustId, editTextCustName, editTextCustPhoneNo, editTextCustAdd;
    private CardView cardViewAlradyCustomer, cardViewNewCustomer;
    private TextView textViewNameCust;
    private MainActivityViewModel mainActivityViewModel;
    private CounterAdapter adepter;
    private ArrayList<Stock> stockList;
    private int total;

    public DebtCustomerDialogFragment(MainActivityViewModel mainActivityViewModel, int total, CounterAdapter adepter, ArrayList<Stock> stockList) {
        this.mainActivityViewModel = mainActivityViewModel;
        this.total = total;
        this.adepter = adepter;
        this.stockList = stockList;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_debt_customer, null);
        editTextcustId = view.findViewById(R.id.edit_customer_Id);
        editTextCustName = view.findViewById(R.id.edit_customerName);
        editTextCustPhoneNo = view.findViewById(R.id.edit_customerPhoneNo);
        editTextCustAdd = view.findViewById(R.id.edit_customeAdd);
        cardViewAlradyCustomer = view.findViewById(R.id.cardview_prodeed_payment);
        textViewNameCust = view.findViewById(R.id.textCustName);
        cardViewNewCustomer = view.findViewById(R.id.cardview_prodeed_payment2);
        cardViewAlradyCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int custId = Integer.parseInt(editTextcustId.getText().toString().trim());
                Customer customer = null;
                try {
                    customer = mainActivityViewModel.getCustList(custId);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                textViewNameCust.setText("Customer Name: " + customer.getCustomerName());
                int debt = Integer.parseInt(customer.getDebt());
                debt = debt + total;
                customer.setDebt(Integer.toString(debt));
                mainActivityViewModel.updateCustomer(customer);
                Sales sales5 = new Sales();
                sales5.setTotalBillAmt(Integer.toString(total));
                sales5.setSalePode("debt");
                sales5.setSalescustId(customer.getCustId());
                mainActivityViewModel.insertsales(sales5);
                Toast.makeText(getContext(), "completed", Toast.LENGTH_SHORT).show();
            }
        });
        cardViewNewCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String custName = editTextCustName.getText().toString().trim();
                String custPhoneNo = editTextCustPhoneNo.getText().toString().trim();
                String custAdd = editTextCustAdd.getText().toString().trim();
                String debt = Integer.toString(total);
                Customer customer = new Customer(custName, custAdd, custPhoneNo, debt);
                mainActivityViewModel.insertCustomer(customer);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Sales sales5 = new Sales();
                sales5.setTotalBillAmt(debt);
                sales5.setSalePode("debt");
                sales5.setSalescustId(customer.getCustId());
                mainActivityViewModel.insertsales(sales5);
                Toast.makeText(getContext(), "completed", Toast.LENGTH_SHORT).show();
                stockList.clear();
                adepter.setItems(stockList);
                dismiss();

            }
        });


        builder.setView(view);
        return builder.create();
    }
}
