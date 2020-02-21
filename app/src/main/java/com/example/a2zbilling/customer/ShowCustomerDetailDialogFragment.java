package com.example.a2zbilling.customer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.a2zbilling.R;

public class ShowCustomerDetailDialogFragment extends AppCompatDialogFragment {
    private CustomerActivityViewModel customerActivityViewModel;
    private TextView textViewId, textViewName, textViewAdd, textViewphone;

    public ShowCustomerDetailDialogFragment(CustomerActivityViewModel customerActivityViewModel) {
        this.customerActivityViewModel = customerActivityViewModel;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.show_customer_detail_dialog_fragment, null);
        textViewId = view.findViewById(R.id.text_cust_id1);
        textViewName = view.findViewById(R.id.text_cust_name1);
        textViewphone = view.findViewById(R.id.text_cust_phone1);
        textViewAdd = view.findViewById(R.id.text_cust_add1);
        textViewId.setText("" + customerActivityViewModel.getCustomer().getCustId());
        textViewName.setText(customerActivityViewModel.getCustomer().getCustomerName());
        textViewphone.setText(customerActivityViewModel.getCustomer().getCustomerPhoneNo());
        textViewAdd.setText(customerActivityViewModel.getCustomer().getCustomerAddress());
        builder.setView(view);
        return builder.create();
    }

}
