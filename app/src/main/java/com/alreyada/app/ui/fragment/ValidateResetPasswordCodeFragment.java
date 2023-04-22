package com.alreyada.app.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alreyada.app.R;
import com.alreyada.app.databinding.FragmentResetValidatePasswordCodeBinding;
import com.alreyada.app.util.Utils;
import com.google.gson.Gson;

public class ValidateResetPasswordCodeFragment extends Fragment {
    private FragmentResetValidatePasswordCodeBinding binding;
    private static final String ARG_EMAIL = "paramEmail";
    private String email;
    private Context context;
    private Activity activity;
    private Dialog progress;

    public ValidateResetPasswordCodeFragment() {
        // Required empty public constructor
    }

    public static ValidateResetPasswordCodeFragment newInstance(String email) {
        ValidateResetPasswordCodeFragment passwordCodeFragment = new ValidateResetPasswordCodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        passwordCodeFragment.setArguments(args);
        return passwordCodeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(ARG_EMAIL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentResetValidatePasswordCodeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = getActivity();
        context = getContext();

        Gson gson = new Gson();
        prepareLoadingDialog();


        if (email != null) {
            binding.emailTv.setText("تم ارسال رمز التاكيد الى البريد الالكتروني " + email + " يرجى ادخاله " + "قبل 20 دقيقة من الان لاعأدة تعيين كلمة مرورك");
        } else {
            binding.emailTv.setVisibility(View.GONE);
        }

        binding.et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    binding.et2.requestFocus();
                } else if (s.length() == 0) {
                    binding.et1.clearFocus();
                }
            }
        });

        binding.et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    binding.et3.requestFocus();
                } else if (s.length() == 0) {
                    binding.et1.requestFocus();
                }
            }
        });

        binding.et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    binding.et4.requestFocus();
                } else if (s.length() == 0) {
                    binding.et2.requestFocus();
                }
            }
        });

        binding.et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    binding.et4.requestFocus();
                } else if (s.length() == 0) {
                    binding.et3.requestFocus();
                }
            }
        });


        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code1 = binding.et1.getText().toString().trim();
                String code2 = binding.et2.getText().toString().trim();
                String code3 = binding.et3.getText().toString().trim();
                String code4 = binding.et4.getText().toString().trim();

                String finalCode = code1 + code2 + code3 + code4;
                if (Utils.isNetworkAvailable(context)) {
                    showLoadingDialog();
                    validateCode(finalCode);
                }
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });

    }

    private void validateCode(String code) {
       /* ApiClient.getApiInterface(false).validatePasswordCode(email, code)
                .enqueue(new Callback<ResetPassword>() {
                    @Override
                    public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ResetPassword resetPassword = response.body();

                            if (resetPassword.getData().getStatus() == 200 || response.code() == 200) {
                                Utils.showSuccessMessage("الرمز المدخل صحيح", activity);
                                listener.onValidateCodeResult(code, email, true);
                            }

                        } else {

                            switch (response.code()) {

                                case 500:

                                    try {
                                        ResetPassword resetPassword = gson.fromJson(response.errorBody().string(), ResetPassword.class);
                                        String code = resetPassword.getCode();

                                        if (code != null && !code.isEmpty()) {

                                            if (code.contains("bad_request")) {
                                                if (resetPassword.getMessage().contains("2")) {
                                                    Utils.showErrorMessage("الرمز المدخل غير صحيح , متبقي لديك محاولتين", activity);
                                                } else if (resetPassword.getMessage().contains("1")) {
                                                    Utils.showErrorMessage("رمز التاكيد المدخل غير صحيح , متبقي محاولة واحدة", activity);
                                                } else if (resetPassword.getMessage().contains("maximum") || resetPassword.getMessage().contains("The reset code provided is not valid. You have used the maximum number of attempts allowed. You must request a new code.")) {
                                                    Utils.showErrorMessage("لقد انتهت عدد المحاولات", activity);
                                                    Utils.showLongToast("انتهت عدد المحاولات الرجاء اعادة ارسال رمز التاكيد", context);
                                                    Intent intent = new Intent(context, LoginActivity.class);
                                                    startActivity(intent);
                                                    activity.finish();
                                                }
                                            }

                                        }

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        Utils.showErrorMessage(getString(R.string.error_500), activity);
                                    }

                                    break;

                                case 400:

                                    try {
                                        ResetPassword resetPassword = gson.fromJson(response.errorBody().string(), ResetPassword.class);
                                        String code = resetPassword.getCode();

                                        if (code != null && !code.isEmpty()) {

                                            if (code.contains("no_code")) {
                                                Utils.showErrorMessage("يرجى ارسال رمز التاكيد", activity);
                                            }

                                            if (code.contains("no_email")) {
                                                Utils.showErrorMessage("تعذر ارسال البريد الالكتروني", activity);
                                            }

                                        }

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        Utils.showErrorMessage("تعذر تاكيد الرمز", activity);
                                    }

                                    break;
                            }
                            listener.onValidateCodeResult(code, email, false);
                        }
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onFailure(Call<ResetPassword> call, Throwable t) {
                        dismissLoadingDialog();
                        Utils.showErrorMessage("فشلت عملية اعادة تعيين كلمة المرور", activity);
                        listener.onValidateCodeResult(code, email, false);
                    }
                });*/
    }

    private void prepareLoadingDialog() {
        if (progress == null) {
            progress = new Dialog(context);
            progress.setContentView(R.layout.custom_dialog);
            progress.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            progress.setCanceledOnTouchOutside(false);
        }
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
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ValidateResetPasswordCodeFragmentListener ");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface ValidateResetPasswordCodeFragmentListener {
        void onValidateCodeResult(String code, String email, boolean isSuccessful);
    }
}
