package com.example.a2zbilling;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordDialogFragment extends AppCompatDialogFragment {
    private TextInputLayout email;
    private CardView save,cancel;
    private FirebaseAuth auth;
    private ProgressDialog regProgress;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_fragment_for_forgetpassword, null);
        auth = FirebaseAuth.getInstance();
        email=view.findViewById(R.id.reg_email);
        save=view.findViewById(R.id.cardview_save);
        cancel=view.findViewById(R.id.cardview_cancel);
        regProgress=new ProgressDialog(getContext());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1 = email.getEditText().getText().toString().trim();
                if (TextUtils.isEmpty(email1)) {
                    Toast.makeText(getContext(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                regProgress.setTitle("Sending mail on "+email1);
                regProgress.setMessage("please Wait...");
                regProgress.setCanceledOnTouchOutside(false);
                regProgress.show();

                auth.sendPasswordResetEmail(email1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                            regProgress.dismiss();
                        }else{
                            Toast.makeText(getContext(), "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                            regProgress.dismiss();
                        }
                    }
                });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        builder.setView(view);
        return builder.create();
    }
}
