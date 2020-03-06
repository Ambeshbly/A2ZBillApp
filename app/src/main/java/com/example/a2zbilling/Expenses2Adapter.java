package com.example.a2zbilling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.customer.CustomerActivity;
import com.example.a2zbilling.customer.ShowCustomerDetailDialogFragment;
import com.example.a2zbilling.db.entities.Expenses;
import com.example.a2zbilling.db.entities.ExpensesCategory;

import java.util.ArrayList;
import java.util.List;

public class Expenses2Adapter extends RecyclerView.Adapter<Expenses2Adapter.ItemHolder> {
    private List<ExpensesCategory> expensesCategories = new ArrayList<>();
    Context context;
    private ExpensesActivity2ViewModel expensesActivity2ViewModel;

    public Expenses2Adapter(Context context, ExpensesActivity2ViewModel expensesActivity2ViewModel) {
        this.context = context;
        this.expensesActivity2ViewModel=expensesActivity2ViewModel;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_for_expenses2, parent, false);
        ItemHolder itemHolder = new ItemHolder(itemView);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        ExpensesCategory currentexpensesCategory = expensesCategories.get(position);
        holder.textViewForTotal.setText(currentexpensesCategory.getExpenseCategoryTotal()+"\u20B9");
        holder.textViewForPaymentMode.setText("payment Mode: "+ currentexpensesCategory.getExpenseCategoryPaymentMode());
        holder.textViewDate.setText("Date : "+currentexpensesCategory.getDate());
        expensesActivity2ViewModel.setExpensesCategory(currentexpensesCategory);
        holder.cardViewEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowExpensesDialogFragment dialogFragementforunit=new ShowExpensesDialogFragment(expensesActivity2ViewModel,1);
                dialogFragementforunit.show(((ExpensesActivity2)context).getSupportFragmentManager(),"exampledialog");
            }
        });
    }

    @Override
    public int getItemCount() {
        return expensesCategories.size();
    }

      public void setExpensesCategories(List<ExpensesCategory> expensesCategories) {
         this.expensesCategories = expensesCategories;
         notifyDataSetChanged();
      }

    class ItemHolder extends RecyclerView.ViewHolder {
        private TextView textViewForExpensesCategory, textViewForTotal, textViewForPaymentMode,textViewDate;
        private CardView cardViewEye;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            textViewForTotal = itemView.findViewById(R.id.text_expenses1_total);
            textViewForPaymentMode = itemView.findViewById(R.id.text_expeses1_payment_mode);
            cardViewEye=itemView.findViewById(R.id.recycler_view_cardview_eye);
            textViewDate=itemView.findViewById(R.id.text_date2);
        }
    }
}