package com.alreyada.app.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alreyada.app.R;
import com.alreyada.app.api.ApiClient;
import com.alreyada.app.data.preference.SessionManager;
import com.alreyada.app.databinding.FragmentSignInBinding;
import com.alreyada.app.model.WpError;
import com.alreyada.app.model.authentication.jwt.JwtGetToken;
import com.alreyada.app.ui.dialog.ResetPasswordDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInFragment extends Fragment {
    private Dialog progress;
    private TextView progressMsgTv, forgetPassword;
    private TextInputLayout nameEmailEt, passwordEt;
    private MaterialButton signInButton;
    private SessionManager sessionManager;
    private ImageView btnBack;
    private FragmentSignInBinding binding;
    private boolean isInTask = false;
    private Activity activity;
    private SignInFragmentListener listener;
    private Context context;
    private static final String TAG = "SignInFragment";
    private Gson gson = new Gson();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = getActivity();
        context = getContext();

        prepareLoadingDialog();
        initView();
        validateInputsWhenTextsChanged();

        gson = new Gson();

        sessionManager = new SessionManager(getActivity());

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameOrEmail = nameEmailEt.getEditText().getText().toString().trim();
                String password = passwordEt.getEditText().getText().toString().trim();

                if (!validateEmailOrName() | !validatePassword()) {
                    return;
                }

                if (!isInTask) {
                    showLoadingDialog();
                    isInTask = true;
                    login(nameOrEmail, password);
                } else {
                    showLoadingDialog();
                    Toast.makeText(getActivity(), "هناك عملية تسجيل دخول سابقة , الرجاء الانتظار", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRequestOpenSignUpFragment();
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPasswordDialog resetPasswordDialog = new ResetPasswordDialog();
                resetPasswordDialog.show(getFragmentManager(), "reset password dialog");
            }
        });
    }

    private void validateInputsWhenTextsChanged() {
        nameEmailEt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!validateEmailOrName()) {

                }
                /*if (charSequence.length() > 40 || charSequence.toString().trim().isEmpty()) {
                    isEmailOrNameValid = false;
                } else {
                    isEmailOrNameValid = true;
                }*/
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwordEt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!validatePassword()) {

                }
                /*if (!VALID_PASSWORD_REGEX.matcher(charSequence).find() || charSequence.toString().trim().isEmpty()) {
                    isPasswordValid = false;
                } else {
                    isPasswordValid = true;
                }*/
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void prepareLoadingDialog() {
        if (progress == null) {
            progress = new Dialog(getActivity());
            progress.setContentView(R.layout.custom_dialog);
            progress.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            progress.setCanceledOnTouchOutside(false);
        }
    }

    private void initView() {
        nameEmailEt = binding.emailTextInput;
        passwordEt = binding.passwordTextInput;
        btnBack = binding.btnBack;
        signInButton = binding.signInBtn;
        forgetPassword = binding.forgetPassword;


        progressMsgTv = progress.findViewById(R.id.tv_progress_msg);

    }

    public void login(String username, String password) {
        ApiClient.getApiInterface(false, getContext())
                .getToken(username, password)
                .enqueue(new Callback<JwtGetToken>() {
                    @Override
                    public void onResponse(Call<JwtGetToken> call, Response<JwtGetToken> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            JwtGetToken data = response.body();

                            if (sessionManager.isLoggedIn()) {
                                sessionManager.logout();
                            }

                            String token = data.getJwtDataToken().getToken();
                            if (token != null && TextUtils.isEmpty(token)) {
                                

                            }
                        } else {
                            try {
                                String error = response.errorBody().string();
                                WpError wpError = gson.fromJson(error, WpError.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JwtGetToken> call, Throwable t) {
                        Log.d(TAG, "onResponse: " + gson.toJson(t));
                    }
                });
      /*  ApiClient.getApiInterface(false).getToken(username, password)
                .enqueue(new Callback<JwtGetToken>() {
                    @Override
                    public void onResponse(Call<JwtGetToken> call, Response<JwtGetToken> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            JwtGetToken data = response.body();

                            if (data.getUserId() != null && data.getUserId() != -1 && data.getUserId() != 0) {
                                String token = data.getToken();

                                User user = new User(data.getUserId(), data.getUserFirstName(), data.getUserLastName(),
                                        data.getUserEmail(), data.getUserRole(),
                                        data.getUserDisplayName(), data.getUserAvatarUrl(),
                                        token);

                                dismissLoadingDialog();

                                if (sessionManager.isLoggedIn()) {
                                    sessionManager.logout();
                                }

                                *//*Intent intent = new Intent(getActivity(), DashboardActivity.class);*//*
                                sessionManager.loginUser(user, false);
                                sessionManager.setPassword(password);

                                activity.setResult(Activity.RESULT_OK);

                                activity.finish();
                            } else {
                                showErrorMessage("معرف المستخدم غير موجود , يرجى الاتصال بنا لحل المشكلة");
                            }

                        } else {
                            if (response.errorBody() != null) {
                                try {
                                    String jsonError = response.errorBody().string();
                                    if (jsonError.contains("You have been locked out.") || jsonError.contains("Forbidden") || jsonError.contains("lock_out_screen") || jsonError.contains("lockout-text")) {
                                        Utils.showErrorMessage("لقد تم حظرك من التسجيل بسبب المحاولات الكثيرة", activity);
                                        return;
                                    }
                                    WpError error = Utils.getWpErrorResponse(jsonError);
                                    String code = null;
                                    if (error != null) {
                                        code = error.getCode();
                                    }
                                    String msg = null;
                                    if (error != null) {
                                        msg = error.getMessage();
                                    }
                                    switch (response.code()) {
                                        case 403:

                                            if (code != null) {
                                                if (code.equalsIgnoreCase("[jwt_auth] empty_password") || code.contains("empty_password")) {
                                                    showErrorMessage("كلمة مرور فارغة");
                                                } else if (code.equalsIgnoreCase("[jwt_auth] empty_username") || code.contains("empty_username")) {
                                                    showErrorMessage("حقل اسم المستخدم فارغ");
                                                } else if (code.equalsIgnoreCase("[jwt_auth] invalid_username") || code.contains("invalid_username")) {
                                                    showErrorMessage("لايوجد مستخدم بهذة الاسم , الرجاء المحاولة مجدددا باستخدام بريدك الالكتروني");
                                                } else if (code.equalsIgnoreCase("[jwt_auth] invalid_email") || code.contains("invalid_email")) {
                                                    showErrorMessage("لايوجد مستخدم بهذة البريد الالكتروني الرجاء التأكد والمحاولة مجددا");
                                                } else if (code.equalsIgnoreCase("[jwt_auth] incorrect_password") || code.contains("incorrect_password")) {
                                                    showErrorMessage("كلمة مرور غير صحيحة الرجاء التأكد واعادة المحاولة");
                                                } else {
                                                    showErrorMessage(msg);
                                                }
                                            }
                                            break;
                                        case 503:
                                            Utils.showErrorMessage("" + getString(R.string.site_under_maintenance), activity);
                                            break;
                                        default:
                                            if (msg != null && !msg.isEmpty()) {
                                                showErrorMessage("خطأ : " + msg);
                                            } else {
                                                showErrorMessage("" + getString(R.string.unknown_error));
                                            }
                                            break;
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Utils.showErrorMessage("خطأ غير معروف " + response.code(), activity);
                                }
                            } else {
                                Utils.showErrorMessage("خطأ غير معروف " + response.code(), activity);
                            }
                        }
                        isInTask = false;
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onFailure(Call<JwtGetToken> call, Throwable t) {
                        isInTask = false;
                        dismissLoadingDialog();
                        showErrorMessage("تعذر تسجيل الدخول , الرجاء التأكد من اتصالك بالانترنيت");
                        Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });*/
    }


    private void showErrorMessage(String msg) {
        Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);
        View sbView = snackBar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.tomato));
        snackBar.setTextColor(Color.WHITE);
        snackBar.show();
    }

    private void showSuccessMessage(String msg) {
        Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);
        View sbView = snackBar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.primaryColor));
        snackBar.setTextColor(Color.WHITE);
        snackBar.show();
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (SignInFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SignInFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private boolean validateEmailOrName() {
        String emailInput = nameEmailEt.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            nameEmailEt.setError("من فضلك ادخل البريد الالكتروني للمتابة");
            return false;
        } else {
            nameEmailEt.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = passwordEt.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            passwordEt.setError("من فضلك ادخل كلمة المرور للمتابة");
            return false;
        } else {
            passwordEt.setError(null);
            return true;
        }
    }

    public interface SignInFragmentListener {
        void onRequestOpenSignUpFragment();
    }
}
