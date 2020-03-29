package com.example.a2zbilling.counter.BillList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.db.entities.SaleDeatial;

import java.util.ArrayList;
import java.util.List;

public class ShowBillingHistoryFragmentAdapterForStock extends RecyclerView.Adapter<ShowBillingHistoryFragmentAdapterForStock.ItemHolder> {
    private List<SaleDeatial> saleDeatials = new ArrayList<>();


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_for_counter_stock, parent, false);
        ItemHolder itemHolder = new ItemHolder(itemView);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        SaleDeatial currentsalesDetail = saleDeatials.get(position);
        int i = 1;
      //  holder.textView_for_saleDetail_id.setText("" + currentsalesDetail.getSaleDetailId());
        holder.textView_for_saleDetail_name.setText(currentsalesDetail.getSaleDetailItemName());
    }

    @Override
    public int getItemCount() {
        return saleDeatials.size();
    }


    public void setSaleDeatials(List<SaleDeatial> saleDeatials) {
        this.saleDeatials = saleDeatials;
        notifyDataSetChanged();
    }


    class ItemHolder extends RecyclerView.ViewHolder {

        private TextView textView_for_saleDetail_name;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);


            textView_for_saleDetail_name = itemView.findViewById(R.id.textView_for_counter_itemName);



        }
    }
}