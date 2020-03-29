package com.example.a2zbilling.customer.AllCustomer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.customer.AddUpdateCustomerFragment;
import com.example.a2zbilling.customer.CustomerActivity;
import com.example.a2zbilling.customer.CustomerActivityViewModel;
import com.example.a2zbilling.customer.ShowCustomerDetailDialogFragment;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.AvailableStock.AvailableStockAdapter;
import com.example.a2zbilling.stock.addUpdate.AddUpdateStockActivity;

import java.util.ArrayList;
import java.util.List;

public class AllCustomerAdapter extends RecyclerView.Adapter<AllCustomerAdapter.DeptHolder> {
    private List<Customer> customers = new ArrayList<>();
    Context context;
    private CustomerActivityViewModel customerActivityViewModel;
    private AllCustomerAdapter.OnItemRecyclerViewListener listener;

    public AllCustomerAdapter(Context context, CustomerActivityViewModel customerActivityViewModel) {
        this.context = context;
        this.customerActivityViewModel = customerActivityViewModel;
    }

    @NonNull
    @Override
    public DeptHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_for_all_customer,parent,false);
        DeptHolder dh=new DeptHolder(itemView);
        return dh;
    }

    @Override
    public void onBindViewHolder(@NonNull final DeptHolder holder, int position) {
        final Customer currentcustomer = customers.get(position);
        holder.textViewForCustomerName.setText(currentcustomer.getCustomerName());
        holder.textViewForCoustomerId.setText(""+currentcustomer.getCustId());
        holder.textViewForCustomerdebt.setText(""+currentcustomer.getDebt()+" \u20B9");
        holder.text_id.setText("Id : ");
        holder.text_debt.setText("Bal.");
        holder.option_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(context,holder.option_menu);
                popupMenu.inflate(R.menu.menu_for_all_customer_recyclerview);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete:
                                customerActivityViewModel.setCustomer(currentcustomer);
                                ShowCustomerDetailDialogFragment dialogFragementforunit=new ShowCustomerDetailDialogFragment(customerActivityViewModel);
                                dialogFragementforunit.show(((CustomerActivity)context).getSupportFragmentManager(),"exampledialog");
                                break;
                            case R.id.update:

                                customerActivityViewModel.setCustomer(currentcustomer);
                                AddUpdateCustomerFragment ialogFragementforunit=new AddUpdateCustomerFragment(customerActivityViewModel,currentcustomer.getCustId());
                                ialogFragementforunit.show(((CustomerActivity)context).getSupportFragmentManager(),"exampledialog");
                                break;

                        }
                        return false;
                    }

                });
                popupMenu.show();
            }
        });

    }
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
        notifyDataSetChanged();
    }

    public void setOnItemRecyclerViewlistener(AllCustomerAdapter.OnItemRecyclerViewListener listener) {
        this.listener = listener;

    }

    public interface OnItemRecyclerViewListener {
        public void onItemClick(Customer customer);
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    class DeptHolder extends RecyclerView.ViewHolder{
        private TextView textViewForCustomerName;
        private TextView textViewForCoustomerId;
        private TextView textViewForCustomerdebt;
        private TextView text_id;
        private TextView text_debt;
        private TextView option_menu;

        public DeptHolder(@NonNull View itemView) {
            super(itemView);
            textViewForCustomerName=itemView.findViewById(R.id.text_view_customer_name);
            textViewForCoustomerId=itemView.findViewById(R.id.text_view_customer_id);
            textViewForCustomerdebt=itemView.findViewById(R.id.text_view_customer_debt);
            text_id=itemView.findViewById(R.id.text_id);
            text_debt=itemView.findViewById(R.id.text_debt);
            option_menu=itemView.findViewById(R.id.menu_in_recyclerview);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(customers.get(position));
                    }
                }
            });

        }
    }
}
