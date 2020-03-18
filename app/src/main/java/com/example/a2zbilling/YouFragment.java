package com.example.a2zbilling;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.a2zbilling.customer.AllCustomer.AllCustomerFragment;
import com.example.a2zbilling.customer.CustomerActivity;
import com.example.a2zbilling.customer.CustomerActivityViewModel;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.support.SupportActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class YouFragment extends Fragment {

    private BottomSheetBehavior bottomSheetBehavior;
    private CardView cardView;
    private YouViewModel youViewModel;
    private int noOfCustomer;
    private TextView allCustomer;

    //override method onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_me, container, false);
        loadLoacal();
        View scrol=view.findViewById(R.id.scrollviewhai);
        bottomSheetBehavior=BottomSheetBehavior.from(scrol);
        cardView=view.findViewById(R.id.allCustomerCard);
        allCustomer=view.findViewById(R.id.allCustomer);
        youViewModel = ViewModelProviders.of(this).get(YouViewModel.class);
        youViewModel.getAllCustomer().observe(getViewLifecycleOwner(), new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
               noOfCustomer= customers.size();
               allCustomer.setText(""+noOfCustomer);
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), CustomerActivity.class);
                startActivity(intent);
            }
        });

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
        CardView language=view.findViewById(R.id.shopLanguage);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanuageDialog();
            }
        });

        return view;
    }

    private void showChangeLanuageDialog() {
        final String[] listItem={"English","Hindi"};
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(getContext());
        mBuilder.setTitle("Chose the Lanuguage");
        mBuilder.setSingleChoiceItems(listItem, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    setLocale("en");
                    getActivity().recreate();
                }

                else if(which==1){
                    setLocale("hi");
                    getActivity().recreate();
                }
                dialog.dismiss();
            }
        });
        AlertDialog myDialog=mBuilder.create();
        myDialog.show();

    }
    public void setLocale(String lang) {
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration=new Configuration();
        configuration.locale=locale;
        getContext().getResources().updateConfiguration(configuration,getContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor=getActivity().getSharedPreferences("setting",MODE_PRIVATE).edit();
        //editor.putString("my_lang",lang);
        editor.putString("my_lang",lang);
        editor.apply();
    }

    public void loadLoacal(){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("setting", MODE_PRIVATE);
        String language=sharedPreferences.getString("my_lang","");
        setLocale(language);
    }


}
