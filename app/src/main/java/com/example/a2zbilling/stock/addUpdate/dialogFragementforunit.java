package com.example.a2zbilling.stock.addUpdate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.databinding.ActivityAddItemFloatingButtonBinding;

public class dialogFragementforunit extends AppCompatDialogFragment {

    ActivityAddItemFloatingButtonBinding activityAddItemFloatingButtonBinding;
    private CardView cardViewKg, cardViewGm, cardViewMg, cardViewMtr, cardViewCm, cardViewMm, cardViewLtr, cardViewml, cardViewDz, cardViewHdz, cardViewPc;

    public dialogFragementforunit(ActivityAddItemFloatingButtonBinding activityAddItemFloatingButtonBinding) {
        this.activityAddItemFloatingButtonBinding = activityAddItemFloatingButtonBinding;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.my_custom_dialogfragments, null);
        builder.setView(view);

        cardViewKg = view.findViewById(R.id.cardView_kg);
        cardViewGm = view.findViewById(R.id.cardview_Gm);
        cardViewMg = view.findViewById(R.id.cardview_Mg);
        cardViewMtr = view.findViewById(R.id.cardview_Mtr);
        cardViewCm = view.findViewById(R.id.cardview_Cm);
        cardViewMm = view.findViewById(R.id.cardview_Mm);
        cardViewLtr = view.findViewById(R.id.cardview_ltr);
        cardViewml = view.findViewById(R.id.cardview_Ml);
        cardViewDz = view.findViewById(R.id.cardview_Dz);
        cardViewHdz = view.findViewById(R.id.cardview_Hdz);
        cardViewPc = view.findViewById(R.id.cardview_pc);

        cardViewKg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "kg clicked", Toast.LENGTH_SHORT).show();
                activityAddItemFloatingButtonBinding.getStock().setItemUnit("1000");
                cardViewKg.setCardBackgroundColor(Color.GREEN);

            }
        });

        cardViewGm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Gm clicked", Toast.LENGTH_SHORT).show();
                activityAddItemFloatingButtonBinding.getStock().setItemUnit("1");
                cardViewGm.setCardBackgroundColor(Color.GREEN);

            }
        });

        cardViewMg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "mg clicked", Toast.LENGTH_SHORT).show();
                activityAddItemFloatingButtonBinding.getStock().setItemUnit(".001");
                cardViewMg.setCardBackgroundColor(Color.GREEN);

            }
        });

        return builder.create();
    }

}
