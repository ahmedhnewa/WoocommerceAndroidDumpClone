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
import com.alreyada.app.databinding.DialogEditPasswordBinding;
import com.alreyada.app.util.Utils;

import java.util.regex.Pattern;

public class EditPasswordDialog extends AppCompatDialogFragment {
    private Context context;
    private Activity activity;
    private DialogEditPasswordBinding binding;
    private AlertDialog dialog;
    private Button positiveButton;
    private EditPasswordDialogListener listener;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 8 characters
                    "$");

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        context = getContext();
        activity = getActivity();

        LayoutInflater inflater = activity.getLayoutInflater();
        binding = DialogEditPasswordBinding.inflate(inflater);
        View view = binding.getRoot();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view)
                .setTitle("تغير كلمة المرور")
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
        positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        if (!SessionManager.getInstance(context).isLoggedIn()) {
            Utils.showLongToast("عذرا , الرجاء تسجيل الدخول", context);
            dialog.dismiss();
        }

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = binding.textInputOldPassword.getEditText().getText().toString().trim();
                String newPassword = binding.textInputPassword.getEditText().getText().toString().trim();

                if (!validateOldPassword(oldPassword) | !validateNewPassword(newPassword)) {
                    return;
                }

                String currentPassword = SessionManager.getInstance(context).getUserPassword();

                if (currentPassword != null && !currentPassword.isEmpty()) {
                    if (!oldPassword.equals(currentPassword)) {
                        binding.textInputOldPassword.setError("كلمة المرور القديمة لاتتطابق مع الحالية");
                        return;
                    }
                } else {
                    Utils.showLongToast("لسبب غير معروف كلمة المرور الحالية غير محفوظة , الرجاء الاتصال بنا لحل المشكلة التقنية", context);
                }

                if (listener != null) {
                    listener.onPasswordSuccess(newPassword);
                }

                dialog.dismiss();
            }
        });
    }

    private boolean validateOldPassword(String oldPassword) {
        if (oldPassword.isEmpty()) {
            binding.textInputOldPassword.setError("من فضلك ادخل كلمة المرور القديمة");
            binding.textInputOldPassword.requestFocus();
            return false;
        } else {
            binding.textInputOldPassword.setError(null);
            return true;
        }
    }

    private boolean validateNewPassword(String password) {
        if (password.isEmpty()) {
            binding.textInputPassword.setError("من فضلك ادخل كلمة المرور الجديدة");
            binding.textInputPassword.requestFocus();
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            binding.textInputPassword.setError("كلمة المرور ضعيفة");
            return false;
        } else {
            binding.textInputPassword.setError(null);
            return true;
        }
    }

    public void setOnGetCurrentPasswordSuccess(final EditPasswordDialogListener listener) {
        this.listener = listener;
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

    public interface EditPasswordDialogListener {
        void onPasswordSuccess(String newPassword);
    }
}
