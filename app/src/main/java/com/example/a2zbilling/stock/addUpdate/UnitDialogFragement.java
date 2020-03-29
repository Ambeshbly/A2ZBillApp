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
import androidx.databinding.DataBindingUtil;

import com.example.a2zbilling.R;
import com.example.a2zbilling.databinding.MyCustomDialogfragmentsBinding;
import com.example.a2zbilling.db.entities.Stock;

public class UnitDialogFragement extends AppCompatDialogFragment {

    MyCustomDialogfragmentsBinding selectUnitBind;
    Stock stock;

    public UnitDialogFragement(Stock stock) {
        this.stock = stock;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.my_custom_dialogfragments, null);
        selectUnitBind = DataBindingUtil.bind(view);
        UnitSelectionListener selectionListener = new UnitSelectionListener();
        selectUnitBind.setSelectUnitHandler(selectionListener);
        builder.setView(view);
        return builder.create();
    }

    public class UnitSelectionListener {
        public void onUnitSelect(View view){
            if(view.getId() == R.id.cardview_Box){
                    Toast.makeText(getContext(), "Box clicked", Toast.LENGTH_SHORT).show();
                    BoxDialogFragment ialogFragement = new BoxDialogFragment(stock, view);
                    ialogFragement.show(getChildFragmentManager(), "exampledialog");
            }else {
                String selectedUnit = "";
                switch (view.getId()) {

                    case R.id.cardView_kg:
                        selectedUnit = Unit.UNIT_KG;
                        break;
                    case R.id.cardview_Gm:
                        selectedUnit = Unit.UNIT_GM;
                        break;
                    case R.id.cardview_Mg:
                        selectedUnit = Unit.UNIT_MG;
                        break;
                    case R.id.cardview_Mtr:
                        selectedUnit = Unit.UNIT_MTR;
                        break;
                    case R.id.cardview_Cm:
                        selectedUnit = Unit.UNIT_CM;
                        break;
                    case R.id.cardview_Mm:
                        selectedUnit = Unit.UNIT_MM;
                        break;
                    case R.id.cardview_ltr:
                        selectedUnit = Unit.UNIT_LTR;
                        break;
                    case R.id.cardview_Ml:
                        selectedUnit = Unit.UNIT_ML;
                        break;
                    case R.id.cardview_Pc:
                        selectedUnit = Unit.UNIT_PC;
                        break;
                    default:
                        // Log an problem.

                }
                Toast.makeText(getContext(), selectedUnit + " clicked", Toast.LENGTH_SHORT).show();
                stock.setPriamryUnit(selectedUnit);
                stock.setSecondUnit("1");
                dismiss();
            }

            ((CardView)view).setCardBackgroundColor(Color.GREEN);
        }
    }

}
