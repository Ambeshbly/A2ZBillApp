package com.example.a2zbilling.counter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;

import com.example.a2zbilling.MainActivityViewModel;
import com.example.a2zbilling.R;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;

import java.util.ArrayList;
import java.util.List;

public class DialogFragmentForUpdateCounterStock extends AppCompatDialogFragment {
    private CardView cancel,save;
    private MainActivityViewModel mainActivityViewModel;
    private TextView itemName,itemTotal;
    private EditText qnty,price;
    private int position;
    private CounterAdapterForPriceQntyValue counterAdapterForPriceQntyValue;

    public DialogFragmentForUpdateCounterStock(MainActivityViewModel mainActivityViewModel,CounterAdapterForPriceQntyValue counterAdapterForPriceQntyValue,int position) {
        this.mainActivityViewModel = mainActivityViewModel;
        this.counterAdapterForPriceQntyValue=counterAdapterForPriceQntyValue;
        this.position=position;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
         View view = inflater.inflate(R.layout.dialog_fragment_for_update_counter_stock, null);

         //--------------------------finding View------------------
         cancel=view.findViewById(R.id.cardview_cancel);
         save=view.findViewById(R.id.cardview_save);
         itemName=view.findViewById(R.id.itemName);
         itemTotal=view.findViewById(R.id.iten_total);
         qnty=view.findViewById(R.id.qnty);
         price=view.findViewById(R.id.price);

         //--------------------------get Current Stock---------------
         final Stock currentStock=mainActivityViewModel.getStock();

         //-----------------------set current Stock detail-------------------
         itemName.setText("Item Name: "+currentStock.getName());
         price.setText(currentStock.getSalePerUnit());
         qnty.setText(""+currentStock.getPrimaryQuant());
         itemTotal.setText(currentStock.getSaleTotal());

         final Double currentQnty=currentStock.getPrimaryQuant();

         //-------------------Click Listener on Both Button----------------
         cancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dismiss();
             }
         });

         qnty.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {
             }
             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
             }
             @Override
             public void afterTextChanged(Editable s) {
                 if(!qnty.getText().toString().isEmpty()){
                     Double quntity=Double.parseDouble(qnty.getText().toString());
                     Double salePrice=Double.parseDouble(price.getText().toString());
                     itemTotal.setText(""+quntity*salePrice);
                 }
             }
         });

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!price.getText().toString().isEmpty()){
                    Double quntity=Double.parseDouble(qnty.getText().toString());
                    Double salePrice=Double.parseDouble(price.getText().toString());
                    itemTotal.setText(""+quntity*salePrice);
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 ArrayList<Stock> soldStockList = mainActivityViewModel.getSoldStocksList();
                 Stock soldStock=soldStockList.get(position);
                 soldStock.setPrimaryQuant((soldStock.getPrimaryQuant()-Double.parseDouble(qnty.getText().toString()))+currentQnty);
                 soldStockList.set(position,soldStock);

                 Double quntity=Double.parseDouble(qnty.getText().toString());
                 Double salePrice=Double.parseDouble(price.getText().toString());
                 Double itemTotal=quntity*salePrice;

                 Sales sales=mainActivityViewModel.getSale();
                 sales.setTotalBillAmt(Double.toString(Double.parseDouble(sales.getTotalBillAmt())+itemTotal));

                 currentStock.setSalePerUnit(Double.toString(salePrice));
                 currentStock.setSaleTotal(Double.toString(itemTotal));
                 currentStock.setPrimaryQuant(quntity);

                 ArrayList<Stock> stockList = mainActivityViewModel.getNewlyAddedStocks().getValue();
                 stockList.set(position,currentStock);
                 counterAdapterForPriceQntyValue.setItems(mainActivityViewModel.getNewlyAddedStocks().getValue());
                 dismiss();
             }
         });
         builder.setView(view);
         return builder.create();
    }
}
