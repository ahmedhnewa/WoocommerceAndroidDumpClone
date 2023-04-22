package com.alreyada.app.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.alreyada.app.R;

public class ConfirmOrderDialog extends AppCompatDialogFragment {
    private Context context;
    private Activity activity;
    private ConfirmOrderDialogListener listener;
    private AlertDialog dialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        context = getContext();
        activity = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("تأكيد الطلب")
                .setMessage("هل انتة متأكد من ارسال الطلب الان ؟")
                .setIcon(R.drawable.ic_shopping_cart_black)
                .setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDismiss();
                        dialog.dismiss();
                    }
                }).setPositiveButton("ارسال", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onOrderConfirmed();
            }
        });

        dialog = builder.create();
        return dialog;
    }

    public void setOnClickButtonListener(final ConfirmOrderDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface ConfirmOrderDialogListener {
        void onOrderConfirmed();

        void onDismiss();
    }
}
