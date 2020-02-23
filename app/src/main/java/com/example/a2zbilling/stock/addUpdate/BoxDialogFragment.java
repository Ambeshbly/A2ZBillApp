package com.example.a2zbilling.stock.addUpdate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.databinding.ActivityAddItemFloatingButtonBinding;
import com.example.a2zbilling.db.entities.Stock;

public class BoxDialogFragment extends AppCompatDialogFragment {
    CardView cardviewCancel, cardViewSave;
    EditText editTextBox;
    ActivityAddItemFloatingButtonBinding activityAddItemFloatingButtonBinding;


    public BoxDialogFragment(ActivityAddItemFloatingButtonBinding activityAddItemFloatingButtonBinding) {
        this.activityAddItemFloatingButtonBinding = activityAddItemFloatingButtonBinding;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_for_box, null);
        cardviewCancel = view.findViewById(R.id.cardview_cancel);
        cardViewSave = view.findViewById(R.id.cardview_save);
        editTextBox = view.findViewById(R.id.edit_box);
        cardviewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        cardViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "save", Toast.LENGTH_SHORT).show();
                activityAddItemFloatingButtonBinding.getStock().setItemUnit("8");
                Stock stock = activityAddItemFloatingButtonBinding.getStock();
                int pic = Integer.parseInt(editTextBox.getText().toString().trim());
                stock.setItemQuentity(stock.getItemQuentity() * pic);
                stock.setPc(editTextBox.getText().toString().trim());
                dismiss();
            }
        });
        builder.setView(view);
        return builder.create();
    }
}
