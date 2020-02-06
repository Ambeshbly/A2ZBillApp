package com.example.a2zbilling.stock;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.tables.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class  AdepterForAvailableItems extends RecyclerView.Adapter<AdepterForAvailableItems.ItemHolder> implements Filterable {
    private List<Items> items=new ArrayList<>();

    private List<Items> itemsList;


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
        holder.textViewForQuentity.setText(currentItem.getItemQuentity());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Items> items){
        this.items=items;

        itemsList=new ArrayList<>(items);

     notifyDataSetChanged();
    }



    class ItemHolder extends RecyclerView.ViewHolder{

        private TextView textViewForItemName;
        private TextView textViewForItemId;
        private TextView textViewForSalePrice;
        private TextView textViewForShowIdText;
        private TextView textViewForShowSaleText;
        private TextView textViewForQuentity;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            textViewForItemName=itemView.findViewById(R.id.text_view_item_name);
            textViewForItemId=itemView.findViewById(R.id.text_view_show_id);
            textViewForShowIdText=itemView.findViewById(R.id.text_view_show_id_text);
            textViewForShowSaleText=itemView.findViewById(R.id.text_view_sale_price_text);
            textViewForQuentity=itemView.findViewById(R.id.text_view_quentity);
        }
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
         List<Items> filteredlist=new ArrayList<>();
         if(constraint==null || constraint.length()==0){
             filteredlist.addAll(itemsList);
         }else {
             String filterPatten=constraint.toString().toLowerCase().trim();
             for (Items item: itemsList){
                 if(item.getItemName().toLowerCase().contains(filterPatten)  ){

                     filteredlist.add(item);
                 }
             }
         }

         FilterResults results=new FilterResults();
         results.values=filteredlist;
         return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items.clear();
            items.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

}