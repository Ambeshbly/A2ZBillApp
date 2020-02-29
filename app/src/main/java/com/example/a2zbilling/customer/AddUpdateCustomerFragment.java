package com.example.a2zbilling.customer;

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
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.stock.addUpdate.AddUpdateStockActivityViewModel;

public class AddUpdateCustomerFragment extends AppCompatDialogFragment {
    CardView cardviewCancel,cardViewSave;
    private EditText editTextCustomerName,editTextCustomerPhoneNo,editTextCustomerAddress;
    private CustomerActivityViewModel customerActivityViewModel;
    private int custId;
    private TextView textViewsaveText;
    private  Customer customer;

    public AddUpdateCustomerFragment(CustomerActivityViewModel customerActivityViewModel,int custId) {
        this.customerActivityViewModel = customerActivityViewModel;
        this.custId=custId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_fragment_for_add_customer,null);
        cardviewCancel=view.findViewById(R.id.cardview_cancel);
        editTextCustomerName=view.findViewById(R.id.edit_customer_name);
        editTextCustomerPhoneNo=view.findViewById(R.id.edit_customer_phone_no);
        editTextCustomerAddress=view.findViewById(R.id.edit_customer_address);
        cardViewSave=view.findViewById(R.id.cardview_save);
        textViewsaveText=view.findViewById(R.id.saveText);

        if(custId==0){
            Toast.makeText(getContext(),"customer not found",Toast.LENGTH_SHORT).show();
        }else {
             customer=customerActivityViewModel.getCustomer();
             custId=customer.getCustId();
             editTextCustomerName.setText(customer.getCustomerName());
             editTextCustomerPhoneNo.setText(customer.getCustomerPhoneNo());
             editTextCustomerAddress.setText(customer.getCustomerAddress());
             textViewsaveText.setText("Update");
        }

        cardviewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();
            }
        });
        cardViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addCustomer();
            }
        });
        builder.setView(view);
        return builder.create();
    }
    public void addCustomer(){
        if(custId!=0){
            String name=editTextCustomerName.getText().toString().trim();
            String phoneNo=editTextCustomerPhoneNo.getText().toString().trim();
            String address=editTextCustomerAddress.getText().toString().trim();
            customer.setCustId(custId);
            customer.setCustomerName(name);
            customer.setCustomerPhoneNo(phoneNo);
            customer.setCustomerAddress(address);
            customerActivityViewModel.updateCustomer(customer);
        }else {
            String name = editTextCustomerName.getText().toString().trim();
            String phoneNo = editTextCustomerPhoneNo.getText().toString().trim();
            String address = editTextCustomerAddress.getText().toString().trim();
            Customer customer = new Customer(name, phoneNo, address,"no debt");
            customerActivityViewModel.insertCustomer(customer);
            Toast.makeText(getContext(), name + " inserted", Toast.LENGTH_SHORT).show();
        }
        dismiss();
    }
}
