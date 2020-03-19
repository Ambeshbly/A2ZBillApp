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

public class ShowBillingHistoryFragmentAdapter extends RecyclerView.Adapter<ShowBillingHistoryFragmentAdapter.ItemHolder> {
    private List<SaleDeatial> saleDeatials = new ArrayList<>();


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_viee_for_show_sales_detail_history, parent, false);
        ItemHolder itemHolder = new ItemHolder(itemView);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        SaleDeatial currentsalesDetail = saleDeatials.get(position);
        int i = 1;
      //  holder.textView_for_saleDetail_id.setText("" + currentsalesDetail.getSaleDetailId());
        holder.textView_for_saleDetail_name.setText(currentsalesDetail.getSaleDetailItemName());
        holder.textView_for_saleDetail_price.setText(currentsalesDetail.getSalingPrice());
        holder.textView_for_saleDetail_qty.setText(Double.toString(currentsalesDetail.getQuntity()));
        String values = Double.toString(Integer.parseInt(currentsalesDetail.getSalingPrice()) * currentsalesDetail.getQuntity());
        holder.textView_for_saleDetail_value.setText(values);


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

        private TextView textView_for_saleDetail_id;
        private TextView textView_for_saleDetail_name;
        private TextView textView_for_saleDetail_price;
        private TextView textView_for_saleDetail_qty;
        private TextView textView_for_saleDetail_value;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);

         //   textView_for_saleDetail_id = itemView.findViewById(R.id.text_view_sale_detail_id);
            textView_for_saleDetail_name = itemView.findViewById(R.id.text_view_sale_detail_name);
            textView_for_saleDetail_price = itemView.findViewById(R.id.text_view_sale_detail_price);
            textView_for_saleDetail_qty = itemView.findViewById(R.id.text_view_sale_detail_qty);
            textView_for_saleDetail_value = itemView.findViewById(R.id.text_view_sale_detail_value);


        }
    }
}