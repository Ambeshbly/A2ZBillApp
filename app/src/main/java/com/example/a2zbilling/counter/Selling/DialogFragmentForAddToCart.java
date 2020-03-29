package com.example.a2zbilling.counter.Selling;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import com.example.a2zbilling.R;
import com.example.a2zbilling.counter.CounterFragment;
import com.example.a2zbilling.databinding.DailogFragmentForAddToCartBinding;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.addUpdate.Unit;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class DialogFragmentForAddToCart extends AppCompatDialogFragment {

    private Stock availableStock;
    private CardView cardViewAddToCart, cardViewSave;
    private Spinner spinner;

    public DialogFragmentForAddToCart(Stock availableStock) {
        this.availableStock = availableStock;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_fragment_for_add_to_cart, null);

        DailogFragmentForAddToCartBinding addToCartBinding = DataBindingUtil.bind(view);
        final Stock saleStock = new Stock(availableStock);

        // re-initialize the quantity for sale stock object.
        saleStock.setPrimaryQuant(0);

        // Bind the both objects.
        addToCartBinding.setSaleStock(saleStock);
        addToCartBinding.setAvailableStock(availableStock);

        spinner = view.findViewById(R.id.Add_to_cart_spinner);
        //double unit = Double.parseDouble(stock.getItemUnit());
        String unit = availableStock.getPriamryUnit();

        List<String> list = new ArrayList<String>();
        ArrayAdapter<String> arrayAdapter = null;

        switch(unit){

            case Unit.UNIT_KG:
                list.add(Unit.UNIT_KG);
            case Unit.UNIT_GM:
                list.add(Unit.UNIT_GM);
            case Unit.UNIT_MG:
                list.add(Unit.UNIT_MG);
                arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        spinner.setSelection(position);
                        String selectedUnit = (String) parent.getAdapter().getItem(position);
                        switch (selectedUnit){
                            case Unit.UNIT_KG:
                                saleStock.setPriamryUnit(Unit.UNIT_KG);
                                break;
                            case Unit.UNIT_GM:
                                saleStock.setPriamryUnit(Unit.UNIT_GM);
                                break;
                            case Unit.UNIT_MG:
                                saleStock.setPriamryUnit(Unit.UNIT_MG);
                                break;
                            default:
                                // Handle error case.
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;

            case Unit.UNIT_MTR:
                list.add(Unit.UNIT_MTR);
            case Unit.UNIT_CM:
                list.add(Unit.UNIT_CM);
            case Unit.UNIT_MM:
                list.add(Unit.UNIT_MM);
                arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        spinner.setSelection(position);
                        String selectedUnit = (String) parent.getAdapter().getItem(position);
                        switch (selectedUnit){
                            case Unit.UNIT_MTR:
                                saleStock.setPriamryUnit(Unit.UNIT_MTR);
                                break;
                            case Unit.UNIT_CM:
                                saleStock.setPriamryUnit(Unit.UNIT_CM);
                                break;
                            case Unit.UNIT_MM:
                                saleStock.setPriamryUnit(Unit.UNIT_MM);
                                break;
                            default:
                                // Handle error case.
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;

            case Unit.UNIT_LTR:
            case Unit.UNIT_ML:
                list.add(Unit.UNIT_LTR);
                list.add(Unit.UNIT_ML);
                arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        spinner.setSelection(position);
                        String selectedUnit = (String) parent.getAdapter().getItem(position);
                        switch (selectedUnit){
                            case Unit.UNIT_LTR:
                                saleStock.setPriamryUnit(Unit.UNIT_LTR);
                                break;
                            case Unit.UNIT_ML:
                                saleStock.setPriamryUnit(Unit.UNIT_ML);
                                break;
                            default:
                                // Handle error case.
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;

            case Unit.UNIT_BOX:
                list.add(Unit.UNIT_BOX);
                list.add(Unit.UNIT_PC);
                arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        spinner.setSelection(position);
                        String selectedUnit = (String) parent.getAdapter().getItem(position);
                        switch (selectedUnit){
                            case Unit.UNIT_BOX:
                                saleStock.setPriamryUnit(Unit.UNIT_BOX);
                                break;
                            case Unit.UNIT_PC:
                                saleStock.setPriamryUnit(Unit.UNIT_PC);
                                break;
                            default:
                                // Handle error case.
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;

            case Unit.UNIT_PC:
                list.add(Unit.UNIT_PC);
                arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        spinner.setSelection(position);
                        String selectedUnit = (String) parent.getAdapter().getItem(position);
                        if (selectedUnit == Unit.UNIT_PC) {
                            saleStock.setPriamryUnit(Unit.UNIT_PC);
                        }
                        // Handle error case.
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            default:
                // Handle the error case.
                break;
        }

        cardViewAddToCart = view.findViewById(R.id.Dialog_cardView_add_to_cart);
        cardViewSave = view.findViewById(R.id.cardview_save);

        cardViewAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStockObjAndSend(availableStock, saleStock);
                dismissAllowingStateLoss();
            }
        });

        cardViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStockObjAndSend(availableStock, saleStock);
                getActivity().finish();

            }
        });

        builder.setView(addToCartBinding.getRoot());
        return builder.create();
    }
    private void updateStockObjAndSend(Stock availableStock, Stock saleStock){
        Toast.makeText(getContext(), "malo", Toast.LENGTH_SHORT).show();

        updateAvailableStock(availableStock, saleStock);

        Intent intent = new Intent();
        intent.putExtra(CounterFragment.TAG_SALE_STOCK_OBJ, saleStock);
        intent.putExtra(CounterFragment.TAG_AVAILABLE_STOCK_OBJ, availableStock);
        getActivity().setResult(RESULT_OK, intent);
    }
    private void updateAvailableStock(Stock availableStock, Stock saleStock){

        double availableQuantity = availableStock.getPrimaryQuant();
        double saleQuantity = saleStock.getPrimaryQuant();

        switch (saleStock.getPriamryUnit()) {
            case Unit.UNIT_KG:
                availableQuantity = availableQuantity - saleQuantity;
                break;
            case Unit.UNIT_GM:
                availableQuantity = availableQuantity * 1000 - saleQuantity;
                break;
            case Unit.UNIT_MG:
                availableQuantity = availableQuantity * 1000* 1000 - saleQuantity;
                break;
            case Unit.UNIT_MTR:
                availableQuantity = availableQuantity - saleQuantity;
                break;
            case Unit.UNIT_CM:
                availableQuantity = availableQuantity * 100 - saleQuantity;
                break;
            case Unit.UNIT_MM:
                availableQuantity = availableQuantity * 1000 - saleQuantity;
                break;
            case Unit.UNIT_LTR:
                availableQuantity = availableQuantity - saleQuantity;
                break;
            case Unit.UNIT_ML:
                availableQuantity = availableQuantity * 1000 - saleQuantity;
                break;
            case Unit.UNIT_BOX:
                double pcPerBox = Double.parseDouble(availableStock.getSecondUnit());
                availableQuantity = availableQuantity - saleQuantity* pcPerBox;
                break;
            case Unit.UNIT_PC:
                availableQuantity = availableQuantity - saleQuantity;
                break;
            default:
                // handle error case.

        }
        availableStock.setPrimaryQuant(availableQuantity);
    }


}
