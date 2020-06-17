package com.example.a2zbilling;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Test extends AppCompatActivity {

    private TestViewModel testViewModel;
    private TextView textViewTodaySellPrice,textViewTodayPurchasePrice,textViewTodayNetProfiteAndLoss,textViewYesterdaysSellPrice,textViewYesterdaysPurchasePrice,textViewYesterdaysNetProfitAndLoss;
    private List<Sales> salesList = new ArrayList<>();
    private List<Sales> salesList1 = new ArrayList<>();
    private List<SaleDeatial> saleDeatialsList = new ArrayList<>();
    double todaySellTotal=0,todayPurchaseToal=0;
    double yesterDaySellTotal=0,yesterdayPurchaseTotal=0;

    CollectionReference saleRef;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String userId=currentUser.getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        setTitle("Profit And Loss");

        //viewModel initialition
        testViewModel = ViewModelProviders.of(this).get(TestViewModel.class);


        //textview find the id from the Xml layout
        textViewTodaySellPrice=findViewById(R.id.text_total_sell_price);
        textViewTodayPurchasePrice=findViewById(R.id.text_total_purchase_price);
        textViewTodayNetProfiteAndLoss=findViewById(R.id.text_net_profit_price);
        textViewYesterdaysSellPrice=findViewById(R.id.text_Tommorrow_total_sell_price);
        textViewYesterdaysPurchasePrice=findViewById(R.id.text_tommorroe_total_purchase_price);
        textViewYesterdaysNetProfitAndLoss=findViewById(R.id.text_tommorrow_net_profit_price);


        saleRef= db.collection("users").document(userId).collection("sales");
        saleRef.addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                Calendar date = new GregorianCalendar();
                date.set(Calendar.HOUR_OF_DAY, 0);
                date.set(Calendar.MINUTE, 0);
                date.set(Calendar.SECOND, 0);
                date.set(Calendar.MILLISECOND, 0);
                long startDate=date.getTimeInMillis();

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                long yesterdayStartDate=cal.getTimeInMillis();

                for (int i = 0; i <queryDocumentSnapshots.getDocuments().size() ; i++) {
                    DocumentSnapshot document=queryDocumentSnapshots.getDocuments().get(i);
                    Sales sales=document.toObject(Sales.class);
                    Calendar calendar=Calendar.getInstance();
                    long LastDate= DateConverter.fromDate(calendar.getTime());
                    if(sales.getDate()>=startDate && sales.getDate()<=LastDate ){
                        salesList.add(sales);
                        todaySellTotal=todaySellTotal+Double.parseDouble(sales.getTotalBillAmt());
                    }

                    if(sales.getDate()>=yesterdayStartDate && sales.getDate()<=LastDate){
                        salesList1.add(sales);
                        yesterDaySellTotal=yesterDaySellTotal+Double.parseDouble(sales.getTotalBillAmt());
                    }
                }
                textViewTodaySellPrice.setText(""+todaySellTotal+" \u20B9");
                textViewYesterdaysSellPrice.setText(""+yesterDaySellTotal+" \u20B9");

            }
        });

        //get user Referance from fireStore database
        CollectionReference saleDetailRef= db.collection("users").document(userId).collection("sale Detail");
        saleDetailRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (int i = 0; i <queryDocumentSnapshots.getDocuments().size() ; i++) {
                    for (int j = 0; j <salesList.size() ; j++) {
                        SaleDeatial saleDeatial=queryDocumentSnapshots.getDocuments().get(i).toObject(SaleDeatial.class);
                        int saleId=saleDeatial.getSaledetailsaleid();
                        if(salesList.get(j).getSaleId()==saleId){
                            saleDeatialsList.add(saleDeatial);
                            todayPurchaseToal=todayPurchaseToal+Double.parseDouble(saleDeatial.getPurchasePrice());
                        }
                    }

                    for (int k = 0; k <salesList1.size() ; k++) {
                        SaleDeatial saleDeatial=queryDocumentSnapshots.getDocuments().get(i).toObject(SaleDeatial.class);
                        int saleId=saleDeatial.getSaledetailsaleid();
                        if(salesList.get(k).getSaleId()==saleId){
                            yesterdayPurchaseTotal=yesterdayPurchaseTotal+Double.parseDouble(saleDeatial.getPurchasePrice());
                        }
                    }
                }
                textViewTodayPurchasePrice.setText(""+todayPurchaseToal+" \u20B9");
                textViewYesterdaysPurchasePrice.setText(""+yesterdayPurchaseTotal+" \u20B9");

                if(todaySellTotal>=todayPurchaseToal){
                    textViewTodayNetProfiteAndLoss.setTextColor(Color.GREEN);
                    textViewTodayNetProfiteAndLoss.setText("+"+(todaySellTotal-todayPurchaseToal)+" \u20B9");
                }
                if(todayPurchaseToal>todaySellTotal){
                    textViewTodayNetProfiteAndLoss.setTextColor(Color.RED);
                    textViewTodayNetProfiteAndLoss.setText("-"+(todayPurchaseToal-todaySellTotal)+" \u20B9");
                }


                if(yesterDaySellTotal>=yesterdayPurchaseTotal){
                    textViewYesterdaysNetProfitAndLoss.setTextColor(Color.GREEN);
                    textViewYesterdaysNetProfitAndLoss.setText("+"+(yesterDaySellTotal-yesterdayPurchaseTotal)+" \u20B9");
                }
                if(yesterdayPurchaseTotal>yesterDaySellTotal){
                    textViewYesterdaysNetProfitAndLoss.setTextColor(Color.RED);
                    textViewYesterdaysNetProfitAndLoss.setText("-"+(yesterdayPurchaseTotal-yesterDaySellTotal)+" \u20B9");
                }


            }
        });

    }
}
