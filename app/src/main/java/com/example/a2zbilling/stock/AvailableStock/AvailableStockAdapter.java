package com.example.a2zbilling.stock.AvailableStock;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.customer.AddUpdateCustomerFragment;
import com.example.a2zbilling.customer.CustomerActivity;
import com.example.a2zbilling.customer.ShowCustomerDetailDialogFragment;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.addUpdate.AddUpdateStockActivity;

import java.util.ArrayList;
import java.util.List;

public class AvailableStockAdapter extends RecyclerView.Adapter<AvailableStockAdapter.ItemHolder> implements Filterable {
    private List<Stock> items = new ArrayList<>();
    private List<Stock> stockList;
    private OnItemRecyclerViewListener listener;
    Context context;

    public AvailableStockAdapter(Context context) {
        this.context = context;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Stock> filteredlist = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredlist.addAll(stockList);
            } else {
                String filterPatten = constraint.toString().toLowerCase().trim();
                for (Stock item : stockList) {
                    if (item.getItemName().toLowerCase().contains(filterPatten)||item.getBarCode().toLowerCase().contains(filterPatten)) {

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_for_addstock, parent, false);
        ItemHolder itemHolder = new ItemHolder(itemView);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, int position) {
        final Stock currentItem = items.get(position);
        holder.textViewForItemName.setText(currentItem.getItemName());
        holder.textViewForItemId.setText("" + currentItem.getItemId());
        holder.textViewForQuentity.setText("" + currentItem.getItemQuentity());
        holder.textViewForShowSaleText.setText(currentItem.getItemUnit());
        holder.Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(context,holder.Menu);
                popupMenu.inflate(R.menu.menu_for_update_stock);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.update:
                                Toast.makeText(context,"update",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, AddUpdateStockActivity.class);
                                intent.putExtra("stock_object", currentItem);
                                context.startActivity(intent);
                                break;
                            case R.id.report:
                                Toast.makeText(context,"Cooming Soon",Toast.LENGTH_SHORT).show();
                                break;

                        }
                        return false;
                    }

                });
                popupMenu.show();
            }
        });

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

    public void setOnItemRecyclerViewlistener(OnItemRecyclerViewListener listener) {
        this.listener = listener;

    }


    public interface OnItemRecyclerViewListener {
        public void onItemClick(Stock stock);
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        private TextView textViewForItemName;
        private TextView textViewForItemId;
        private TextView textViewForSalePrice;
        private TextView textViewForShowIdText;
        private TextView textViewForShowSaleText;
        private TextView textViewForQuentity;
        private ImageView imageViewForItemImage;
        private TextView Menu;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            textViewForItemName = itemView.findViewById(R.id.text_view_item_name);
            textViewForItemId = itemView.findViewById(R.id.text_view_show_id);
            textViewForShowIdText = itemView.findViewById(R.id.text_view_show_id_text);
            textViewForShowSaleText = itemView.findViewById(R.id.text_view_sale_price_text);
            textViewForQuentity = itemView.findViewById(R.id.text_view_quentity);
            imageViewForItemImage = itemView.findViewById(R.id.imageview_for_item);
            Menu=itemView.findViewById(R.id.menu_in_recyclerview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(items.get(position));
                    }
                }
            });
        }
    }
}