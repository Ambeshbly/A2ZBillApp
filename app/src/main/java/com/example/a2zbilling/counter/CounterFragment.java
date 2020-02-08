package com.example.a2zbilling.counter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.a2zbilling.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CounterFragment extends Fragment {

    //Floatin action button declaration which used to change activity from mainActicity to SellingStocksActivity activity
    FloatingActionButton floatingActionButton;

    //cardView declarations both waitList and BillHistoryActivity
    CardView cardView_waitList,cardView_conformList;

    //onCreateView Override method
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_counter,container,false);

    }

    //onStart override method which is used to add action listener in flotating button or both CardView
    @Override
    public void onStart() {

        //finding Cardview waitList in Xml file
        cardView_waitList=getView().findViewById(R.id.waitlistcardview);

        //finding Cardview conformList in Xml file
        cardView_conformList=getView().findViewById(R.id.conformListcardview);

        //finding Floating button  in Xml file
        floatingActionButton=getView().findViewById(R.id.bt_float);
        super.onStart();

        //Floating Button add action listener
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SellingStocksActivity.class);
                startActivity(intent);
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
