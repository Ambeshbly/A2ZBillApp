package com.example.a2zbilling.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.ExpensesActivity2;
import com.example.a2zbilling.ExpensesActivity2ViewModel;
import com.example.a2zbilling.R;
import com.example.a2zbilling.ShowExpensesDialogFragment;
import com.example.a2zbilling.db.entities.ExpensesCategory;
import com.example.a2zbilling.db.entities.Payment;

import java.util.ArrayList;
import java.util.List;

public class CustomerPaymentAdapter extends RecyclerView.Adapter<CustomerPaymentAdapter.ItemHolder> {
    private List<Payment> payments = new ArrayList<>();

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_for_payment_history, parent, false);
        ItemHolder itemHolder = new ItemHolder(itemView);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Payment currentpayment = payments.get(position);
        holder.textViewForTotal.setText(currentpayment.getTotal()+"\u20B9");
        holder.textViewForPaymentMode.setText("payment Mode: "+ currentpayment.getPaymentMode());
        holder.textViewDate.setText("Date : "+currentpayment.getDate());

    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

      public void setPayments(List<Payment> payments) {
         this.payments = payments;
         notifyDataSetChanged();
      }

    class ItemHolder extends RecyclerView.ViewHolder {
        private TextView textViewForExpensesCategory, textViewForTotal, textViewForPaymentMode,textViewDate;
        private CardView cardViewEye;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            textViewForTotal = itemView.findViewById(R.id.text_expenses1_total);
            textViewForPaymentMode = itemView.findViewById(R.id.text_expeses1_payment_mode);
            cardViewEye=itemView.findViewById(R.id.recycler_view_cardview_eye);
            textViewDate=itemView.findViewById(R.id.text_date2);
        }
    }
}