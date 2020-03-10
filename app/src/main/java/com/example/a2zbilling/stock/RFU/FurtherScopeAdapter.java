package com.example.a2zbilling.stock.RFU;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.db.entities.Expenses;
import com.example.a2zbilling.db.entities.Purchase;

import java.util.ArrayList;
import java.util.List;

public class FurtherScopeAdapter extends RecyclerView.Adapter<FurtherScopeAdapter.ItemHolder> {
    private List<Purchase> purchases = new ArrayList<>();
    private OnItemRecyclerViewListener listener;


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_for_expenses, parent, false);
        ItemHolder itemHolder = new ItemHolder(itemView);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Purchase currentPurchase = purchases.get(position);
        holder.textViewForExpensesCategory.setText("Purchase Id : "+currentPurchase.getPurchaseId());
        holder.textViewForTotal.setText("Total Items : "+currentPurchase.getItemNumber());
        holder.textViewForPaymentMode.setText("");
        holder.textDate.setText("Date : "+currentPurchase.getDate());
    }

    @Override
    public int getItemCount() {
        return purchases.size();
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
        notifyDataSetChanged();
    }

    public void setOnItemRecyclerViewlistener(FurtherScopeAdapter.OnItemRecyclerViewListener listener) {
        this.listener = listener;
    }

    public interface OnItemRecyclerViewListener {
        public void onItemClick(Purchase purchase);
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        private TextView textViewForExpensesCategory, textViewForTotal, textViewForPaymentMode,textDate;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            textViewForExpensesCategory = itemView.findViewById(R.id.text_expeses_category);
            textViewForTotal = itemView.findViewById(R.id.text_expenses_total);
            textViewForPaymentMode = itemView.findViewById(R.id.text_expeses_payment_mode);
            textDate = itemView.findViewById(R.id.text_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(purchases.get(position));
                    }
                }
            });

        }
    }
}