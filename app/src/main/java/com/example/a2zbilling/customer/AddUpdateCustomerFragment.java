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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


public class AddUpdateCustomerFragment extends AppCompatDialogFragment {

    private CustomerActivityViewModel customerActivityViewModel;
    private int custId;
    private  Customer customer;
    private int maxCustomer=0;
    private DialogFragmentForAddCustomerBinding dialogFragmentForAddCustomerBinding;

    //just for test
    CollectionReference customerRef;
    //get database refrence of fireStore Database
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //get userCurrent id
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    String userId=currentUser.getUid();

    public AddUpdateCustomerFragment(CustomerActivityViewModel customerActivityViewModel,int custId) {
        this.customerActivityViewModel = customerActivityViewModel;
        this.custId=custId;

        customerRef= db.collection("users").document(userId).collection("customers");
        customerRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                maxCustomer = queryDocumentSnapshots.getDocuments().size();
            }
        });

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
            customerActivityViewModel.cloudUpdateCustomer(dialogFragmentForAddCustomerBinding.getCustomer());
        }else {

            dialogFragmentForAddCustomerBinding.getCustomer().setDebt("0");
            dialogFragmentForAddCustomerBinding.getCustomer().setCustId(maxCustomer+1);
           // customerActivityViewModel.insertCustomer(dialogFragmentForAddCustomerBinding.getCustomer());
            customerActivityViewModel.cloudInsertCustomer(dialogFragmentForAddCustomerBinding.getCustomer());

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
