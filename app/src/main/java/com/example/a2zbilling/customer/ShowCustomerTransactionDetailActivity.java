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
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ShowCustomerTransactionDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ShowCustomerTranscationDetailActivityAdapter adepter;
    private ShowCustomerTransactionDetailActivityViewModel showCustomerTransactionDetailActivityViewModel;
    private double total;
    private TextView textViewTotal,textViewMenu;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_customer_transaction_detail);

        view=findViewById(R.id.toolbar);

        TextView textViewTitle=findViewById(R.id.customer_name_title_bar);
        textViewTotal=findViewById(R.id.toolbar_debt);
        textViewMenu=findViewById(R.id.menu_in_customerDetail);

        recyclerView = findViewById(R.id.recycler_view_sale_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setHasFixedSize(true);


        adepter = new ShowCustomerTranscationDetailActivityAdapter();
        recyclerView.setAdapter(adepter);
        Intent intent = getIntent();
         final Customer selectedCustomer = (Customer) intent.getSerializableExtra("customer_transaction");
        textViewTitle.setText(selectedCustomer.getCustomerName());

        showCustomerTransactionDetailActivityViewModel = ViewModelProviders.of(this).get(ShowCustomerTransactionDetailActivityViewModel.class);

        showCustomerTransactionDetailActivityViewModel.getAllsaleForcustomer(selectedCustomer.getCustId()).observe(this, new Observer<List<Sales>>() {

            @Override
            public void onChanged(List<Sales> sales) {
                Toast.makeText(getBaseContext(), "Sales ", Toast.LENGTH_SHORT).show();
                adepter.setItems(sales);
                for (int i = 0; i < sales.size(); i++) {
                    Sales sales1 = sales.get(i);
                    total=total+Double.parseDouble(sales1.getTotalBillAmt());
                }
                textViewTotal.setText(""+total+" \u20B9");
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


                            case R.id.payment_history:
                                Intent intent1=new Intent(getBaseContext(),CustomerPaymentHistoryActivity.class);
                                startActivity(intent1);
                                break;

                        }
                        return false;
                    }

                });
                popupMenu.show();

            }
        });

    }



}
