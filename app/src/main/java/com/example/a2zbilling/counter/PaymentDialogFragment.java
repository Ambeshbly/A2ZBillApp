package com.example.a2zbilling.counter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

import com.example.a2zbilling.MainActivityViewModel;
import com.example.a2zbilling.R;
import com.example.a2zbilling.databinding.DialogFragmentForPaymentBinding;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;

import java.util.ArrayList;
import java.util.List;

public class PaymentDialogFragment extends AppCompatDialogFragment {
    private TextView textViewTotal;
    private double total;
    private List<String> list;
    private Spinner spinner;
    private String stringTotal;
    private CardView cardViewproceed;
    private ArrayList<Stock> stockList;
    private CounterAdapter adepter;
    private EditText   editAdd, editPhone;
    private AutoCompleteTextView autoCompleteTextViewId,autoCompleteTextViewName;
    private MainActivityViewModel mainActivityViewModel;
    private List<Customer> customerList;
    private   ArrayList<String> custNameList;
    private DialogFragmentForPaymentBinding dialogFragmentForPaymentBinding;

    public PaymentDialogFragment(double total, MainActivityViewModel mainActivityViewModel, CounterAdapter adepter) {
        this.total = total;
        this.mainActivityViewModel = mainActivityViewModel;
        this.adepter = adepter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_for_payment, null);
        textViewTotal = view.findViewById(R.id.totalPayment_text);
        autoCompleteTextViewId = view.findViewById(R.id.edit_custId);
        autoCompleteTextViewName = view.findViewById(R.id.edit_custName);
        editPhone = view.findViewById(R.id.edit_CustPhoneNo);
        editAdd = view.findViewById(R.id.edit_custAdd);
        spinner = view.findViewById(R.id.paymeny_spinner);
        stringTotal = Double.toString(total);
        autoCompleteTextViewId.setVisibility(View.INVISIBLE);
        autoCompleteTextViewName.setVisibility(View.INVISIBLE);
        editPhone.setVisibility(View.INVISIBLE);
        editAdd.setVisibility(View.INVISIBLE);
        textViewTotal.setText(stringTotal);
        dialogFragmentForPaymentBinding= DataBindingUtil.setContentView(getActivity(),R.layout.dialog_fragment_for_payment);
        dialogFragmentForPaymentBinding.setCustomer(new Customer());



        mainActivityViewModel.getAllCustomer().observe(this.getActivity(), new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                customerList=customers;
                Toast.makeText(getContext(), "customers bbbb", Toast.LENGTH_SHORT).show();
                custNameList =new ArrayList<String>();
                for (int i = 0; i < customers.size(); i++) {
                    Customer customer = customers.get(i);
                    double value = 0;
                    String name = customer.getCustomerName();
                    custNameList.add(name);
                }
            }
        });


        list = new ArrayList<String>();
        list.add("Case");
        list.add("chaqe");
        list.add("paytem");
        list.add("google pay");
        list.add("debt");
        list.add("other");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        stockList = mainActivityViewModel.getNewlyAddedStocks().getValue();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
                int p = position;
                switch (p) {
                    case 0:
                        Sales sales = new Sales();
                        sales.setTotalBillAmt(stringTotal);
                        sales.setSalePode("case");
                        mainActivityViewModel.setSales(sales);
                        break;
                    case 1:
                        Sales sales1 = new Sales();
                        sales1.setTotalBillAmt(stringTotal);
                        sales1.setSalePode("chaqe");
                        mainActivityViewModel.setSales(sales1);
                        break;
                    case 2:
                        Sales sales2 = new Sales();
                        sales2.setTotalBillAmt(stringTotal);
                        sales2.setSalePode("paytem");
                        mainActivityViewModel.setSales(sales2);
                        break;
                    case 3:
                        Sales sales3 = new Sales();
                        sales3.setTotalBillAmt(stringTotal);
                        sales3.setSalePode("google pay");
                        mainActivityViewModel.setSales(sales3);
                        break;
                    case 4:
                        autoCompleteTextViewId.setVisibility(View.VISIBLE);
                        autoCompleteTextViewName.setVisibility(View.VISIBLE);
                        editPhone.setVisibility(View.VISIBLE);
                        editAdd.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> arrayAdapterForCustomerList=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, custNameList);
                        autoCompleteTextViewName.setAdapter(arrayAdapterForCustomerList);


               case 5:
                        Sales sales5 = new Sales();
                        sales5.setTotalBillAmt(stringTotal);
                        sales5.setSalePode("other");
                        mainActivityViewModel.setSales(sales5);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        cardViewproceed = view.findViewById(R.id.cardview_prodeed_payment);
        cardViewproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(getContext(), "proceed", Toast.LENGTH_SHORT).show();

                Sales sales = mainActivityViewModel.getSales();
                mainActivityViewModel.insertsales(sales);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < stockList.size(); i++) {
                    Stock stock2 = stockList.get(i);
                    int itemId = stock2.getItemId();
                    double quntity = stock2.getItemQuentity();
                    String sellingPrice = stock2.getItemSalePerUnit();
                    String itemName = stock2.getItemName();
                    SaleDeatial saleDeatial = new SaleDeatial();
                    saleDeatial.setSaledetailsaleid(sales.getSaleId());
                    saleDeatial.setSaleDetailitemId(itemId);
                    saleDeatial.setQuntity(quntity);
                    saleDeatial.setSalingPrice(sellingPrice);
                    saleDeatial.setSaleDetailItemName(itemName);
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


}
