package com.example.a2zbilling.counter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
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

import com.example.a2zbilling.R;
import com.example.a2zbilling.db.entities.Stock;

import static android.app.Activity.RESULT_OK;

public class DialogFragmentForAddToCart extends AppCompatDialogFragment {

    private SellingActivityViewModel sellingActivityViewModel;
    private TextView textViewId, textViewName, textViewAvailablestock;
    private EditText editTextsalePrice, editTextQuntity;
    private Stock stock;
    private Stock tempstock;
    private CardView cardViewAddToCart, cardViewSave;

    public DialogFragmentForAddToCart(SellingActivityViewModel sellingActivityViewModel) {
        this.sellingActivityViewModel = sellingActivityViewModel;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_fragment_for_add_to_cart, null);
        stock = sellingActivityViewModel.getStock();

        textViewId = view.findViewById(R.id.Dialog_id);
        textViewName = view.findViewById(R.id.Dialog_item_name);
        textViewAvailablestock = view.findViewById(R.id.Dialod_available_stock);
        editTextsalePrice = view.findViewById(R.id.Dialog_editText_sale_price);
        editTextQuntity = view.findViewById(R.id.Dialog_editext_qunity);
        cardViewAddToCart = view.findViewById(R.id.Dialog_cardView_add_to_cart);
        cardViewSave = view.findViewById(R.id.cardview_save);

        textViewId.setText("" + stock.getItemId());
        textViewName.setText(stock.getItemName());
        textViewAvailablestock.setText(stock.getItemQuentity());
        editTextsalePrice.setText(stock.getItemSalePerUnit());

        //   Toast.makeText(getContext(),"malo",Toast.LENGTH_SHORT).show();


        cardViewAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "malo", Toast.LENGTH_SHORT).show();


                String quntity = editTextQuntity.getText().toString().trim();
                int itemquntity = Integer.parseInt(stock.getItemQuentity()) - Integer.parseInt(quntity);
                String qnty = Integer.toString(itemquntity);
                stock.setItemQuentity(qnty);


                tempstock = new Stock();
                tempstock.setItemId(stock.getItemId());
                tempstock.setItemImage(stock.getItemImage());
                tempstock.setItemName(stock.getItemName());
                tempstock.setItemQuentity(quntity);
                tempstock.setItemPurchase(stock.getItemPurchasePerUnit());
                tempstock.setItemPuchaseTotal(stock.getItemPuchaseTotal());
                tempstock.setItemSale(stock.getItemSalePerUnit());
                tempstock.setItemUnit(stock.getItemUnit());
                tempstock.setItemSaleTotal(stock.getItemSaleTotal());

                //Stock stock1=stock;
                Intent intent = new Intent().putExtra("stock", tempstock);
                getActivity().setResult(RESULT_OK, intent);
                dismissAllowingStateLoss();


            }
        });


        cardViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String quntity = editTextQuntity.getText().toString().trim();
                int itemquntity = Integer.parseInt(stock.getItemQuentity()) - Integer.parseInt(quntity);
                String qnty = Integer.toString(itemquntity);
                stock.setItemQuentity(qnty);


                tempstock = new Stock();
                tempstock.setItemId(stock.getItemId());
                tempstock.setItemImage(stock.getItemImage());
                tempstock.setItemName(stock.getItemName());
                tempstock.setItemQuentity(quntity);
                tempstock.setItemPurchase(stock.getItemPurchasePerUnit());
                tempstock.setItemPuchaseTotal(stock.getItemPuchaseTotal());
                tempstock.setItemSale(stock.getItemSalePerUnit());
                tempstock.setItemUnit(stock.getItemUnit());
                tempstock.setItemSaleTotal(stock.getItemSaleTotal());


                Intent intent = new Intent().putExtra("stock", tempstock);
                getActivity().setResult(RESULT_OK, intent);
                getActivity().finish();

            }
        });


        builder.setView(view);
        return builder.create();
    }


}
