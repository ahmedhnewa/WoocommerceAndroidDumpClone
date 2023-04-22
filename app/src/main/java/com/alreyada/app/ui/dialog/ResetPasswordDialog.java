package com.alreyada.app.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.alreyada.app.R;
import com.alreyada.app.databinding.DialogResetPasswordBinding;
import com.google.android.material.textfield.TextInputLayout;

public class ResetPasswordDialog extends DialogFragment {
    private ResetPasswordDialogListener listener;
    private DialogResetPasswordBinding binding;
    private TextInputLayout textInputEmail;
    private AlertDialog dialog;
    private Context context;
    private Activity activity;
    private RadioButton checkedRadioButton, radioOne, radioTow;
    private View view;
    private String selectedItem = "code";

    public interface ResetPasswordDialogListener {
        void onReceiveEmail(String email, boolean isWantByEmailLink);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        context = getContext();
        activity = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        binding = DialogResetPasswordBinding.inflate(inflater);
        view = binding.getRoot();
        builder.setView(view)
                .setTitle("اعادة تعيين كلمة المرور")
                .setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("ارسال", null);

        dialog = builder.create();

        textInputEmail = binding.textInputEmail;
        radioOne = binding.radioOne;
        radioTow = binding.radioTwo;

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        Button sendButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textInputEmail.getEditText().getText().toString().trim();
                if (!validateEmail(email)) {
                    return;
                }
                int radioId = binding.radioGroup.getCheckedRadioButtonId();
                checkedRadioButton = v.findViewById(radioId);
                boolean isWantByEmailLink = false;
                if (selectedItem.equals("code")) {
                    isWantByEmailLink = false;
                } else if (selectedItem.equals("link")) {
                    isWantByEmailLink = true;
                }
                listener.onReceiveEmail(email, isWantByEmailLink);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_one:
                        checkedRadioButton = view.findViewById(R.id.radio_one);
                        radioOne.setChecked(true);
                        radioTow.setChecked(false);
                        selectedItem = "code";
                        break;
                    case R.id.radio_two:
                        checkedRadioButton = view.findViewById(R.id.radio_two);
                        radioOne.setChecked(false);
                        radioTow.setChecked(true);
                        selectedItem = "link";
                        break;
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ResetPasswordDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ResetPasswordDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            textInputEmail.setError("الرجاء ادخال البريد الالكتروني");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputEmail.setError("الرجاء ادخال بريد الكتروني صالح");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

}
