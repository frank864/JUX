package com.example.juxtagrocery;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class LicenseDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_license,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);

        TextView textView = view.findViewById(R.id.txtLicense);
        Button btnDismiss= view.findViewById(R.id.btnDismiss);

        textView.setText(Utils.getLicense());

        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        return builder.create();
    }
}
