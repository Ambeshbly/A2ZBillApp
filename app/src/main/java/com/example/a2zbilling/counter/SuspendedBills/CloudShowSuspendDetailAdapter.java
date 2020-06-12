package com.example.a2zbilling.counter.SuspendedBills;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.SuspendDetail;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CloudShowSuspendDetailAdapter extends FirestoreRecyclerAdapter<SuspendDetail, CloudShowSuspendDetailAdapter.CloudStockViewHolder>  {


    private Context context;


    public CloudShowSuspendDetailAdapter(@NonNull FirestoreRecyclerOptions<SuspendDetail> options, Context context) {
        super(options);
        this.context=context;

    }

    @Override
    protected void onBindViewHolder(@NonNull final CloudStockViewHolder holder, int position, @NonNull final SuspendDetail
            model) {
        holder.textView_for_saleDetail_price.setText(model.getSalingPrice());
        holder.textView_for_saleDetail_name.setText(model.getItemName());
        holder.textView_for_saleDetail_qty.setText(Double.toString(model.getQnty()));
        String values = Double.toString(Double.parseDouble(model.getSalingPrice()) * model.getQnty());
        holder.textView_for_saleDetail_value.setText(values);


    }

    @NonNull
    @Override
    public CloudStockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_for_counter_price_qnty_value,parent,false);
        return new CloudStockViewHolder(view);
    }

    public class CloudStockViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_for_saleDetail_price;
        private TextView textView_for_saleDetail_qty;
        private TextView textView_for_saleDetail_value;
        private TextView textView_for_saleDetail_name;



        public CloudStockViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_for_saleDetail_price = itemView.findViewById(R.id.textView_for_counter_price);
            textView_for_saleDetail_qty = itemView.findViewById(R.id.textView_counter_qnty);
            textView_for_saleDetail_value = itemView.findViewById(R.id.TextView_values);
            textView_for_saleDetail_name = itemView.findViewById(R.id.textView_for_counter_itemName);
        }
    }
}
