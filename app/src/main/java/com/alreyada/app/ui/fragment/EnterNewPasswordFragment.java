package com.alreyada.app.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alreyada.app.R;
import com.alreyada.app.databinding.FragmentEnterNewPasswordBinding;
import com.google.gson.Gson;

public class EnterNewPasswordFragment extends Fragment {
    private FragmentEnterNewPasswordBinding binding;
    private EnterNewPasswordFragmentListener listener;
    private static final String ARG_CODE = "paramCode";
    private static final String ARG_EMAIL = "paramEmail";
    private String email, code;
    private Dialog progress;
    private Context context;
    private Activity activity;
    private Gson gson;

    public EnterNewPasswordFragment() {
        // Required empty public constructor
    }

    public static EnterNewPasswordFragment newInstance(String code, String email) {
        EnterNewPasswordFragment enterNewPasswordFragment = new EnterNewPasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CODE, code);
        args.putString(ARG_EMAIL, email);
        enterNewPasswordFragment.setArguments(args);
        return enterNewPasswordFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(ARG_EMAIL);
            code = getArguments().getString(ARG_CODE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEnterNewPasswordBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        context = getContext();
        activity = getActivity();
        prepareLoadingDialog();
        gson = new Gson();


        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = binding.editTextTextPassword.getText().toString().trim();

                if (!newPassword.isEmpty() && email != null && code != null) {
                    showLoadingDialog();
                    setNewPassword(email, newPassword, code);
                } else {
                    Toast.makeText(context, "" + email + "  " + code + "  " + newPassword, Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    private void setNewPassword(String email, String newPassword, String code) {
      /*  ApiClient.getApiInterface(false).setNewPassword(email, newPassword, code)
                .enqueue(new Callback<ResetPassword>() {
                    @Override
                    public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ResetPassword resetPassword = response.body();
                            if (resetPassword.getMessage().contains("successfully") || resetPassword.getData().getStatus() == 200) {
                                Utils.showSuccessMessage("تم تغير كلمة المرور بنجاح", activity);
                                listener.onChangePasswordResult(true);
                            } else {
                                listener.onChangePasswordResult(false);
                            }
                        } else {
                            String errorResponse = null;
                            try {
                                errorResponse = response.errorBody().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ResetPassword resetPassword = null;
                            resetPassword = gson.fromJson(errorResponse, ResetPassword.class);

                            switch (response.code()) {
                                case 500:
                                    if (resetPassword.getCode().contains("bad_request") || resetPassword.getData().getStatus() == 500 || resetPassword.getMessage().contains("You must request a password reset code before you try to set a new password.")) {
                                        Utils.showErrorMessage("الرجاء ارسال رمز جديد", activity);
                                        Intent intent = new Intent(context, LoginActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(context, "الرجاء ارسال رمز جديد وأعادة المحاولة", Toast.LENGTH_SHORT).show();
                                        activity.finish();
                                    } else {
                                        Utils.showErrorMessage("هناك خطأ من الخادم", activity);
                                    }

                                    break;

                                case 400:
                                    String errorCode = resetPassword.getCode();
                                    Log.d("TAG", "onResponse: " + errorResponse );
                                    if (errorCode.contains("no_code") || errorCode.contains("no_email")) {
                                        Utils.showErrorMessage("معلومات غير كاملة",activity);
                                    }
                                    break;

                                default:
                                    Utils.showErrorMessage("خطأ غير معروف", activity);
                            }

                        }
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onFailure(Call<ResetPassword> call, Throwable t) {
                        dismissLoadingDialog();
                        listener.onChangePasswordResult(false);
                    }
                });*/
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
            listener = (EnterNewPasswordFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement EnterNewPasswordFragmentListener ");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
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

    public interface EnterNewPasswordFragmentListener {
        void onChangePasswordResult(boolean isSuccessful);
    }

}
