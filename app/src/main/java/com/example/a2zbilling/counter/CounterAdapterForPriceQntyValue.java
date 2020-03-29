package com.example.a2zbilling.counter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.db.entities.Stock;

import java.util.ArrayList;
import java.util.List;

public class CounterAdapterForPriceQntyValue extends RecyclerView.Adapter<CounterAdapterForPriceQntyValue.ItemHolder> {
    private List<Stock> items = new ArrayList<>();


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_for_counter_price_qnty_value, parent, false);
        ItemHolder itemHolder = new ItemHolder(itemView);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Stock currentItem = items.get(position);
        holder.textViewForSalePrice.setText("" + currentItem.getSalePerUnit());
        holder.textViewForQnty.setText("" + currentItem.getPrimaryQuant());

        holder.textViewForValues.setText(""+currentItem.getSaleTotal());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Stock> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    class ItemHolder extends RecyclerView.ViewHolder {

        private TextView  textViewForSalePrice, textViewForQnty, textViewForValues;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            textViewForSalePrice = itemView.findViewById(R.id.textView_for_counter_price);
            textViewForQnty = itemView.findViewById(R.id.textView_counter_qnty);
            textViewForValues = itemView.findViewById(R.id.TextView_values);

        }
    }
}