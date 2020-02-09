package com.example.a2zbilling.stock.AvailableStock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.PrimaryKey;

import com.example.a2zbilling.R;
import com.example.a2zbilling.db.entities.Stock;

import java.util.ArrayList;
import java.util.List;

public class AvailableStockAdapter extends RecyclerView.Adapter<AvailableStockAdapter.ItemHolder> implements Filterable {
    private List<Stock> items = new ArrayList<>();

    private List<Stock> stockList;
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Stock> filteredlist = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredlist.addAll(stockList);
            } else {
                String filterPatten = constraint.toString().toLowerCase().trim();
                for (Stock item : stockList) {
                    if (item.getItemName().toLowerCase().contains(filterPatten)) {

                        filteredlist.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items.clear();
            items.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_viee_for_available_item, parent, false);
        ItemHolder itemHolder = new ItemHolder(itemView);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Stock currentItem = items.get(position);
        holder.textViewForItemName.setText(currentItem.getItemName());
        holder.textViewForItemId.setText("" + currentItem.getItemId());
        holder.textViewForQuentity.setText(""+currentItem.getItemQuentity());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Stock> items) {
        this.items = items;

        stockList = new ArrayList<>(items);

        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        private TextView textViewForItemName;
        private TextView textViewForItemId;
        private TextView textViewForSalePrice;
        private TextView textViewForShowIdText;
        private TextView textViewForShowSaleText;
        private TextView textViewForQuentity;
        private ImageView imageViewForItemImage;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            textViewForItemName = itemView.findViewById(R.id.text_view_item_name);
            textViewForItemId = itemView.findViewById(R.id.text_view_show_id);
            textViewForShowIdText = itemView.findViewById(R.id.text_view_show_id_text);
            textViewForShowSaleText = itemView.findViewById(R.id.text_view_sale_price_text);
            textViewForQuentity = itemView.findViewById(R.id.text_view_quentity);
            imageViewForItemImage=itemView.findViewById(R.id.imageview_for_item);
        }
    }

}