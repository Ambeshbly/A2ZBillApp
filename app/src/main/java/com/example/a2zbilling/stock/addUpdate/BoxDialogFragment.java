package com.example.a2zbilling.stock.addUpdate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;

import com.example.a2zbilling.R;
import com.example.a2zbilling.db.entities.Stock;

import java.util.ArrayList;
import java.util.List;

public class BoxDialogFragment extends AppCompatDialogFragment {
    CardView cardviewCancel, cardViewSave;
    EditText editTextBox;
    Stock stock;
    private Spinner spinner;


    public BoxDialogFragment(Stock stock) {
        this.stock = stock;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_for_box, null);
        cardviewCancel = view.findViewById(R.id.cardview_cancel);
        cardViewSave = view.findViewById(R.id.cardview_save);
        spinner = view.findViewById(R.id.box_sppiner);
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
                stock.setPriamryUnit(Unit.UNIT_BOX);
                int pic = Integer.parseInt(editTextBox.getText().toString().trim());
                stock.setPrimaryQuant(stock.getPrimaryQuant() * pic);
                stock.setSecondUnit(editTextBox.getText().toString().trim());
                dismiss();
            }
        });

        List<String> list = new ArrayList<String>();
        list.add("Pc");
        list.add("Kg");
        list.add("Ltr");
        list.add("Mtr");
        ArrayAdapter<String> arrayAdapter = null;
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
                switch (position){
                    case 0:
                      //handle pc
                        break;
                    case 1:
                        //handle Kg
                        break;
                    case 2:
                        //handle Ltr
                        break;
                    case 3:
                        //handle Mtr
                        break;
                    default:
                        //handle default
                       break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });





        builder.setView(view);
        return builder.create();
    }
}
