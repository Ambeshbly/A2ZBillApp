package com.example.a2zbilling.counter.SuspendedBills;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;

import com.example.a2zbilling.MainActivityViewModel;
import com.example.a2zbilling.R;
import com.example.a2zbilling.counter.CounterAdapterForPriceQntyValue;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.db.entities.Suspend;
import com.example.a2zbilling.db.entities.SuspendDetail;
import com.google.android.material.textfield.TextInputEditText;
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

public class DialogFragmentForSespendList extends AppCompatDialogFragment {

    private ArrayList<Stock> stocksList;
    private EditText editCustomerName;
    private double totalBill;

    private  CounterAdapterForPriceQntyValue counterAdapterForPriceQntyValue;

    private CardView save,cancel;

    private MainActivityViewModel mainActivityViewModel;

    private int maxId=0,maxSuspendDetailId=0;

    CollectionReference saleRef,suspendDetailRef;
    //get database refrence of fireStore Database
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //get userCurrent id
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String  userId = currentUser.getUid();

    public DialogFragmentForSespendList(ArrayList<Stock> value,MainActivityViewModel mainActivityViewModel, CounterAdapterForPriceQntyValue counterAdapterForPriceQntyValue) {
        this.stocksList=value;

        this.mainActivityViewModel=mainActivityViewModel;

        this.counterAdapterForPriceQntyValue=counterAdapterForPriceQntyValue;

        //count the number of suspend list
        saleRef = db.collection("users").document(userId).collection("suspend");
        saleRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                maxId = queryDocumentSnapshots.getDocuments().size();
            }
        });

        //count the number of suspent detail list
        suspendDetailRef = db.collection("users").document(userId).collection("suspend detail");
        saleRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                maxSuspendDetailId = queryDocumentSnapshots.getDocuments().size();
            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_for_suspendlist, null);
        editCustomerName=view.findViewById(R.id.edit_customer_name_suspend);
        save=view.findViewById(R.id.cardview_save);
        cancel=view.findViewById(R.id.cardview_cancel);

        totalBill=0.0;

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //suspend work
                Suspend suspend=new Suspend();
                suspend.setSuspendId(maxId+1);

                String CustomerName=editCustomerName.getText().toString().trim();
                suspend.setCustomerName(CustomerName);

                Calendar calendar=Calendar.getInstance();
                String selecteddate= DateFormat.getDateInstance().format(calendar.getTime());
                suspend.setDate(selecteddate);

                for (int i = 0; i < stocksList.size(); i++) {
                    Stock stock=stocksList.get(i);
                    String total=stock.getSaleTotal();
                    totalBill=totalBill+Double.parseDouble(total);
                }
                suspend.setTotalBillAmt(Double.toString(totalBill));

                mainActivityViewModel.addCloudSuspend(suspend);

                //suspend Detail work
                for (int i = 0; i <stocksList.size() ; i++) {
                    SuspendDetail suspendDetail=new SuspendDetail();
                    suspendDetail.setSuspendId(maxId+1);
                    suspendDetail.setSuspendDetailId(maxSuspendDetailId+1);
                    suspendDetail.setItemId(stocksList.get(i).getId());
                    suspendDetail.setItemName(stocksList.get(i).getName());
                    suspendDetail.setQnty(stocksList.get(i).getPrimaryQuant());
                    suspendDetail.setPurchasePrice(stocksList.get(i).getPurchasePerUnit());
                    suspendDetail.setSalingPrice(stocksList.get(i).getSalePerUnit());
                    suspendDetail.setUnit(stocksList.get(i).getPriamryUnit());
                    mainActivityViewModel.addSuspendDetail(suspendDetail);
                }

                stocksList.clear();
                counterAdapterForPriceQntyValue.setItems(stocksList);
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }
}
