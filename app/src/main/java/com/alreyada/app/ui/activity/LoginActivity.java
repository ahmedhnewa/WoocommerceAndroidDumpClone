package com.alreyada.app.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alreyada.app.R;
import com.alreyada.app.ui.dialog.ResetPasswordDialog;
import com.alreyada.app.ui.fragment.SignInFragment;
import com.alreyada.app.ui.fragment.SignUpFragment;
import com.alreyada.app.util.Tools;
import com.alreyada.app.util.Utils;

public class LoginActivity extends AppCompatActivity implements ResetPasswordDialog.ResetPasswordDialogListener, SignUpFragment.SignUpFragmentListener, SignInFragment.SignInFragmentListener {
    private Activity activity;
    private Context context;
    private Dialog progress;
    private FragmentManager fm;
    private final SignUpFragment signUpFragment = new SignUpFragment();
    private final SignInFragment signInFragment = new SignInFragment();
    private static final String TAG = "LoginActivity";
    @IdRes
    private int fragmentContainer = R.id.fragmentContainer;
    private Fragment active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activity = LoginActivity.this;
        context = LoginActivity.this;

        initVar();

        if (savedInstanceState == null) {
            initFragments();
        }

        prepareLoadingDialog();

        Tools.setSystemBarColor(this, R.color.grey_3);

        //change system bar icon color
        Tools.setSystemBarLight(this);

    }

    private void initFragments() {
        fm.beginTransaction().add(fragmentContainer, signInFragment, "1").hide(signInFragment).commit();
        fm.beginTransaction().add(fragmentContainer, signUpFragment, "2").commit();
        active = signUpFragment;
    }

    private void initVar() {
        fm = getSupportFragmentManager();
    }

    private void prepareLoadingDialog() {
        if (progress == null) {
            progress = new Dialog(context);
            progress.setContentView(R.layout.custom_dialog);
            progress.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            progress.setCanceledOnTouchOutside(false);
        }
    }

    @Override
    public void onReceiveEmail(String email, boolean isWantByEmailLink) {
        if (Utils.isNetworkAvailable(context)) {
            if (!isWantByEmailLink) {
                showLoadingDialog();
                sendPasswordResetCode(email);
            } else {
                showLoadingDialog();
                sendEmailResetLink(email);
            }
        } else {
            Utils.showErrorMessage("لايوجد اتصال بالانترنيت", activity);
        }
    }

    private void sendEmailResetLink(String email) {
       /* ApiClient.getApiInterface(true).sendResetPasswordLink(email)
                .enqueue(new Callback<LostPassword>() {
                    @Override
                    public void onResponse(Call<LostPassword> call, Response<LostPassword> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            dismissLoadingDialog();
                            if (response.body().isSuccess()) {
                                Utils.showSuccessMessage("تم ارسال الرابط لبريدك الالكتروني , الرجاء التحقق من البريد الالكتروني", activity);
                            } else {
                                Utils.showErrorMessage("تعذر ارسال الرابط", activity);
                                Log.d(TAG, "onResponse: " + response.body().getMessage());
                            }
                        } else {
                            Utils.showErrorMessage("تعذر ارسال الرابط", activity);
                            try {
                                Log.d(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onFailure(Call<LostPassword> call, Throwable t) {
                        dismissLoadingDialog();
                        Log.d(TAG, "onResponse: " + t.getMessage());
                        if (!Utils.isNetworkAvailable(context)) {
                            Utils.showErrorMessage("لايوجد اتصال بالانترنيت", activity);
                        } else {
                            Utils.showErrorMessage("تعذر ارسال رابط اعادة تعيين كلمة مرورك الرجاء التأكد من اتصالك بالانترنيت واعادة المحاولة", activity);
                        }
                    }
                });*/
    }

    private void sendPasswordResetCode(String email) {
      /*  ApiClient.getApiInterface(false).sendResetPasswordCode(email).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResetPassword resetPassword = response.body();
                    if (response.code() == 200 || resetPassword.getData().getStatus() == 200) {
                        *//*AppUtils.showSuccessMessage(" " + resetPassword.getMessage() + " : " + resetPassword.getCode()
                                , activity);*//*
                        Intent intent = new Intent(context, ResetPasswordActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish();
                    } else {
                        Utils.showErrorMessage("فشلت عملية ارسال رسالة اعادة تعيين البريد الالكتروني", activity);
                    }
                } else {
                    switch (response.code()) {
                        case 500:
                            Gson gson = new Gson();
                            try {
                                ResetPassword resetPassword = gson.fromJson(response.errorBody().string()
                                        , ResetPassword.class);
                                String code = resetPassword.getCode();
                                String message = resetPassword.getMessage();
                                if (code != null && !code.isEmpty() && code.contains("bad_email")) {
                                    Utils.showErrorMessage("لايوجد مستخدم بهذة البريد الالكتروني", activity);
                                } else if (message != null && !message.isEmpty() && message.contains("You cannot request a password reset for a user with this role.")) {
                                    Utils.showErrorMessage("تعذر ارسال رسالة اعادة تعيين كلمة المرور", activity);
                                } else {
                                    Utils.showErrorMessage("" + getString(R.string.error_500), activity);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                Utils.showErrorMessage("" + getString(R.string.error_500), activity);
                            }

                            break;

                        case 400:
                            Utils.showErrorMessage("الرجاء ادخال البريد الالكتروني", activity);
                            break;
                        default:
                            Utils.showErrorMessage("هناك خطأ غير معروف", activity);
                    }
                }
                dismissLoadingDialog();
            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {
                dismissLoadingDialog();
                Utils.showErrorMessage("تعذر ارسال رسالة اعادة تعيين كلمة المرور", activity);
            }
        });*/
    }

    private void dismissLoadingDialog() {
        if (progress.isShowing()) {
            progress.dismiss();
        }
    }

    private void showLoadingDialog() {
        if (!progress.isShowing()) {
            progress.show();
        }
    }

    @Override
    public void onRequestOpenSignInFragment() {
        if (active != signInFragment) {
            fm.beginTransaction().hide(active).show(signInFragment).commit();
        }
        active = signInFragment;
    }

    @Override
    public void onRequestOpenSignUpFragment() {
        if (active != signUpFragment) {
            fm.beginTransaction().hide(active).show(signUpFragment).commit();
        }
        active = signUpFragment;
    }
}