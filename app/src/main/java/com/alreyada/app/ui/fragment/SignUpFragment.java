package com.alreyada.app.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alreyada.app.R;
import com.alreyada.app.data.constant.Constants;
import com.alreyada.app.data.preference.SessionManager;
import com.alreyada.app.databinding.FragmentSignUpBinding;
import com.alreyada.app.ui.dialog.EnterPasswordDialog;
import com.alreyada.app.util.FirebaseAuthentication;
import com.alreyada.app.util.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment {
    private static final String TAG = "SignUpFragment";
    private EditText edtFirstName, edtLastName, edtEmail, edtPassword;
    private MaterialButton btnSignUp, btnSignIn;
    private Dialog progress;
    private Activity activity;
    private Context context;
    private SessionManager sessionManager;
    private FragmentSignUpBinding binding;
    private SignUpFragmentListener listener;
    private boolean isInTask = false;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 8 characters
                    "$");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = getActivity();
        context = getContext();

        initView();
        initVar();
        prepareLoadingDialog();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRequestOpenSignInFragment();
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail() | !validateFirstUserName() | !validateLastUserName() | !validatePassword()) {
                    return;
                }
                String firstName = edtFirstName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String lastName = edtLastName.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();


                if (!isInTask) {
                    isInTask = true;
                    Map<String, String> queryMap = new HashMap<>();
                    queryMap.put("first_name", firstName);
                    queryMap.put("last_name", lastName);
                    createAccount(queryMap, email, password, false);
                } else {
                    Utils.showShortToast("هناك عملية بالخلفية , الرجاء الانتظار", context);
                }

            }
        });

        binding.signInWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });
    }

    private void initVar() {
        sessionManager = new SessionManager(context);
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
        edtFirstName = binding.edtFirstName;
        edtLastName = binding.edtLastName;
        edtEmail = binding.edtEmail;
        edtPassword = binding.edtPassword;
        btnSignUp = binding.btnSignUp;
        btnSignIn = binding.btnSignIn;
    }


    private void createAccount(Map<String, String> queryMap, String email, String password, boolean isSocialLogin) {
        showLoadingDialog();
     /*   ApiClient.getApiInterface(true)
                .createNewCustomer(queryMap, email, password)
                .enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            Customer userResponse = response.body();

                            isInTask = false;

                            if (userResponse.getId() != null) {
                                *//*User user = new User(userResponse.getId(),
                                        userResponse.getFirstName(),
                                        userResponse.getLastName(),
                                        userResponse.getEmail(),
                                        userResponse.getRole(),
                                        userResponse.getUsername(),
                                        userResponse.getAvatarUrl());
                                sessionManager.loginUser(user, isSocialLogin);*//*
                                *//*dismissLoadingDialog();*//*
                                // sign in user after login
                                signInUser(email, password);

                            } else {
                                showErrorMessage("معرف المستخدم مفقود");
                            }

                        } else {
                            Gson gson = new Gson();
                            Customer errorResponse;
                            try {
                                errorResponse = gson.fromJson(response.errorBody().string(), Customer.class);

                                switch (response.code()) {

                                    case 400:
                                        if (!isSocialLogin) {
                                            if (errorResponse != null) {
                                                String codeError = errorResponse.getCode();
                                                if (codeError.equalsIgnoreCase("registration-error-email-exists")) {
                                                    Toast.makeText(activity, "هناك حساب اخر بنفس البريد الالكتروني", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        } else {
                                            if (errorResponse != null) {
                                                String errorCode = errorResponse.getCode();
                                                if (errorCode.equalsIgnoreCase("registration-error-email-exists")) {

                                                    signInUser(email, password);
                                                } else {
                                                    String msg = Html.fromHtml(errorResponse.getMessage()).toString();
                                                    showErrorMessage(msg);
                                                }
                                            } else {
                                                showErrorMessage("فشلت عملية انشاء الحساب");
                                            }
                                        }
                                        break;

                                    case 401:
                                        showErrorMessage(getString(R.string.error_401));
                                        break;

                                    case 404:
                                        showErrorMessage(getString(R.string.error_404));
                                        break;

                                    case 500:
                                        showErrorMessage(getString(R.string.error_500));
                                        break;

                                    default:
                                        showErrorMessage("خطأ : " + errorResponse.getMessage());

                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                                showErrorMessage("خطأ غير معروف ");
                            }
                            isInTask = false;
                            dismissLoadingDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                        isInTask = false;
                        dismissLoadingDialog();
                        if (Utils.isNetworkAvailable(context)) {
                            Utils.showErrorMessage("" + getString(R.string.no_internet), activity);
                        } else {
                            showErrorMessage("تعذر تسجيل الدخول , الرجاء التأكد من اتصالك بالانترنيت");
                        }
                    }
                });*/
    }

    private void signInUser(String email, String password) {
        showLoadingDialog();
      /*  ApiClient.getApiInterface(false).getToken(email, password)
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

                            // this method i copied it from sign in fragment , so ahmed if you changed anythings in sign in fragment login function , do not forget to change it from here
                        } else {
                            if (response.errorBody() != null) {
                                try {
                                    String jsonError = response.errorBody().string();
                                    if (jsonError.contains("You have been locked out.") || jsonError.contains("Forbidden")) {
                                        Utils.showErrorMessage("لقد تم حظرك من تسجيل االدخول بسبب المحاولات الكثيرة", activity);
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
                            isInTask = false;
                            dismissLoadingDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<JwtGetToken> call, Throwable t) {
                        isInTask = false;
                        dismissLoadingDialog();
                        if (Utils.isNetworkAvailable(context)) {
                            Utils.showErrorMessage("" + getString(R.string.no_internet), activity);
                        } else {
                            showErrorMessage("تعذر تسجيل الدخول , الرجاء التأكد من اتصالك بالانترنيت");
                        }
                    }
                });*/
    }


    private boolean validateFirstUserName() {
        String usernameInput = edtFirstName.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            edtFirstName.setError("من فضلك , ادخل الاسم للمتابعة");
            btnSignIn.setEnabled(true);
            return false;
        } else if (usernameInput.length() > 20) {
            edtFirstName.setError("الاسم طويل جدا");
            btnSignIn.setEnabled(true);
            return false;
        } else {
            edtFirstName.setError(null);
            return true;
        }
    }

    private boolean validateLastUserName() {
        String usernameInput = edtLastName.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            edtLastName.setError("من فضلك , ادخل الاسم للمتابعة");
            edtLastName.requestFocus();
            btnSignIn.setEnabled(true);
            return false;
        } else if (usernameInput.length() > 15) {
            edtLastName.setError("الاسم طويل جدا");
            edtLastName.requestFocus();
            btnSignIn.setEnabled(true);
            return false;
        } else {
            edtLastName.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String usernameInput = edtEmail.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            edtEmail.setError("من فضلك , ادخل البريد الالكتروني للمتابعة");
            edtEmail.requestFocus();
            btnSignIn.setEnabled(true);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(usernameInput).matches()) {
            edtPassword.setError("بريد الكتروني غير صحيح");
            edtEmail.requestFocus();
            return false;

        } else {
            edtEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String usernameInput = edtPassword.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            edtPassword.setError("من فضلك , ادخل كلمة المرور للمتابعة");
            edtPassword.requestFocus();
            btnSignIn.setEnabled(true);
            return false;
        } else if (!PASSWORD_PATTERN.matcher(usernameInput).matches()) {
            edtPassword.setError("كلمة المرور ضعيفة");
            edtPassword.requestFocus();
            return false;
        } else {
            edtPassword.setError(null);
            return true;
        }
    }

    private void signInWithGoogle() {
        Intent signInIntent = FirebaseAuthentication.getInstance(context).getSignInClient().getSignInIntent();
        startActivityForResult(signInIntent, Constants.REQUEST_CODE_SIGN_IN_WITH_GOOGLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_SIGN_IN_WITH_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                woocommerceAuthWithGoogle(account);
                /*firebaseAuthWithGoogle(account);*/
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(activity, "فشلت عملية تسجيل الدخول باستخدام حسابك كوكل", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void woocommerceAuthWithGoogle(GoogleSignInAccount account) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("first_name", account.getGivenName());
        queryMap.put("last_name", account.getFamilyName());
        if (account.getPhotoUrl() != null) {
            queryMap.put("avatar_url", account.getPhotoUrl().toString());
        }

        EnterPasswordDialog enterPasswordDialog = new EnterPasswordDialog();
        enterPasswordDialog.show(getActivity().getSupportFragmentManager(), "enter password dialog");
        enterPasswordDialog.setOnReceivePassword(new EnterPasswordDialog.EnterPasswordDialogListener() {
            @Override
            public void onReceivePassword(String password) {
                createAccount(queryMap, account.getEmail(), password, true);
            }
        });

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnSuccessListener(activity, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // If sign in succeeds the auth state listener will be notified and logic to
                        // handle the signed in user can be handled in the listener.
                        Log.d(TAG, "signInWithCredential:success");

                    }
                })
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential", e);
                    }
                });
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

    public interface SignUpFragmentListener {
        void onRequestOpenSignInFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (SignUpFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SignUpFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listener = null;
    }
}
