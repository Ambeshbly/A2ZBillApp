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
    View unitView;


    public BoxDialogFragment(Stock stock, View view) {
        this.stock = stock;
        this.unitView = view;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_fragment_for_box, null);
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
                stock.setPriamryUnit(Unit.UNIT_BOX);
                int pPB = Integer.parseInt(editTextBox.getText().toString().trim());
                stock.setPcPerBox(pPB);
                stock.updateSecondQuant(pPB * stock.getPrimaryQuant());
                dismiss();
            }
        });

        List<String> list = new ArrayList<String>();
        list.add(Unit.UNIT_PC);
        list.add(Unit.UNIT_KG);
        list.add(Unit.UNIT_LTR);
        list.add(Unit.UNIT_MTR);
        ArrayAdapter<String> arrayAdapter = null;
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
                String secondUnit = (String) parent.getAdapter().getItem(position);

                stock.setSecondUnit(secondUnit);
                /*switch (secondUnit){
                    case Unit.UNIT_PC:
                      //handle pc
                        break;
                    case Unit.UNIT_KG:
                        //handle Kg
                        break;
                    case Unit.UNIT_LTR:
                        //handle Ltr
                        break;
                    case Unit.UNIT_MTR:
                        //handle Mtr
                        break;
                    default:
                        //handle default
                       break;
                }*/
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        builder.setView(view);
        return builder.create();
    }
}
