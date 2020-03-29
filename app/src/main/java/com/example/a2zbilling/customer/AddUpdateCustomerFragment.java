package com.example.a2zbilling.customer;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.databinding.DataBindingUtil;
import com.example.a2zbilling.R;
import com.example.a2zbilling.databinding.DialogFragmentForAddCustomerBinding;
import com.example.a2zbilling.db.entities.Customer;


public class AddUpdateCustomerFragment extends AppCompatDialogFragment {

    private CustomerActivityViewModel customerActivityViewModel;
    private int custId;
    private  Customer customer;
    private DialogFragmentForAddCustomerBinding dialogFragmentForAddCustomerBinding;

    public AddUpdateCustomerFragment(CustomerActivityViewModel customerActivityViewModel,int custId) {
        this.customerActivityViewModel = customerActivityViewModel;
        this.custId=custId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        dialogFragmentForAddCustomerBinding  = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout. dialog_fragment_for_add_customer, null, false);
        Customer customer1=new Customer();
        dialogFragmentForAddCustomerBinding.setCustomer(customer1);
        OnclickListener listener=new OnclickListener();
        dialogFragmentForAddCustomerBinding.setClickListener(listener);

        if(custId==0){
            Toast.makeText(getContext(),"customer not found",Toast.LENGTH_SHORT).show();
        }else {
             customer=customerActivityViewModel.getCustomer();
             custId=customer.getCustId();
             dialogFragmentForAddCustomerBinding.saveText.setText("Update");
             dialogFragmentForAddCustomerBinding.setCustomer(customer);
        }
        builder.setView(dialogFragmentForAddCustomerBinding.getRoot());
        return builder.create();
    }
    public void addCustomer(){
        if(custId!=0){
            customerActivityViewModel.updateCustomer(dialogFragmentForAddCustomerBinding.getCustomer());
        }else {

            dialogFragmentForAddCustomerBinding.getCustomer().setDebt("0");
            customerActivityViewModel.insertCustomer(dialogFragmentForAddCustomerBinding.getCustomer());
        }
        dismiss();
    }

    public class OnclickListener{
        public void doCancel(View view){
            dismiss();
        }
        public void insertCustomer(View view){
            addCustomer();
        }
    }
}
