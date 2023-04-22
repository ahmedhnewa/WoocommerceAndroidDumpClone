package com.alreyada.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.alreyada.app.R;
import com.alreyada.app.databinding.ActivityResetPasswordBinding;
import com.alreyada.app.ui.fragment.EnterNewPasswordFragment;
import com.alreyada.app.ui.fragment.ValidateResetPasswordCodeFragment;
import com.alreyada.app.data.preference.SessionManager;

public class ResetPasswordActivity extends AppCompatActivity implements ValidateResetPasswordCodeFragment.ValidateResetPasswordCodeFragmentListener, EnterNewPasswordFragment.EnterNewPasswordFragmentListener {
    private FragmentManager fm;
    private String email = "";
    @IdRes
    private final int fragmentContainer = R.id.fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.alreyada.app.databinding.ActivityResetPasswordBinding binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();

        if (intent != null && intent.getExtras() != null) {
            email = intent.getStringExtra("email");
        }

        final ValidateResetPasswordCodeFragment validateCode = ValidateResetPasswordCodeFragment.newInstance(email);

        if (savedInstanceState == null) {
            fm = getSupportFragmentManager();
            fm.beginTransaction().replace(fragmentContainer, validateCode).commit();
        }

    }

    @Override
    public void onValidateCodeResult(String code, String email, boolean isSuccessful) {
        if (isSuccessful) {
//            if (SessionManager.getInstance(ResetPasswordActivity.this.getAttemptFailedLogin().getAttemptFailedLoginEmail().equalsIgnoreCase(email)){
//                SessionManager.getInstance(ResetPasswordActivity.this).setAttemptFailedLoginCount(0,email);
//            }
            final EnterNewPasswordFragment newPasswordFragment = EnterNewPasswordFragment.newInstance(code, email);
            fm.beginTransaction().replace(fragmentContainer, newPasswordFragment).commit();
        }
    }

    @Override
    public void onChangePasswordResult(boolean isSuccessful) {
        if (isSuccessful) {
            Toast.makeText(this, "تم تغير كلمة المرور بنجاح الرجاء تسجيل الدخول", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
            finish();
        }
    }
}