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
import com.alreyada.app.data.preference.AppPreference;
import com.alreyada.app.data.preference.PrefKey;

public class PleaseSwipeToRefreshCartDialog extends AppCompatDialogFragment {
    private Context context;
    private Activity activity;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        context = getContext();
        activity = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("السلة")
                .setMessage("قمت باضافة منتج جديد للسلة , لتحديثها واظهار التغير الرجاء اسحب اصبعك من الاعلى للاسفل عند الضغط على زر السلة")
                .setIcon(R.drawable.ic_shopping_cart_black)
                .setPositiveButton("لاتظهر مجددا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppPreference.getInstance(context).setBoolean(PrefKey.isShouldShow1, false);
                    }
                })
                .setNegativeButton("اغلاق", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

}
