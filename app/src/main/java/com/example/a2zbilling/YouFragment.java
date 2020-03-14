package com.example.a2zbilling;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.a2zbilling.support.SupportActivity;

public class YouFragment extends Fragment {

    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_me, container, false);
        CardView shopSelling=view.findViewById(R.id.shopsetting);
        shopSelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ShopSettingActivity.class);
                startActivity(intent);
            }
        });
        CardView helpAndSupport=view.findViewById(R.id.help_and_support);
        helpAndSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SupportActivity.class);
                startActivity(intent);
            }
        });
        CardView aboutUs=view.findViewById(R.id.about_us);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4=new Intent(Intent.ACTION_VIEW, Uri.parse("https://a2zbill.blogspot.com/"));
                startActivity(intent4);
            }
        });
        return view;
    }
}
