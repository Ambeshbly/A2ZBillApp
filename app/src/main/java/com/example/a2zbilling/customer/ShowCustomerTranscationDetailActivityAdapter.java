package com.example.a2zbilling.customer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.DateConverter;
import com.example.a2zbilling.R;
import com.example.a2zbilling.db.entities.Sales;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class ShowCustomerTranscationDetailActivityAdapter extends RecyclerView.Adapter<ShowCustomerTranscationDetailActivityAdapter.ItemHolder>  {
    private List<Sales> sales = new ArrayList<>();

    private ShowCustomerTranscationDetailActivityAdapter.OnItemRecyclerViewListener listener;

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_viee_for_transaction_history, parent, false);
        ItemHolder itemHolder = new ItemHolder(itemView);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Sales currentsales = sales.get(position);
        holder.textView_for_saleId.setText(""+currentsales.getSaleId());
        holder.textViewForTotalAmtText.setText("");
        holder.textViewForTotal.setText("Bal."+currentsales.getTotalBillAmt()+" \u20B9");
        holder.textViewpaymentMode.setText(currentsales.getSalePode());
        holder.textViewdate.setText(""+ DateFormat.getDateInstance().format(DateConverter.toDate(currentsales.getDate())));
        if(currentsales.getTotalBillAmt().charAt(0)=='+'){
            holder.textView_sale_text.setText("Payment :");
        }
    }

    @Override
    public int getItemCount() {
        return sales.size();
    }

    public void setItems(List<Sales> sales) {
        this.sales = sales;
        notifyDataSetChanged();
    }

    public void setOnItemRecyclerViewlistener(ShowCustomerTranscationDetailActivityAdapter.OnItemRecyclerViewListener listener) {
        this.listener = listener;

   }

    public interface OnItemRecyclerViewListener {
        public void onItemClick(Sales sales);
   }

    class ItemHolder extends RecyclerView.ViewHolder {

        private TextView textView_for_saleId;
        private TextView textView_sale_text;
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
            textView_sale_text=itemView.findViewById(R.id.text_view_sale_id_text);

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