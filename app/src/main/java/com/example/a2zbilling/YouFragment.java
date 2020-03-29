package com.example.a2zbilling;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.a2zbilling.customer.CustomerActivity;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.Expenses;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.StockActivity;
import com.example.a2zbilling.support.SupportActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class YouFragment extends Fragment {

    private BottomSheetBehavior bottomSheetBehavior;
    private CardView cardView,cardViewDueCust,cardViewAllstock,cardViewLessItem,cardViewTodatSale,cardProfit,cardAllExpenese,CardAllExpnesRs;
    private YouViewModel youViewModel;
    private int noOfCustomer,dueCustomer=0,allStock,lessItem=0;
    private TextView allCustomer,dueCustom,textViewAllStock,textViewLessItem,textViewTodaySale,textViewProfit,textViewAllExpense,textViewAllExpneseRs;

    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_me, container, false);
        loadLoacal();
        View scrol=view.findViewById(R.id.scrollviewhai);
        bottomSheetBehavior=BottomSheetBehavior.from(scrol);
        cardView=view.findViewById(R.id.allCustomerCard);
        cardViewDueCust=view.findViewById(R.id.dueCust);
        cardViewAllstock=view.findViewById(R.id.allStoc);
        cardViewLessItem=view.findViewById(R.id.lessIte);
        cardProfit=view.findViewById(R.id.cardProfit);
        cardViewTodatSale=view.findViewById(R.id.cardTodaysale);
        cardAllExpenese=view.findViewById(R.id.cardAllExpenses);
        CardAllExpnesRs=view.findViewById(R.id.cardAllExpensesRs);

        allCustomer=view.findViewById(R.id.allCustomer);
        dueCustom=view.findViewById(R.id.dueCustomer);
        textViewAllStock=view.findViewById(R.id.allStock);
        textViewLessItem=view.findViewById(R.id.lessItem);
        textViewTodaySale=view.findViewById(R.id.toDaySale);
        textViewProfit=view.findViewById(R.id.profit);
        textViewAllExpense=view.findViewById(R.id.allExpenses);
        textViewAllExpneseRs=view.findViewById(R.id.allExpensesRs);



        youViewModel = ViewModelProviders.of(this).get(YouViewModel.class);
        youViewModel.getAllCustomer().observe(getViewLifecycleOwner(), new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
               noOfCustomer= customers.size();
               for (int i = 0; i < customers.size(); i++) {
                    Customer Cust= customers.get(i);
                    if(Double.parseDouble(Cust.getDebt())>0){
                        dueCustomer++;
                    }
                }
                allCustomer.setText(""+noOfCustomer);
                dueCustom.setText(""+dueCustomer);
            }
        });
        youViewModel.getAllItems().observe(getViewLifecycleOwner(), new Observer<List<Stock>>() {
            @Override
            public void onChanged(List<Stock> stocks) {
                allStock=stocks.size();
                for (int i = 0; i < stocks.size(); i++) {
                    Stock stock= stocks.get(i);
                    if(stock.getPrimaryQuant()<10){
                        lessItem++;
                    }
                }
                textViewAllStock.setText(""+allStock);
                textViewLessItem.setText(""+lessItem);
            }
        });

        youViewModel.getAllExpenses().observe(getViewLifecycleOwner(), new Observer<List<Expenses>>() {
            @Override
            public void onChanged(List<Expenses> expenses) {
                int allExp=expenses.size();
                int allExpRs=0;
                for (int i = 0; i < expenses.size(); i++) {
                    Expenses exp= expenses.get(i);
                    allExpRs=allExpRs+Integer.parseInt(exp.getExpenseTotal());
                    }
                textViewAllExpense.setText(""+allExp);
                textViewAllExpneseRs.setText(""+allExpRs);
                }

        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), CustomerActivity.class);
                startActivity(intent);
            }
        });

        cardViewAllstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(getContext(), StockActivity.class);
               startActivity(intent);

            }
        });

        cardViewDueCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),CustomerActivity.class);
                startActivity(intent);
            }
        });
        cardViewLessItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),LessStock.class);
                startActivity(intent);
            }
        });

        cardProfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Test.class);
                startActivity(intent);
            }
        });

        cardViewTodatSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Test.class);
                startActivity(intent);
            }
        });

        cardAllExpenese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ExpensesActivity.class);
                startActivity(intent);
            }
        });

        CardAllExpnesRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ExpensesActivity.class);
                startActivity(intent);
            }
        });

        CardView shopSelling=view.findViewById(R.id.shopsetting);
        shopSelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ShopSettingActivity.class);
                startActivity(intent);
            }
        });
        CardView helpAndSupport=view.findViewById(R.id.help_and_support);
        helpAndSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SupportActivity.class);
                startActivity(intent);
            }
        });
        CardView aboutUs=view.findViewById(R.id.about_us);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4=new Intent(Intent.ACTION_VIEW, Uri.parse("https://a2zbill.blogspot.com/"));
                startActivity(intent4);
            }
        });
        CardView language=view.findViewById(R.id.shopLanguage);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanuageDialog();
            }
        });

        //call getToday Sale Function
        Calendar calendar=Calendar.getInstance();
        long LastDate= DateConverter.fromDate(calendar.getTime());
        Calendar date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        long startDate=date.getTimeInMillis();
        List<Sales> todaySaleList=youViewModel.getSalesFromToFrom(startDate,LastDate);
        double totalSales=0;
        double puchaseTotal=0;
        for(int i=0;i<todaySaleList.size();i++){
            Sales sales=todaySaleList.get(i);
            List<SaleDeatial> saleDeatialList=youViewModel.getAllSAlesList(sales.getSaleId());
            for(int i1=0;i1<saleDeatialList.size();i1++){
                puchaseTotal=puchaseTotal+Double.parseDouble(saleDeatialList.get(i1).getPurchasePrice());
            }
            totalSales=totalSales+Double.parseDouble(sales.getTotalBillAmt());
        }
        textViewTodaySale.setText(""+totalSales);
        double profit=totalSales-puchaseTotal;
        textViewProfit.setText(""+profit);
        return view;
    }

    private void showChangeLanuageDialog() {
        final String[] listItem={"English","Hindi"};
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(getContext());
        mBuilder.setTitle("Chose the Lanuguage");
        mBuilder.setSingleChoiceItems(listItem, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    setLocale("en");
                    getActivity().recreate();
                }
                else if(which==1){
                    setLocale("hi");
                    getActivity().recreate();
                }
                dialog.dismiss();
            }
        });
        AlertDialog myDialog=mBuilder.create();
        myDialog.show();

    }
    public void setLocale(String lang) {
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration=new Configuration();
        configuration.locale=locale;
        getContext().getResources().updateConfiguration(configuration,getContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor=getActivity().getSharedPreferences("setting",MODE_PRIVATE).edit();
        //editor.putString("my_lang",lang);
        editor.putString("my_lang",lang);
        editor.apply();
    }

    public void loadLoacal(){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("setting", MODE_PRIVATE);
        String language=sharedPreferences.getString("my_lang","");
        setLocale(language);
    }



}
