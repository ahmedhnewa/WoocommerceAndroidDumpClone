package com.alreyada.app.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.alreyada.app.R;
import com.alreyada.app.ui.activity.LoginActivity;

public class NotLoggedInOrdersDialog extends AppCompatDialogFragment {
    private Context context;
    private Activity activity;
    private NotLoggedInOrdersDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        context = getContext();
        activity = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("تنبيه")
                .setIcon(R.drawable.ic_error)
                .setMessage("انتة غير مسجل دخول بالتطبيق , ففي حالة انشاء طلب لايتم حفظه على التطبيق ولايمكن عرض تفاصيله , لكن سيتم ارساله لك اذا كان رقم الهاتف صحيح")
                .setNegativeButton("متابعة", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onOkButtonClicked();
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("تسجيل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*listener.onDismissDialogClicked();*/
                        dialog.dismiss();
                        startActivity(new Intent(context, LoginActivity.class));
                    }
                });
        return builder.create();
    }

    public interface NotLoggedInOrdersDialogListener{
        void onOkButtonClicked();
        /*void onDismissDialogClicked();*/
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (NotLoggedInOrdersDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement NotLoggedInOrdersDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
