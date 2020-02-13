package com.example.a2zbilling.counter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.db.entities.Stock;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CounterFragment extends Fragment {

    //Floatin action button declaration which used to change activity from mainActicity to SellingStocksActivity activity
    FloatingActionButton floatingActionButton;

    //cardView declarations both waitList and BillHistoryActivity
    CardView cardView_waitList,cardView_conformList;


       public static final int ADD_NEW_STOCK_REQ_CODE = 1;
       RecyclerView recyclerView;
       CounterAdapter adepter;
       private TextView textViewTotal;

       private MainActivityViewModel addToCartActivityViewModel;

       public CounterFragment(MainActivityViewModel addToCartActivityViewModel) {
       this.addToCartActivityViewModel = addToCartActivityViewModel;
      }

    //onCreateView Override method
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view=inflater.inflate(R.layout.fragment_counter,container,false);

        textViewTotal=view.findViewById(R.id.textView_counter_total);

        recyclerView = view.findViewById(R.id.recyclerView_for_counter_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adepter = new CounterAdapter();
        recyclerView.setAdapter(adepter);


        return view;

    }

    @Override
   public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      super.onActivityResult(requestCode, resultCode, data);       if (requestCode == ADD_NEW_STOCK_REQ_CODE) {
          Stock stock = (Stock) data.getSerializableExtra("stock");
        //   String itemName = stock.getItemName();
           addToCartActivityViewModel.addNewlyAddedStock(stock);


            addToCartActivityViewModel.getNewlyAddedStocks().observe(getViewLifecycleOwner(), new Observer<ArrayList<Stock>>() {
                @Override
                public void onChanged(ArrayList<Stock> stocks) {
                    adepter.setItems(stocks);
                }
            });

            ArrayList<Stock> stockList= addToCartActivityViewModel.getTemproryItemList();
            int total=0;

            for(int i = 0; i <stockList.size(); i++)
            {
                Stock stock2=stockList.get(i);
                int value=0;
                value=  Integer.parseInt(stock2.getItemQuentity())* Integer.parseInt(stock2.getItemSalePerUnit());
                total=total+value;

            }
            String totalString=Integer.toString(total);
            textViewTotal.setText(totalString);

      }

   }

    //onStart override method which is used to add action listener in flotating button or both CardView
    @Override
    public void onStart() {

        //finding Cardview waitList in Xml file
        cardView_waitList=getView().findViewById(R.id.waitlistcardview);

        //finding Cardview conformList in Xml file
        cardView_conformList=getView().findViewById(R.id.conformListcardview);

        //finding Floating button  in Xml file

        super.onStart();

        floatingActionButton=getView().findViewById(R.id.bt_float);
        //Floating Button add action listener
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SellingStocksActivity.class);
                 startActivityForResult(intent,ADD_NEW_STOCK_REQ_CODE);
               // startActivity(intent);
            }
        });

        //Cardview waitList add action Listener
        cardView_waitList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SuspendedTransactionListActivity.class);
                startActivity(intent);
            }
        });

        //cardView conformList Add action listener
        cardView_conformList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), BillHistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
