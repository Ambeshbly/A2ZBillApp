package com.example.a2zbilling.customer;

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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.DateConverter;
import com.example.a2zbilling.R;
import com.example.a2zbilling.counter.BillList.BillHistoryActivityViewModel;
import com.example.a2zbilling.counter.BillList.ShowBillingHistoryFragments;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.printer.BTPrinter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.DateFormat;
import java.util.List;

public class CloudShowCustomerSaleActivityAdepter extends FirestoreRecyclerAdapter<Sales, CloudShowCustomerSaleActivityAdepter.CloudStockViewHolder>  {


    private Context context;
    private BillHistoryActivityViewModel billHistoryActivityViewModel;
    Activity activity;

    public CloudShowCustomerSaleActivityAdepter(@NonNull FirestoreRecyclerOptions<Sales> options, Context context ) {
        super(options);
        this.context=context;

    }

    @Override
    protected void onBindViewHolder(@NonNull final CloudStockViewHolder holder, int position, @NonNull final Sales
            model) {

        holder.textView_for_saleId.setText(""+model.getSaleId());
        holder.textViewdate.setText(""+ DateFormat.getDateInstance().format(DateConverter.toDate(model.getDate())));
        holder.paymentMode.setText("Payment Mode : "+model.getSalePode());
        holder.textViewForTotalBillAmount.setText("Total Amt : "+model.getTotalBillAmt()+" \u20B9");
        holder.paymentBillAmount.setText("Pay Amt : "+model.getPymentAmount()+" \u20B9");
    }



    @NonNull
    @Override
    public CloudStockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_viee_for_customer_transaction_history,parent,false);
        return new CloudStockViewHolder(view);
    }

    public class CloudStockViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_for_saleId;
        private TextView paymentMode;
        private TextView textViewdate;
        private TextView textViewForTotalBillAmount;
        private TextView paymentBillAmount;




        public CloudStockViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_for_saleId = itemView.findViewById(R.id.text_view_sale_id);
            textViewdate=itemView.findViewById(R.id.paymenttext);
            paymentMode=itemView.findViewById(R.id.payment);
            textViewForTotalBillAmount = itemView.findViewById(R.id.paymentdate);
            paymentBillAmount = itemView.findViewById(R.id.text_view_total);


        }
    }
}
