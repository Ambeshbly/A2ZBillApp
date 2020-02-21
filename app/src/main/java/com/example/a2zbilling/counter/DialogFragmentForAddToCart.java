package com.example.a2zbilling.counter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.db.entities.Stock;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class DialogFragmentForAddToCart extends AppCompatDialogFragment {

    private SellingActivityViewModel sellingActivityViewModel;
    private TextView textViewId, textViewName, textViewAvailablestock;
    private EditText editTextsalePrice, editTextQuntity;
    private Stock stock;
    private Stock tempstock;
    private CardView cardViewAddToCart, cardViewSave;
    List<String> list;
    String quntty;
    int kg,gm,mg,mtr,cm,mm,ltr,ml,box,pc;



    private Spinner spinner;

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
        spinner=view.findViewById(R.id.Add_to_cart_spinner);
        double unit=Double.parseDouble(stock.getItemUnit());

        if (unit==10 || unit==11 || unit==12){
            list= new ArrayList<String>();
            list.add("  kg  ");
            list.add("  gm  ");
            list.add("  mg  ");
            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,list);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spinner.setSelection(position);
                    int p=position;
                    if (p==0){
                        kg=1;
                        quntty=stock.getItemQuentity();
                    }
                    if (p==1){
                        gm=2;
                        quntty=(Integer.toString(Integer.parseInt(stock.getItemQuentity())*1000));
                    }
                    if (p==2){
                        mg=3;
                        quntty=(Integer.toString(Integer.parseInt(stock.getItemQuentity())*1000*1000));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        if (unit==3 | unit==4 | unit==5){
            list= new ArrayList<String>();
            list.add("  Mtr  ");
            list.add("  Cm   ");
            list.add("  Mm   ");
            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,list);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spinner.setSelection(position);
                    int p=position;
                    if (p==0){
                        mtr=4;
                        quntty=stock.getItemQuentity();
                    }
                    if (p==1){
                        cm=5;
                        quntty=(Integer.toString(Integer.parseInt(stock.getItemQuentity())*100));
                    }
                    if (p==2){
                        mm=6;
                        quntty=(Integer.toString(Integer.parseInt(stock.getItemQuentity())*1000*10));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        if (unit==6 | unit==7 ){
            list= new ArrayList<String>();
            list.add("  Ltr  ");
            list.add("  ml  ");
            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,list);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spinner.setSelection(position);
                    int p=position;
                    if (p==0){
                        ltr=7;
                        quntty=stock.getItemQuentity();
                    }
                    if (p==1){
                        ml=8;
                        quntty=(Integer.toString(Integer.parseInt(stock.getItemQuentity())*1000));
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        if (unit==8 ){
            list= new ArrayList<String>();
            list.add("  Box  ");
            list.add("  pc  ");
            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,list);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spinner.setSelection(position);
                    int p=position;
                    if (p==0){
                        box=9;
                        quntty=stock.getItemQuentity();

                    }
                    if (p==1){
                        pc=10;
                        quntty=(Integer.toString(Integer.parseInt(stock.getItemQuentity())*1000));
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        if (unit==9 ){
            list= new ArrayList<String>();
            list.add("  pc  ");
            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,list);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spinner.setSelection(position);
                    int p=position;
                    if (p==0){
                        pc=11;
                        quntty=stock.getItemQuentity();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

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

                if(kg==1){

                  int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                  stock.setItemQuentity(Integer.toString(qnt));
                }

                if(gm==2){

                    int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                    int qnt1=qnt/1000;
                    stock.setItemQuentity(Integer.toString(qnt1));
                }

                if(mg==3){

                    int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                    int qnt1=qnt/1000;
                    int qnt2=qnt1/1000;
                    stock.setItemQuentity(Integer.toString(qnt2));
                }

                if(mtr==4){

                    int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                    stock.setItemQuentity(Integer.toString(qnt));
                }

                if(cm==5){

                    int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                    int qnt1=qnt/100;
                    stock.setItemQuentity(Integer.toString(qnt1));
                }

                if(mm==6){

                    int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                    int qnt1=qnt/100;
                    int qnt2=qnt1/10;
                    stock.setItemQuentity(Integer.toString(qnt2));
                }

                if(ltr==7){

                    int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                    stock.setItemQuentity(Integer.toString(qnt));
                }

                if(ml==8){

                    int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                    int qnt1=qnt/1000;
                    stock.setItemQuentity(Integer.toString(qnt1));
                }

                if( box==9){
                    int pic=Integer.parseInt(stock.getPc());
                    int qnty=Integer.parseInt(quntty);
                    int caluclation=qnty/pic;
                    int qnt=caluclation-Integer.parseInt(quntity);
                    stock.setItemQuentity(Integer.toString(qnt));
                }else


                if(pc==10){
                    int pic=Integer.parseInt(stock.getPc());
                    int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                    int qnt1=qnt/pic;
                    stock.setItemQuentity(Integer.toString(qnt1));
                }

                if(pc==11){
                    int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                    stock.setItemQuentity(Integer.toString(qnt));
                }

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

                //sellingActivityViewModel.setStock(stock);


                //Stock stock1=stock;
                //Intent intent = new Intent().putExtra("stock", tempstock);
                Intent intent=new Intent();
                intent.putExtra("stock",tempstock);
                intent.putExtra("update",stock);
                getActivity().setResult(RESULT_OK, intent);
                dismissAllowingStateLoss();
            }
        });


        cardViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String quntity = editTextQuntity.getText().toString().trim();




                if(kg==1){

                    int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                    stock.setItemQuentity(Integer.toString(qnt));
                }

                if(gm==2){

                    int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                    int qnt1=qnt/1000;
                    stock.setItemQuentity(Integer.toString(qnt1));
                }

                if(mg==3){

                    int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                    int qnt1=qnt/1000;
                    int qnt2=qnt1/1000;
                    stock.setItemQuentity(Integer.toString(qnt2));
                }

                if(mtr==4){

                    int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                    stock.setItemQuentity(Integer.toString(qnt));
                }

                if(cm==5){

                    int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                    int qnt1=qnt/100;
                    stock.setItemQuentity(Integer.toString(qnt1));
                }

                if(mm==6){

                    int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                    int qnt1=qnt/100;
                    int qnt2=qnt1/10;
                    stock.setItemQuentity(Integer.toString(qnt2));
                }

                if(ltr==7){

                    int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                    stock.setItemQuentity(Integer.toString(qnt));
                }

                if(ml==8){

                    int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                    int qnt1=qnt/1000;
                    stock.setItemQuentity(Integer.toString(qnt1));
                }

                if( box==9){
                    int pic=Integer.parseInt(stock.getPc());
                    int qnty=Integer.parseInt(quntty);
                    int caluclation=qnty/pic;
                    int qnt=caluclation-Integer.parseInt(quntity);
                    stock.setItemQuentity(Integer.toString(qnt));
                }else


                if(pc==10){
                    int pic=Integer.parseInt(stock.getPc());
                    int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                    int qnt1=qnt/pic;
                    stock.setItemQuentity(Integer.toString(qnt1));
                }

                if(pc==11){
                    int qnt=Integer.parseInt(quntty)-Integer.parseInt(quntity);
                    stock.setItemQuentity(Integer.toString(qnt));
                }



                tempstock = new Stock();
                tempstock.setItemId(stock.getItemId());
                tempstock.setItemImage(stock.getItemImage());
                tempstock.setItemName(stock.getItemName());
                tempstock.setItemQuentity(quntity);
                tempstock.setItemPurchase(stock.getItemPurchasePerUnit());
                tempstock.setItemPuchaseTotal(stock.getItemPuchaseTotal());
                tempstock.setItemSale(stock.getItemSalePerUnit());
                tempstock.setPc(stock.getPc());
                tempstock.setItemUnit(stock.getItemUnit());
                tempstock.setItemSaleTotal(stock.getItemSaleTotal());
            //    sellingActivityViewModel.update(stock);


                Intent intent=new Intent();
                intent.putExtra("stock",tempstock);
                intent.putExtra("update",stock);
                getActivity().setResult(RESULT_OK, intent);
                getActivity().finish();

            }
        });


        builder.setView(view);
        return builder.create();
    }


}
