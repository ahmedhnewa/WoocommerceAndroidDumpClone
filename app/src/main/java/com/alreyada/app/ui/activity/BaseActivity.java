package com.alreyada.app.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.alreyada.app.R;
import com.alreyada.app.data.preference.AppPreference;
import com.alreyada.app.data.preference.SessionManager;
import com.alreyada.app.util.Utils;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    private Dialog progressDialog;
    private Context context;
    private Activity activity;
    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = BaseActivity.this;
        context = activity.getApplicationContext();
        actionBar = getSupportActionBar();

    }


    public void prepareLoadingDialog(Dialog progressDialog) {
        if (progressDialog == null) {
            progressDialog = new Dialog(context);
            progressDialog.setContentView(R.layout.custom_dialog);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            progressDialog.setCanceledOnTouchOutside(false);
        }
    }

    public void dismissLoadingDialog(Dialog progressDialog) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void showLoadingDialog(Dialog progressDialog) {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void showErrorMessage(String msg) {
        Utils.showErrorMessage(msg, activity);
    }

    public void showSuccessMessage(String msg) {
        Utils.showSuccessMessage(msg, activity);
    }

    public AppPreference getSharedPrefInstance() {
        return AppPreference.getInstance(context);
    }

    public SessionManager getSessionsManager(){
        return new SessionManager(context);
    }

    public void setToolbarWithBackButton(){
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public Context changeLanguage(Locale locale){
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        config.setLayoutDirection(locale);
        return context.createConfigurationContext(config);
    }

    public void enableBackButton() {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }
}
