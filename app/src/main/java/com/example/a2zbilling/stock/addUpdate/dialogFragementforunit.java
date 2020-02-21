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
import com.example.a2zbilling.db.entities.Stock;

public class dialogFragementforunit extends AppCompatDialogFragment {

    ActivityAddItemFloatingButtonBinding activityAddItemFloatingButtonBinding;
    AddUpdateStockActivityViewModel addUpdateStockActivityViewModel;
    private CardView cardViewKg, cardViewGm, cardViewMg, cardViewMtr, cardViewCm, cardViewMm, cardViewLtr, cardViewml, cardViewBox,  cardViewPc;

    public dialogFragementforunit(ActivityAddItemFloatingButtonBinding activityAddItemFloatingButtonBinding,AddUpdateStockActivityViewModel addUpdateStockActivityViewModel) {
        this.activityAddItemFloatingButtonBinding = activityAddItemFloatingButtonBinding;
        this.addUpdateStockActivityViewModel=addUpdateStockActivityViewModel;
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
        cardViewBox = view.findViewById(R.id.cardview_Box);
        cardViewPc = view.findViewById(R.id.cardview_Pc);

        cardViewKg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "kg clicked", Toast.LENGTH_SHORT).show();
                addUpdateStockActivityViewModel.setButtonText("kg");
                activityAddItemFloatingButtonBinding.getStock().setItemUnit("10");
                activityAddItemFloatingButtonBinding.getStock().setPc("1");
                cardViewKg.setCardBackgroundColor(Color.GREEN);
                dismiss();

            }
        });

        cardViewGm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Gm clicked", Toast.LENGTH_SHORT).show();
                addUpdateStockActivityViewModel.setButtonText("Gm");
                activityAddItemFloatingButtonBinding.getStock().setItemUnit("11");
                activityAddItemFloatingButtonBinding.getStock().setPc("1");
                cardViewGm.setCardBackgroundColor(Color.GREEN);
                dismiss();

            }
        });

        cardViewMg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "mg clicked", Toast.LENGTH_SHORT).show();
                addUpdateStockActivityViewModel.setButtonText("Mg");
                activityAddItemFloatingButtonBinding.getStock().setItemUnit("12");
                activityAddItemFloatingButtonBinding.getStock().setPc("1");
                cardViewMg.setCardBackgroundColor(Color.GREEN);
                dismiss();

            }
        });


        cardViewMtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Mtr clicked", Toast.LENGTH_SHORT).show();
                addUpdateStockActivityViewModel.setButtonText("Mtr");
                activityAddItemFloatingButtonBinding.getStock().setItemUnit("3");
                activityAddItemFloatingButtonBinding.getStock().setPc("1");
                cardViewKg.setCardBackgroundColor(Color.GREEN);
                dismiss();

            }
        });

        cardViewCm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Cm clicked", Toast.LENGTH_SHORT).show();
                addUpdateStockActivityViewModel.setButtonText("Cm");
                activityAddItemFloatingButtonBinding.getStock().setItemUnit("4");
                activityAddItemFloatingButtonBinding.getStock().setPc("1");
                cardViewCm.setCardBackgroundColor(Color.GREEN);
                dismiss();

            }
        });

        cardViewMm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Mm clicked", Toast.LENGTH_SHORT).show();
                addUpdateStockActivityViewModel.setButtonText("Mm");
                activityAddItemFloatingButtonBinding.getStock().setItemUnit("5");
                activityAddItemFloatingButtonBinding.getStock().setPc("1");
                cardViewMm.setCardBackgroundColor(Color.GREEN);
                dismiss();

            }
        });


        cardViewLtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ltr clicked", Toast.LENGTH_SHORT).show();
                addUpdateStockActivityViewModel.setButtonText("Ltr");
                activityAddItemFloatingButtonBinding.getStock().setItemUnit("6");
                activityAddItemFloatingButtonBinding.getStock().setPc("1");
                cardViewLtr.setCardBackgroundColor(Color.GREEN);
                dismiss();

            }
        });

        cardViewml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "ml clicked", Toast.LENGTH_SHORT).show();
                addUpdateStockActivityViewModel.setButtonText("ml");
                activityAddItemFloatingButtonBinding.getStock().setItemUnit("7");
                activityAddItemFloatingButtonBinding.getStock().setPc("1");
                cardViewml.setCardBackgroundColor(Color.GREEN);
                dismiss();

            }
        });



        cardViewBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Box clicked", Toast.LENGTH_SHORT).show();
                addUpdateStockActivityViewModel.setButtonText("Box");
                cardViewBox.setCardBackgroundColor(Color.GREEN);
                BoxDialogFragment ialogFragement = new BoxDialogFragment(activityAddItemFloatingButtonBinding);
                ialogFragement.show(getChildFragmentManager(), "exampledialog");

            }
        });

        cardViewPc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Pc clicked", Toast.LENGTH_SHORT).show();
                addUpdateStockActivityViewModel.setButtonText("Pc");
                activityAddItemFloatingButtonBinding.getStock().setItemUnit("9");
                activityAddItemFloatingButtonBinding.getStock().setPc("1");
                cardViewPc.setCardBackgroundColor(Color.GREEN);
                dismiss();
            }
        });


        return builder.create();
    }

}
