package com.example.a2zbilling;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.a2zbilling.customer.CustomerActivity;
import com.example.a2zbilling.stock.StockActivity;
import com.example.a2zbilling.support.SupportActivity;

public class Dashboard extends Fragment  {

    MediaPlayer mediaPlayer;

    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    //billingcounter_cardview
    //override method onStart because add action listener in the Add item cardview
    @Override
    public void onStart() {
        super.onStart();

        mediaPlayer= MediaPlayer.create(getContext(),R.raw.simple);


        //finding stock cardView in the Xml file
        CardView stocksCardView=(CardView)getView().findViewById(R.id.stocks_cardview);

        //finding customer cardview in the xml file
        CardView customerCardView=(CardView)getView().findViewById(R.id.customer_cardview);

        //finding customer cardview in the xml file
        CardView supportCardView=(CardView)getView().findViewById(R.id.support_cardview);

        //finding customer cardview in the xml file
        CardView profiteAndlossCardView=(CardView)getView().findViewById(R.id.billingcounter_cardview);

        //finding customer cardview in the xml file
        CardView expensesCardView=(CardView)getView().findViewById(R.id.expenses_cardview);


        //add action listener in the stock cardview
        stocksCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent=new Intent(getContext(), StockActivity.class);
                startActivity(intent);
            }
        });


        //add action listener in the customer cardview
        customerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent=new Intent(getContext(), CustomerActivity.class);
                startActivity(intent);
            }
        });

        supportCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent=new Intent(getContext(),SupportActivity.class);
             startActivity(intent);
            }
        });


        profiteAndlossCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getContext(),ProfitAndLossActivity.class);
//                startActivity(intent);
                Intent intent=new Intent(getContext(),Test.class);
                startActivity(intent);


            }
        });


        expensesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ExpensesActivity.class);
                startActivity(intent);
            }
        });
    }


}