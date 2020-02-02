package com.example.a2zbilling.stock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.tables.Items;

import java.util.ArrayList;
import java.util.List;

public class AdepterForAvailableItems extends RecyclerView.Adapter<AdepterForAvailableItems.ItemHolder> {
    private List<Items> items=new ArrayList<>();


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_viee_for_available_item,parent,false);
        ItemHolder itemHolder=new ItemHolder(itemView);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Items currentItem=items.get(position);
        holder.textViewForItemName.setText(currentItem.getItemName());
        holder.textViewForItemId.setText(""+currentItem.getItemId());
        holder.textViewForSalePrice.setText(""+currentItem.getItemSalePerUnit());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Items> items){
        this.items=items;
     notifyDataSetChanged();
    }

    class ItemHolder extends RecyclerView.ViewHolder{

        private TextView textViewForItemName;
        private TextView textViewForItemId;
        private TextView textViewForSalePrice;
        private TextView textViewForShowIdText;
        private TextView textViewForShowSaleText;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            textViewForItemName=itemView.findViewById(R.id.text_view_item_name);
            textViewForItemId=itemView.findViewById(R.id.text_view_show_id);
            textViewForSalePrice=itemView.findViewById(R.id.text_view_sale_price);
            textViewForShowIdText=itemView.findViewById(R.id.text_view_show_id_text);
            textViewForShowSaleText=itemView.findViewById(R.id.text_view_sale_price_text);
        }
    }
}
