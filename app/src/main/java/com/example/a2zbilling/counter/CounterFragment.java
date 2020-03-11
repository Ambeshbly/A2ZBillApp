package com.example.a2zbilling.counter;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.DateConverter;
import com.example.a2zbilling.MainActivityViewModel;
import com.example.a2zbilling.R;
import com.example.a2zbilling.counter.BillList.BillHistoryActivity;
import com.example.a2zbilling.counter.Selling.SellingStocksActivity;
import com.example.a2zbilling.counter.SuspendedBills.SuspendedTransactionListActivity;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CounterFragment extends Fragment {

    public static final int ADD_NEW_STOCK_REQ_CODE = 1;


    CounterAdapter adepter;
    Stock updateStock;
    MediaPlayer mediaPlayer;
    private TextView textViewTotal;
    private MainActivityViewModel mainActivityViewModel;
    private List<Customer> customerList;


    public CounterFragment(MainActivityViewModel mainActivityViewModel) {
        this.mainActivityViewModel = mainActivityViewModel;
        mainActivityViewModel.setSale(new Sales());
    }

    //onCreateView Override method
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_counter, container, false);

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.simple);

        textViewTotal = view.findViewById(R.id.textView_counter_total);


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_for_counter_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adepter = new CounterAdapter();
        recyclerView.setAdapter(adepter);

        mainActivityViewModel.getNewlyAddedStocks().observe(getViewLifecycleOwner(), new Observer<ArrayList<Stock>>() {
            @Override
            public void onChanged(ArrayList<Stock> stocks) {
                adepter.setItems(stocks);
            }
        });

        mainActivityViewModel.getAllCustomer().observe(getViewLifecycleOwner(), new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                customerList = customers;
            }
        });


        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NEW_STOCK_REQ_CODE) {

            //this is first object
            Stock stock = (Stock) data.getSerializableExtra("stock");

            //this id second object which is need to update when proceed button is click
            updateStock = (Stock) data.getSerializableExtra("update");

            //   String itemName = stock.getItemName();
            mainActivityViewModel.addNewlyAddedStock(stock);


            ArrayList<Stock> stockList = mainActivityViewModel.getNewlyAddedStocks().getValue();
            double total = 0;

            for (int i = 0; i < stockList.size(); i++) {
                Stock stock2 = stockList.get(i);
                double value = 0;
                value = stock2.getItemQuentity() * Integer.parseInt(stock2.getItemSalePerUnit());
                total = total + value;

            }

            String totalString = Double.toString(total);
            textViewTotal.setText(totalString);
            Sales sale = mainActivityViewModel.getSale();
            sale.setTotalBillAmt(totalString);
            Calendar calendar=Calendar.getInstance();
           // String selecteddate= DateFormat.getDateInstance().format(calendar.getTime());
            Long date= DateConverter.fromDate(calendar.getTime());
            sale.setDate(date);

        }

    }

    //onStart override method which is used to add action listener in flotating button or both CardView
    @Override
    public void onStart() {
        //finding Cardview waitList in Xml file
        CardView cardView_waitList = getView().findViewById(R.id.waitlistcardview);
        //finding Cardview conformList in Xml file
        CardView cardView_conformList = getView().findViewById(R.id.conformListcardview);
        CardView cardView_Proceed = getView().findViewById(R.id.cardview_proceed);
        cardView_Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                ArrayList<Stock> stockList = mainActivityViewModel.getNewlyAddedStocks().getValue();
                if (stockList.isEmpty()) {
                    Toast.makeText(getContext(), "please add the item first", Toast.LENGTH_SHORT).show();
                } else {
                    //double total = Double.parseDouble(mainActivityViewModel.getSale().getTotalBillAmt());
                    PaymentDialogFragment dialogFragment = new PaymentDialogFragment(mainActivityViewModel, adepter, customerList);
                    dialogFragment.show(getActivity().getSupportFragmentManager(), "exampledialog");
                    mainActivityViewModel.update(updateStock);
                }
            }
        });


        //finding Floating button  in Xml file

        super.onStart();

        FloatingActionButton floatingActionButton = getView().findViewById(R.id.bt_float);
        //Floating Button add action listener
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent = new Intent(getContext(), SellingStocksActivity.class);
                startActivityForResult(intent, ADD_NEW_STOCK_REQ_CODE);
                // startActivity(intent);
            }
        });

        //Cardview waitList add action Listener
        cardView_waitList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent = new Intent(getContext(), SuspendedTransactionListActivity.class);
                startActivity(intent);
            }
        });

        //cardView conformList Add action listener
        cardView_conformList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent = new Intent(getContext(), BillHistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
