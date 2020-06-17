package com.example.a2zbilling.counter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.service.autofill.ImageTransformation;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import com.example.a2zbilling.MainActivityViewModel;
import com.example.a2zbilling.R;
import com.example.a2zbilling.databinding.DialogFragmentForPaymentBinding;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class PaymentDialogFragment extends AppCompatDialogFragment {
    private Spinner spinner;
    private CardView cardViewproceed;
    private ArrayList<Stock> stockList;
   // private CounterAdapterForStock adepter;
    private CounterAdapterForPriceQntyValue counterAdapterForPriceQntyValue;
    private EditText editTotalPayment, editPhone;
    private AutoCompleteTextView autoCompleteTextViewId, autoCompleteTextViewName, autoCompleteTextViewPhoneNo, autoCompleteTextViewAdd;
    private MainActivityViewModel mainActivityViewModel;
    private List<Customer> customers = new ArrayList<>();
    private int marker;
    private String paymentRs;
    private int maxId=0,max=0,maxCustomer=0;
    private ProgressDialog regProgress;
    ArrayList<String> paymentModeList;
    public static final String PAY_MODE_CASH = "Cash";
    public static final String PAY_MODE_CHEQUE = "Cheque";
    public static final String PAY_MODE_PAYTM = "Paytm";
    public static final String PAY_MODE_GOOGLEPAY = "Google Pay";
    public static final String PAY_MODE_DEBT = "Debt";
    public static final String PAY_MODE_OTHER = "Other";

    DialogFragmentForPaymentBinding customerBinding;



    //just for test
    CollectionReference saleRef,saleDetailRef,customerRef;
    //get database refrence of fireStore Database
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //get userCurrent id
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String userId=currentUser.getUid();




    public PaymentDialogFragment(MainActivityViewModel mainActivityViewModel, CounterAdapterForPriceQntyValue counterAdapterForPriceQntyValue, final List<Customer> customerList) {
        this.mainActivityViewModel = mainActivityViewModel;
       // this.adepter = adepter;
        this.counterAdapterForPriceQntyValue=counterAdapterForPriceQntyValue;



        paymentModeList = new ArrayList<String>();
        paymentModeList.add(PAY_MODE_CASH);
        paymentModeList.add(PAY_MODE_CHEQUE);
        paymentModeList.add(PAY_MODE_PAYTM);
        paymentModeList.add(PAY_MODE_GOOGLEPAY);
        paymentModeList.add(PAY_MODE_DEBT);
        paymentModeList.add(PAY_MODE_OTHER);


        //just for test
        saleRef= db.collection("users").document(userId).collection("sales");
        saleRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                maxId = queryDocumentSnapshots.getDocuments().size();
            }
        });

        saleDetailRef= db.collection("users").document(userId).collection("sale Detail");
        saleDetailRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                max = queryDocumentSnapshots.getDocuments().size();
            }
        });

        customerRef= db.collection("users").document(userId).collection("customers");
        customerRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                maxCustomer = queryDocumentSnapshots.getDocuments().size();

                for (int i = 0; i <queryDocumentSnapshots.getDocuments().size() ; i++) {
                    DocumentSnapshot document=queryDocumentSnapshots.getDocuments().get(i);
                    Customer customer=document.toObject(Customer.class);
                    customers.add(customer);
                }
            }
        });



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
        regProgress=new ProgressDialog(getContext());
        cardViewproceed = view.findViewById(R.id.cardview_prodeed_payment);

        ArrayAdapter<Customer> arrayAdapterForCustomerList = new ArrayAdapter<Customer>(getContext(), android.R.layout.simple_list_item_1, customers);
        autoCompleteTextViewId.setAdapter(arrayAdapterForCustomerList);
        autoCompleteTextViewId.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Customer selectedCustomer = (Customer) parent.getAdapter().getItem(position);
                customerBinding.setCustomer(selectedCustomer);
            }
        });


        ArrayAdapter<Customer> arrayAdapterForCustomerList1 = new ArrayAdapter<Customer>(getContext(), android.R.layout.simple_list_item_1, customers);
        autoCompleteTextViewName.setAdapter(arrayAdapterForCustomerList1);
        autoCompleteTextViewName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Customer selectedCustomer = (Customer) parent.getAdapter().getItem(position);
                autoCompleteTextViewId.setText(""+selectedCustomer.getCustId());
                customerBinding.setCustomer(selectedCustomer);
            }
        });

        // TODO: Set Auto complete on Customer Name.


        marker = 1;

        customerBinding = DataBindingUtil.bind(view);
        customerBinding.setCustomer(new Customer());
        final Sales sale = mainActivityViewModel.getSale();
        customerBinding.setSale(sale);

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
                regProgress.setTitle("Processing");
                regProgress.setMessage("please Wait...");
                regProgress.setCanceledOnTouchOutside(false);
                regProgress.show();

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

                        String pyment;
                        if(editTotalPayment.getText().toString().trim().isEmpty()){
                            paymentRs=sale.getTotalBillAmt();
                        }else {
                            pyment=editTotalPayment.getText().toString().trim();
                            paymentRs= Double.toString(Double.parseDouble(sale.getTotalBillAmt())-Double.parseDouble(pyment));
                            sale.setPymentAmount(pyment);
                        }


                        // Update the debt of customer.
                        updateDebt(customer, sale,paymentRs);
                       // mainActivityViewModel.insertCustomer(customer);

                        customer.setCustId(maxCustomer+1);

                        sale.setSalescustId(maxCustomer+1);

                        mainActivityViewModel.cloudInsertCustomer(customer);

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

                    String pyment;
                    if(editTotalPayment.getText().toString().trim().isEmpty()){
                        paymentRs=sale.getTotalBillAmt();
                    }else {
                        pyment=editTotalPayment.getText().toString().trim();
                        paymentRs= Double.toString(Double.parseDouble(sale.getTotalBillAmt())-Double.parseDouble(pyment));
                        sale.setPymentAmount(pyment);
                    }

                    updateDebt(customer, sale,paymentRs);
                    sale.setSalescustId(customer.getCustId());
                    mainActivityViewModel.updateCustomerDebt(customer.getDebt(),customer);
                }

                // update the stocks.
                ArrayList<Stock> soldStockList = mainActivityViewModel.getSoldStocksList();
                for( Stock stock : soldStockList){
                  //  mainActivityViewModel.update(stock);
                    mainActivityViewModel.updatetStock(stock,Integer.toString(stock.getId()));
                }



               /* if (customer.getCustId() != 0) {
                    sale.setSalescustId(maxCustomer+1);
                }*/


                //mainActivityViewModel.insertsales(sale);

                //just for test
                sale.setSaleId(maxId+1);
                mainActivityViewModel.cloudInsertSales(sale);

                // TODO: Use scheduler to schedule insert of SaleDetail only after insert of Sale.
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < stockList.size(); i++) {
                    Stock stock = stockList.get(i);

                    SaleDeatial saleDeatial = new SaleDeatial();
                    saleDeatial.setSaleDetailId(max+1);
                    saleDeatial.setSaledetailsaleid(maxId+1);
                    saleDeatial.setSaleDetailitemId(stock.getId());
                    saleDeatial.setQuntity(stock.getPrimaryQuant());
                    saleDeatial.setPurchasePrice(stock.getPurchasePerUnit());
                    saleDeatial.setSalingPrice(stock.getSalePerUnit());
                    saleDeatial.setSaleDetailItemName(stock.getName());
                    saleDeatial.setUnit(stock.getPriamryUnit());
                  //  mainActivityViewModel.insertSaleDetail(saleDeatial);

                    //just for test
                    mainActivityViewModel.cloudInsertSalDetail(saleDeatial);
                }
                sale.setSaleId(0);
                sale.setTotalBillAmt("0");
                sale.setPymentAmount("0");
                sale.setSalescustId(0);
                stockList.clear();
             //   adepter.setItems(stockList);
                counterAdapterForPriceQntyValue.setItems(stockList);
                regProgress.dismiss();
                dismiss();

            }
        });


        builder.setView(view);
        return builder.create();
    }

    private void updateDebt(Customer customer, Sales sale,String debt){
        if(sale.getSalePode() == PAY_MODE_DEBT) {
            if (customer.getDebt().isEmpty()) {
                customer.setDebt(debt);
            } else {

                // Get existing debt of customer.
                double currentDebt = Double.parseDouble(customer.getDebt());
                double saleDebt = Double.parseDouble(debt);

                // calculate total debt.
                double updatedDebt = currentDebt + saleDebt;

                // Set Updated Debt to customer.
                customer.setDebt(Double.toString(updatedDebt));
            }
        }
    }
}
