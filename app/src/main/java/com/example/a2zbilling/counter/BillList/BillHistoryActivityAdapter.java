package com.example.a2zbilling.counter.BillList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.DateConverter;
import com.example.a2zbilling.R;
import com.example.a2zbilling.customer.AddUpdateCustomerFragment;
import com.example.a2zbilling.customer.CustomerActivity;
import com.example.a2zbilling.customer.ShowCustomerDetailDialogFragment;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.printer.BTPrinter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class BillHistoryActivityAdapter extends RecyclerView.Adapter<BillHistoryActivityAdapter.ItemHolder>  {
    private List<Sales> sales = new ArrayList<>();
    Context context;
    Activity activity;
    private BillHistoryActivityViewModel billHistoryActivityViewModel;
    private BillHistoryActivityAdapter.OnItemRecyclerViewListener listener;

    public BillHistoryActivityAdapter(Context context, Activity activity,BillHistoryActivityViewModel billHistoryActivityViewModel) {
        this.context = context;
        this.activity = activity;
        this.billHistoryActivityViewModel= billHistoryActivityViewModel;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_viee_for_transaction_history, parent, false);
        ItemHolder itemHolder = new ItemHolder(itemView);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, int position) {
        final Sales currentsales = sales.get(position);
        holder.textView_for_saleId.setText(""+currentsales.getSaleId());
        holder.textViewForTotalAmtText.setText("Total Amt:  ");
        holder.textViewForTotal.setText(currentsales.getTotalBillAmt());
        holder.textViewpaymentMode.setText(currentsales.getSalePode());
        holder.textViewdate.setText("Date : "+ DateFormat.getDateInstance().format(DateConverter.toDate(currentsales.getDate())));
        holder.textViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(context,holder.textViewMenu);
                popupMenu.inflate(R.menu.menu_for_printer);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.printer:
                                //write the code to connect the printer here
                                BTPrinter btPrinter = BTPrinter.getInstance(activity);
                                btPrinter.printTicket(billHistoryActivityViewModel.getSaleDeatialList(currentsales.getSaleId()), currentsales);


                                Toast.makeText(context,"printer Connected Sucessfully",Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }

                });
                popupMenu.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return sales.size();
    }

    public void setItems(List<Sales> sales) {
        this.sales = sales;
        notifyDataSetChanged();
    }

    public void setOnItemRecyclerViewlistener(BillHistoryActivityAdapter.OnItemRecyclerViewListener listener) {
        this.listener = listener;

    }

    public interface OnItemRecyclerViewListener {
        public void onItemClick(Sales sales);
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        private TextView textView_for_saleId;
        private TextView textViewForTotalAmtText;
        private TextView textViewForTotal;
        private TextView textViewpaymentMode;
        private TextView textViewdate;
        private TextView textViewMenu;



        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            textView_for_saleId = itemView.findViewById(R.id.text_view_sale_id);
            textViewForTotalAmtText = itemView.findViewById(R.id.text_view_Total_amt_text);
            textViewForTotal = itemView.findViewById(R.id.text_view_total);
            textViewpaymentMode = itemView.findViewById(R.id.paymenttext);
            textViewdate=itemView.findViewById(R.id.paymentdate);
            textViewMenu=itemView.findViewById(R.id.menu_in_recyclerview);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(sales.get(position));
                    }
                }
            });

        }
    }
}