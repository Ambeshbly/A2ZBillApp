package com.example.a2zbilling.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2zbilling.Dashboard;
import com.example.a2zbilling.R;
import com.example.a2zbilling.YouFragment;
import com.example.a2zbilling.counter.BillList.BillHistoryActivityAdapter;
import com.example.a2zbilling.counter.BillList.BillHistoryActivityViewModel;
import com.example.a2zbilling.counter.BillList.ShowBillingHistoryFragments;
import com.example.a2zbilling.counter.CounterFragment;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.ExpensesCategory;
import com.example.a2zbilling.db.entities.Payment;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ShowCustomerTransactionDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ShowCustomerTranscationDetailActivityAdapter adepter;
    private ShowCustomerTransactionDetailActivityViewModel showCustomerTransactionDetailActivityViewModel;
    private double total;
    private TextView textViewTotal,textViewMenu;
    private View view;
    private Button bt;
    private TextView textViewNoTranscation;
    private ImageView imageViewNoTranscation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_customer_transaction_detail);
        view=findViewById(R.id.toolbar);
        bt=findViewById(R.id.bt_Button);

        TextView textViewTitle=findViewById(R.id.customer_name_title_bar);
        TextView textViewPhoneNo=findViewById(R.id.customer_phone_no);
        TextView textViewAdd=findViewById(R.id.customer_add);
        textViewTotal=findViewById(R.id.toolbar_debt);
        textViewMenu=findViewById(R.id.menu_in_customerDetail);

        textViewNoTranscation=findViewById(R.id.textView_no_transaxtion);
        imageViewNoTranscation=findViewById(R.id.imageView_no_transcation);

        recyclerView = findViewById(R.id.recycler_view_sale_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setHasFixedSize(true);
        adepter = new ShowCustomerTranscationDetailActivityAdapter();
        recyclerView.setAdapter(adepter);
        Intent intent = getIntent();
         final Customer selectedCustomer = (Customer) intent.getSerializableExtra("customer_transaction");
        textViewTitle.setText(selectedCustomer.getCustomerName());
        textViewPhoneNo.setText(selectedCustomer.getCustomerPhoneNo());
        textViewAdd.setText(selectedCustomer.getCustomerAddress());

        showCustomerTransactionDetailActivityViewModel = ViewModelProviders.of(this).get(ShowCustomerTransactionDetailActivityViewModel.class);

        showCustomerTransactionDetailActivityViewModel.getAllsaleForcustomer(selectedCustomer.getCustId()).observe(this, new Observer<List<Sales>>() {
            @Override
            public void onChanged(List<Sales> sales) {

                if(sales.isEmpty()){
                }else {
                    textViewNoTranscation.setVisibility(View.INVISIBLE);
                    imageViewNoTranscation.setVisibility(View.INVISIBLE);
                    Sales sales1 = sales.get(sales.size()-1);
                    total=total+Double.parseDouble(sales1.getTotalBillAmt());
                }
                textViewTotal.setText(""+total+" \u20B9");
                adepter.setItems(sales);
            }
        });


        adepter.setOnItemRecyclerViewlistener(new ShowCustomerTranscationDetailActivityAdapter.OnItemRecyclerViewListener() {
            @Override
            public void onItemClick(Sales sales) {
                showCustomerTransactionDetailActivityViewModel.setSales(sales);
                String id=Integer.toString(sales.getSaleId());
                Toast.makeText(getBaseContext(), id, Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                ShowBillingHistoryFragments showBillingHistoryFragments=new ShowBillingHistoryFragments(showCustomerTransactionDetailActivityViewModel,1);
                fragmentTransaction.replace(R.id.fragment_conterner2,showBillingHistoryFragments);
                fragmentTransaction.commit();


            }
        });
        textViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(ShowCustomerTransactionDetailActivity.this, textViewMenu);
                popupMenu.inflate(R.menu.menu_for_customer);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.detail:
                                Toast.makeText(getBaseContext(),"detail",Toast.LENGTH_SHORT).show();
                                showCustomerTransactionDetailActivityViewModel.setCustomer(selectedCustomer);
                                ShowCustomerDetailDialogFragment dialogFragementforunit=new ShowCustomerDetailDialogFragment(showCustomerTransactionDetailActivityViewModel,1);
                                dialogFragementforunit.show(getSupportFragmentManager(),"exampledialog");
                                break;
                            case R.id.update:
//                                Toast.makeText(context,"update",Toast.LENGTH_SHORT).show();
//                                customerActivityViewModel.setCustomer(currentcustomer);
//                                AddUpdateCustomerFragment ialogFragementforunit=new AddUpdateCustomerFragment(customerActivityViewModel,currentcustomer.getCustId());
//                                ialogFragementforunit.show(((CustomerActivity)context).getSupportFragmentManager(),"exampledialog");
                                 break;

                        }
                        return false;
                    }

                });
                popupMenu.show();

            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerPaymentBottomSheetDialog customerPaymentBottomSheetDialog=new CustomerPaymentBottomSheetDialog(showCustomerTransactionDetailActivityViewModel,selectedCustomer);
                customerPaymentBottomSheetDialog.show(getSupportFragmentManager(),"customer Payment");
            }
        });

    }
}
