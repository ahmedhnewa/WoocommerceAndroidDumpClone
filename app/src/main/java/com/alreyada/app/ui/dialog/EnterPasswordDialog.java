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

import com.alreyada.app.databinding.DialogEnterPasswordBinding;

public class EnterPasswordDialog extends AppCompatDialogFragment {
    private Context context;
    private Activity activity;
    private DialogEnterPasswordBinding binding;
    private Button positiveButton;
    private EnterPasswordDialogListener listener;
    private AlertDialog dialog;
    private static final String ACTION = "actionParam";

    public static EnterPasswordDialog newInstance(String action) {
        // no longer need it , keep it when i tried to use it again if i need i later
        EnterPasswordDialog enterPasswordDialog = new EnterPasswordDialog();
        Bundle args = new Bundle();
        args.putString(ACTION,action);
        enterPasswordDialog.setArguments(args);
        return enterPasswordDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        context = getContext();
        activity = getActivity();

        LayoutInflater inflater = activity.getLayoutInflater();
        binding = DialogEnterPasswordBinding.inflate(inflater);
        View view = binding.getRoot();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("ادخل كلمة المرور")
                .setView(view)
                .setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("موافق",null);
        dialog = builder.create();
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        binding.note.setText("يرجى كتابة كلمة مرور في الحقل ادناه الخاص على موقعنا لاكمال العملية , في حالة ان لم يكن لديك كلمة مرور مسبقا فسيتم انشائها");

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = binding.textInputPassword.getEditText().getText().toString().trim();

                if (!validatePassword(password)) {
                    return;
                }

                if (listener != null) {
                    listener.onReceivePassword(password);
                }

                dialog.dismiss();
            }
        });
    }

    private boolean validatePassword(String password) {
        if (password.isEmpty()) {
            binding.textInputPassword.setError("من فضلك ادخل كلمة المرور");
            binding.textInputPassword.requestFocus();
            return false;
        } else {
            binding.textInputPassword.setError(null);
            return true;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (listener == null) {
            try {
                listener = (EnterPasswordDialogListener) context;
            } catch (ClassCastException e) {
                try {
                    listener = (EnterPasswordDialogListener) getTargetFragment();
                } catch (ClassCastException exception) {
                    throw new ClassCastException(context.toString() + " must implement EnterPasswordDialogListener in fragment or activity");
                }
            }
        }
    }

    public void setOnReceivePassword(final EnterPasswordDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listener = null;
    }

    public interface EnterPasswordDialogListener{
        void onReceivePassword(String password);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
