package com.alreyada.app.ui.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {
/*
    private ActivityAdminBinding binding;
    private FragmentManager fm;
    private Toolbar toolbar;
    private SessionManager sessionManager;
    private Context context;
    private Activity activity;
    private Dialog progress;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuthentication authentication;
    private BottomNavigationView bottomNavigationView;
    private final UsersFragment usersFragment = new UsersFragment();
    private static final String TAG = "AdminActivity";
    @IdRes
    private int fragmentContainerId = R.id.fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        context = AdminActivity.this;
        activity = AdminActivity.this;

        initView();
        initVar();
        initToolbar();

        if (!sessionManager.isLoggedIn() || !Utils.isNetworkAvailable(context)) {
            finish();
            return;
        }

        prepareLoadingDialog();
        checkCurrentUser();

    }

    private void checkCurrentUser() {
        showLoadingDialog();
        checkIsAdmin(new AdminActivityListener() {
            @Override
            public void onGetResult(boolean isWordpressAdmin, boolean isFirebaseAdmin, Customer customer) {
                if (isWordpressAdmin && isFirebaseAdmin) {
                    Toast.makeText(context, "لديك صلاحية للوصول هنا", Toast.LENGTH_SHORT).show();
                    showActivityContainer();
                } else if (isWordpressAdmin) {
                    Toast.makeText(context, "انتة ادمن في وردبريس فقط", Toast.LENGTH_SHORT).show();
                    Utils.copyText("your_uid", authentication.getUserUid(), context);
                } else if (isFirebaseAdmin) {
                    Toast.makeText(context, "انتة ادمن في فايربيس فقط", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, "onGetResult: " + "s1 : " + isWordpressAdmin + " s2 : " + isFirebaseAdmin);
                dismissLoadingDialog();
            }

            @Override
            public void onWooResponseNotSuccessful(int responseCode, String jsonError) {
                Gson gson = new Gson();
                Customer customer = gson.fromJson(jsonError, Customer.class);
                switch (responseCode) {
                    case 404:
                        if (customer.getCode().equalsIgnoreCase("woocommerce_rest_invalid_id")) {
                            finish();
                            sessionManager.logout();
                            authentication.signOut();
                            Utils.showLongToast("الحساب المسجل لم يعد موجود !", context);
                        }
                        break;
                    default:
                        finish();
                        Utils.showErrorMessage("هناك خطأ غير معروف", activity);
                        break;
                }
                Log.d(TAG, "onWooResponseNotSuccessful: " + responseCode + "\n" + jsonError);
                dismissLoadingDialog();
            }

            @Override
            public void onWooFailed(String msg) {
                if (!Utils.isNetworkAvailable(context)) {
                    Utils.showLongToast("لايوجد اتصال بالانترنيت", activity);
                } else {
                    Utils.showLongToast("تعذر التحقق من صلاحياتك", activity);
                }
                finish();
                Log.d(TAG, "onWooFailed: " + msg);
                dismissLoadingDialog();
            }

            @Override
            public void onFirebaseFailed(String msg, int errorCode) {
                if (!Utils.isNetworkAvailable(context)) {
                    Utils.showLongToast("لايوجد اتصال بالانترنيت", activity);
                } else {
                    Utils.showLongToast("تعذر التحقق من صلاحياتك", activity);
                }
                finish();
                Log.d(TAG, "onFirebaseFailed: " + errorCode + "\n" + errorCode);
                dismissLoadingDialog();

            }
        });

    }

    private void showActivityContainer() {
        showView(bottomNavigationView);
        UsersFragment usersFragment = new UsersFragment();
        fm.beginTransaction().replace(fragmentContainerId, usersFragment).commit();
    }


    private void hideView(View ly) {
        if (ly.getVisibility() == View.VISIBLE) {
            ly.setVisibility(View.GONE);
        }
    }

    private void showView(View ly) {
        if (ly.getVisibility() == View.INVISIBLE || ly.getVisibility() == View.GONE) {
            ly.setVisibility(View.VISIBLE);
        }
    }

    private void prepareLoadingDialog() {
        if (progress == null) {
            progress = new Dialog(activity);
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

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>" + "تواصل معنا" + "</font>"));
        }
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void initView() {
        toolbar = binding.toolbarLayout.toolbar;
        bottomNavigationView = binding.bottomNavigation;
    }

    private void initVar() {
        fm = getSupportFragmentManager();
        sessionManager = new SessionManager(context);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        authentication = new FirebaseAuthentication(context);
    }

    */
/*private boolean checkIsWordpressAdmin() {
        User user = sessionManager.getUser();
        if (user.getRole().equals("administrator") || sessionManager.isLoggedIn() || isAdmin && !isThereError) {
            return true;
        }
        finish();
        AppUtils.showLongToast("ليس لديك صلاحية للوصول هنا !!", context);
        return false;
    }*//*


    */
/*private boolean checkIsAdmin() {
        if (checkIsWordpressAdmin()) {
            isAdmin = false;

            if (authentication.isLoggedIn()) {

                if (isAllowedToManageDatabase) {
                    isAdmin = true;
                }

            }

            return isAdmin;
        }
        return false;
    }*//*


    private void checkIsAdmin(final AdminActivityListener adminActivityListener) {
        // get user details and check is admin in wordpress or not
        ApiClient.getApiInterface(true).getUserDetailsById(sessionManager.getId())
                .enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // when response is successful
                            // check user role
                            Customer customer = response.body();
                            String role = customer.getRole();

                            // if user is admin in wordpress
                            if (role.equals("administrator")) {

                                // before send the result back
                                // check if admin in firebase and send back the result

                                // make request to the clients reference and check is his uid is exists
                                reference.child(Constants.CLIENTS_KEY).child(authentication.getUserUid())
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                // first check is snapshot is not null , and check if the snapshot exist
                                                if (snapshot != null && snapshot.exists()) {
                                                    // good , it already exist , let is check the value is true or false (isAdmin)
                                                    Boolean isFirebaseAdmin = false;
                                                    isFirebaseAdmin = snapshot.getValue(Boolean.class);

                                                    // check if the value is not null , and it true
                                                    if (isFirebaseAdmin != null && isFirebaseAdmin) {
                                                        // good, user is wordpressAdmin and firebaseAdmin
                                                        adminActivityListener.onGetResult(true, true, customer);
                                                    } else {
                                                        // user is admin in wordpress db , but not admin in the firebase realtime database
                                                        // sadly he did not have access to change the values
                                                        adminActivityListener.onGetResult(true, false, customer);
                                                    }

                                                } else {
                                                    adminActivityListener.onGetResult(true, false, customer);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                adminActivityListener.onFirebaseFailed(error.getMessage(), error.getCode());
                                            }
                                        });


                            } else {
                                // the user is not admin in wordpress db
                                adminActivityListener.onGetResult(false, false, customer);
                            }


                        } else {
                            // there is error with the request , maybe authorization error or user account is no longer in wordpress db
                            try {
                                adminActivityListener.onWooResponseNotSuccessful(response.code(), response.errorBody().string());
                            } catch (IOException e) {
                                adminActivityListener.onWooResponseNotSuccessful(response.code(), null);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                        // there is error when check current user
                        // maybe caused by internet or converter library or java class or the server send different response
                        adminActivityListener.onWooFailed(t.getMessage());
                    }
                });
    }
*/

}