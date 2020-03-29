package com.example.a2zbilling.stock.RFU;

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

public class PurchaseHistoryFragmentAdapter extends RecyclerView.Adapter<PurchaseHistoryFragmentAdapter.ItemHolder> {
    private List<Stock> stocks = new ArrayList<>();


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_viee_for_purchase_history, parent, false);
        ItemHolder itemHolder = new ItemHolder(itemView);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Stock currentStock = stocks.get(position);
        int i = 1;
        holder.textView_for_saleDetail_id.setText("" + currentStock.getId());
        holder.textView_for_saleDetail_name.setText(currentStock.getName());
        holder.textView_for_saleDetail_price.setText(currentStock.getPurchasePerUnit());
        holder.textView_for_saleDetail_qty.setText(currentStock.getSalePerUnit());
        holder.textView_for_saleDetail_value.setText(Double.toString(currentStock.getPrimaryQuant()));


    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }


    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
        notifyDataSetChanged();
    }


    class ItemHolder extends RecyclerView.ViewHolder {

        private TextView textView_for_saleDetail_id;
        private TextView textView_for_saleDetail_name;
        private TextView textView_for_saleDetail_price;
        private TextView textView_for_saleDetail_qty;
        private TextView textView_for_saleDetail_value;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            textView_for_saleDetail_id = itemView.findViewById(R.id.text_view_sale_detail_id);
            textView_for_saleDetail_name = itemView.findViewById(R.id.text_view_sale_detail_name);
            textView_for_saleDetail_price = itemView.findViewById(R.id.text_view_sale_detail_price);
            textView_for_saleDetail_qty = itemView.findViewById(R.id.text_view_sale_detail_qty);
            textView_for_saleDetail_value = itemView.findViewById(R.id.text_view_sale_detail_value);


        }
    }
}