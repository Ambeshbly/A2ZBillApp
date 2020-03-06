package com.example.a2zbilling;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.db.entities.Expenses;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.AvailableStock.AvailableStockAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ItemHolder> {
    private List<Expenses> expenses = new ArrayList<>();
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
        Expenses currentExpenses = expenses.get(position);
        holder.textViewForExpensesCategory.setText(currentExpenses.getExpenseCategory());
        holder.textViewForTotal.setText(currentExpenses.getExpenseTotal()+"\u20B9");
        holder.textViewForPaymentMode.setText("payment Mode: "+ currentExpenses.getPaymentMode());
        holder.textDate.setText("Date : "+currentExpenses.getDate());
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public void setExpenses(List<Expenses> expenses) {
        this.expenses = expenses;
        notifyDataSetChanged();
    }

    public void setOnItemRecyclerViewlistener(ExpensesAdapter.OnItemRecyclerViewListener listener) {
        this.listener = listener;
    }

    public interface OnItemRecyclerViewListener {
        public void onItemClick(Expenses expenses);
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
                        listener.onItemClick(expenses.get(position));
                    }
                }
            });

        }
    }
}