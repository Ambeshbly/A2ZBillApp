package com.example.a2zbilling.counter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.a2zbilling.MainActivity;
import com.example.a2zbilling.MainActivityViewModel;
import com.example.a2zbilling.R;
import com.example.a2zbilling.databinding.DialogFragmentForAddCustomerBinding;
import com.example.a2zbilling.databinding.DialogFragmentForPaymentBinding;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class PaymentDialogFragment extends AppCompatDialogFragment {
    private Spinner spinner;
    private CardView cardViewproceed;
    private ArrayList<Stock> stockList;
    private CounterAdapter adepter;
    private EditText editTotalPayment, editPhone;
    private AutoCompleteTextView autoCompleteTextViewId, autoCompleteTextViewName, autoCompleteTextViewPhoneNo, autoCompleteTextViewAdd;
    private MainActivityViewModel mainActivityViewModel;
    private List<Customer> customerList;
    private int marker;
    ArrayList<String> paymentModeList;

    public static final String PAY_MODE_CASH = "Cash";
    public static final String PAY_MODE_CHEQUE = "Cheque";
    public static final String PAY_MODE_PAYTM = "Paytm";
    public static final String PAY_MODE_GOOGLEPAY = "Google Pay";
    public static final String PAY_MODE_DEBT = "Debt";
    public static final String PAY_MODE_OTHER = "Other";

    DialogFragmentForPaymentBinding customerBinding;

    public PaymentDialogFragment(MainActivityViewModel mainActivityViewModel, CounterAdapter adepter, List<Customer> customerList) {
        this.mainActivityViewModel = mainActivityViewModel;
        this.adepter = adepter;
        this.customerList = customerList;


        paymentModeList = new ArrayList<String>();
        paymentModeList.add(PAY_MODE_CASH);
        paymentModeList.add(PAY_MODE_CHEQUE);
        paymentModeList.add(PAY_MODE_PAYTM);
        paymentModeList.add(PAY_MODE_GOOGLEPAY);
        paymentModeList.add(PAY_MODE_DEBT);
        paymentModeList.add(PAY_MODE_OTHER);


    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_for_payment, null);

        editTotalPayment = view.findViewById(R.id.Total_payemnt);

        autoCompleteTextViewId = view.findViewById(R.id.edit_custId);
        autoCompleteTextViewName = view.findViewById(R.id.edit_custName);
        autoCompleteTextViewPhoneNo = view.findViewById(R.id.edit_CustPhoneNo);
        autoCompleteTextViewAdd = view.findViewById(R.id.edit_custAdd);
        spinner = view.findViewById(R.id.paymeny_spinner);
        cardViewproceed = view.findViewById(R.id.cardview_prodeed_payment);

        ArrayAdapter<Customer> arrayAdapterForCustomerList = new ArrayAdapter<Customer>(getContext(), android.R.layout.simple_list_item_1, customerList);
        autoCompleteTextViewId.setAdapter(arrayAdapterForCustomerList);
        autoCompleteTextViewId.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Customer selectedCustomer = (Customer) parent.getAdapter().getItem(position);
                customerBinding.setCustomer(selectedCustomer);
            }
        });

        // TODO: Set Auto complete on Customer Name, Customer Mobile Number.


        marker = 1;

        customerBinding = DataBindingUtil.bind(view);
        customerBinding.setCustomer(new Customer());
        final Sales sale = mainActivityViewModel.getSale();
        customerBinding.setSale(sale);

        Toast.makeText(getContext(), "customers bbbb", Toast.LENGTH_SHORT).show();


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, paymentModeList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        stockList = mainActivityViewModel.getNewlyAddedStocks().getValue();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
                String paymentMode = (String) parent.getAdapter().getItem(position);
                switch (paymentMode) {
                    case PAY_MODE_CASH:
                        sale.setSalePode(PAY_MODE_CASH);
                        break;

                    case PAY_MODE_CHEQUE:
                        sale.setSalePode(PAY_MODE_CHEQUE);

                        break;
                    case PAY_MODE_PAYTM:
                        sale.setSalePode(PAY_MODE_PAYTM);
                        break;

                    case PAY_MODE_GOOGLEPAY:
                        sale.setSalePode(PAY_MODE_GOOGLEPAY);
                        break;

                    case PAY_MODE_DEBT:
                        sale.setSalePode(PAY_MODE_DEBT);
                        break;

                    case PAY_MODE_OTHER:
                        sale.setSalePode(PAY_MODE_OTHER);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        cardViewproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "proceed", Toast.LENGTH_SHORT).show();


                //double paymentTotal = Double.parseDouble(editTotalPayment.getText().toString());

                Customer customer = customerBinding.getCustomer();

                //TODO : Calculate the debt for customer and set to it in case of PAY_MODE_DEBT.
                if(sale.getSalePode() == PAY_MODE_DEBT
                && customer.getCustomerName().isEmpty()){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Customer Info Missing !")
                            .setMessage("Please Enter Customer Detail.")
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }

                if (customer.getCustId() == 0) {
                    //Check if any new customer needs to be added.

                    if ((customer.getCustomerName() != null && customer.getCustomerName().isEmpty() == false)
                            && (customer.getCustomerPhoneNo() != null && customer.getCustomerPhoneNo().isEmpty() == false)) {
                        // Update the debt of customer.
                        updateDebt(customer, sale);
                        mainActivityViewModel.insertCustomer(customer);

                        // TODO: Use scheduler to schedule insert of Sale only after insert of Customer.
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // if customer detail does not have customer name and customer number,
                    // No need to add any new customer.
                    else {

                    }
                } else {

                    updateDebt(customer, sale);
                    mainActivityViewModel.updateCustomer(customer);
                }


                Sales sales = mainActivityViewModel.getSale();
                if (customer.getCustId() != 0) {
                    sales.setSalescustId(customer.getCustId());
                }
                mainActivityViewModel.insertsales(sales);
                // TODO: Use scheduler to schedule insert of SaleDetail only after insert of Sale.
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < stockList.size(); i++) {
                    Stock stock = stockList.get(i);

                    SaleDeatial saleDeatial = new SaleDeatial();
                    saleDeatial.setSaledetailsaleid(sales.getSaleId());
                    saleDeatial.setSaleDetailitemId(stock.getItemId());
                    saleDeatial.setQuntity(stock.getItemQuentity());
                    saleDeatial.setPurchasePrice(stock.getItemPurchasePerUnit());
                    saleDeatial.setSalingPrice(stock.getItemSalePerUnit());
                    saleDeatial.setSaleDetailItemName(stock.getItemName());
                    saleDeatial.setUnit(stock.getItemUnit());
                    mainActivityViewModel.insertSaleDetail(saleDeatial);
                }
                Toast.makeText(getContext(), "completed", Toast.LENGTH_SHORT).show();
                stockList.clear();
                adepter.setItems(stockList);
                dismiss();

            }
        });


        builder.setView(view);
        return builder.create();
    }

    private void updateDebt(Customer customer, Sales sale){
        if(customer.getDebt().isEmpty()) {
            customer.setDebt(sale.getTotalBillAmt());
        }else{
            // Get existing debt of customer.
            double currentDebt = Double.parseDouble(customer.getDebt());
            double saleDebt = Double.parseDouble(sale.getTotalBillAmt());

            // calculate total debt.
            double updatedDebt = currentDebt + saleDebt;

            // Set Updated Debt to customer.
            customer.setDebt(Double.toString(updatedDebt));
        }
    }
}
