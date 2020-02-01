package com.example.a2zbilling;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class Dashboard extends Fragment  {

    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    //override method onStart because add action listener in the Add item cardview
    @Override
    public void onStart() {
        super.onStart();

        //finding stock cardView in the Xml file
        CardView stocksCardView=(CardView)getView().findViewById(R.id.stocks_cardview);

        //finding customer cardview in the xml file
        CardView customerCardView=(CardView)getView().findViewById(R.id.customer_cardview);

        //add action listener in the stock cardview
        stocksCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),AddItem.class);
                startActivity(intent);
            }
        });


        //add action listener in the customer cardview
        customerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Customer.class);
                startActivity(intent);
            }
        });
    }


}