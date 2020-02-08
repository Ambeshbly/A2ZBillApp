package com.example.a2zbilling.customer.AllCustomer;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;

public class AllCustomerAdapter extends RecyclerView.Adapter<AllCustomerAdapter.DeptHolder> {

    String data1[]={"rahul","satendra","Ambesh", "Nishi"};
    String data2[]={"102","103","104", "105"};
    String data3[]={"+11","-50","0", "-10"};
    @NonNull
    @Override
    public DeptHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_for_all_customer,parent,false);
        DeptHolder dh=new DeptHolder(itemView);
        return dh;
    }

    @Override
    public void onBindViewHolder(@NonNull DeptHolder holder, int position) {

        holder.textViewForCustomerName.setText(data1[position]);
        holder.textViewForCoustomerId.setText(data2[position]);
        if(Integer.parseInt(data3[position])<0){
            holder.textViewForCustomerdebt.setTextColor(Color.parseColor("#FF0000"));
            holder.textViewForCustomerdebt.setText(data3[position]);
        }
        else{
            holder.textViewForCustomerdebt.setTextColor(Color.parseColor("#32CD32"));
            holder.textViewForCustomerdebt.setText(data3[position]);
        }
        holder.text_id.setText("Id : ");
        holder.text_debt.setText("Debt.");

    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    class DeptHolder extends RecyclerView.ViewHolder{
        private TextView textViewForCustomerName;
        private TextView textViewForCoustomerId;
        private TextView textViewForCustomerdebt;
        private TextView text_id;
        private TextView text_debt;
        private CardView cardview_update;
        private CardView cardView_detail;

        public DeptHolder(@NonNull View itemView) {
            super(itemView);
            textViewForCustomerName=itemView.findViewById(R.id.text_view_customer_name);
            textViewForCoustomerId=itemView.findViewById(R.id.text_view_customer_id);
            textViewForCustomerdebt=itemView.findViewById(R.id.text_view_customer_debt);
            text_id=itemView.findViewById(R.id.text_id);
            text_debt=itemView.findViewById(R.id.text_debt);
            cardview_update=itemView.findViewById(R.id.card_view_update);
            cardView_detail=itemView.findViewById(R.id.card_view_detail);

        }
    }
}
