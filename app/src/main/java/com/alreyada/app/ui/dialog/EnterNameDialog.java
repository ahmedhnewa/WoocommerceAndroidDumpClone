package com.alreyada.app.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.alreyada.app.data.preference.SessionManager;
import com.alreyada.app.databinding.DialogEnterNameBinding;
import com.alreyada.app.util.FirebaseAuthentication;

public class EnterNameDialog extends AppCompatDialogFragment {
    private DialogEnterNameBinding binding;
    private Context context;
    private Activity activity;
    private AlertDialog dialog;
    private Button positiveBtn;
    protected EnterNameDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        context = getContext();
        activity = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        binding = DialogEnterNameBinding.inflate(inflater);
        View view = binding.getRoot();
        builder.setView(view)
                .setTitle("ادخل الاسم للمتابعة")
                .setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("موافق", null);

        dialog = builder.create();

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        positiveBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        SessionManager sessionManager = new SessionManager(context);
        if (sessionManager.isLoggedIn()) {
            binding.textInputFirstName.getEditText().setText(sessionManager.getUser().getFirstName());
            binding.textInputLastName.getEditText().setText(sessionManager.getUser().getLastName());
        }

        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = binding.textInputFirstName.getEditText().getText().toString().trim();
                String lastName = binding.textInputLastName.getEditText().getText().toString().trim();
                String phoneNumber = binding.textInputPhoneNumber.getEditText().getText().toString().trim();

                if (!validateFirstName(firstName) | !validateLastName(lastName) | !validatePhoneNumber(phoneNumber)) {
                    return;
                }

                listener.onReceiveTextsFromEnterNameDialog(firstName, lastName, phoneNumber);
//                FirebaseAuthentication.getInstance(context).setAnonymousData(new FirebaseUserAnonyMous(firstName,lastName,phoneNumber));
                dialog.dismiss();
            }
        });
    }

    private boolean validateFirstName(String firstName) {
        if (firstName.isEmpty()) {
            binding.textInputFirstName.setError("من فضلك ادخل الاسم الاول");
            binding.textInputFirstName.requestFocus();
            return false;
        } else if (firstName.length() > 15) {
            binding.textInputFirstName.setError("من فضلك ادخل اسم لايزيد عن 15 حرف");
            return false;
        } else {
            binding.textInputFirstName.setError(null);
            return true;
        }
    }

    private boolean validateLastName(String lastName) {
        if (lastName.isEmpty()) {
            binding.textInputLastName.setError("من فضلك ادخل الاسم الثاني");
            binding.textInputLastName.requestFocus();
            return false;
        } else if (lastName.length() > 15) {
            binding.textInputLastName.setError("من فضلك ادخل اسم لايزيد عن 15 حرف");
            return false;
        } else {
            binding.textInputLastName.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.isEmpty()) {
            binding.textInputPhoneNumber.setError("من فضلك ادخل رقم الهاتف");
            binding.textInputPhoneNumber.requestFocus();
            return false;
        } else if (phoneNumber.length() > 11) {
            binding.textInputPhoneNumber.setError("من فضلك ادخل رقم لايزيد عن 11 رقم");
            return false;
        } else if (phoneNumber.length() < 11) {
            binding.textInputPhoneNumber.setError("الرجاء ادخال رقم هاتف كامل");
            return false;
        } else {
            binding.textInputPhoneNumber.setError(null);
            return true;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (EnterNameDialogListener) context;
        } catch (ClassCastException e) {
            try {
                listener = (EnterNameDialogListener) getTargetFragment();
            } catch (ClassCastException exception) {
                throw new ClassCastException(context.toString() + " must implement EnterNameDialogListener");
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface EnterNameDialogListener {
        void onReceiveTextsFromEnterNameDialog(String firstName, String lastName, String phoneNumber);
    }

}
