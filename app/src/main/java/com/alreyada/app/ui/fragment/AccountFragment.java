package com.alreyada.app.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alreyada.app.R;
import com.alreyada.app.data.constant.Constants;
import com.alreyada.app.data.preference.SessionManager;
import com.alreyada.app.databinding.FragmentAccountBinding;
import com.alreyada.app.model.User;
import com.alreyada.app.ui.activity.DashboardActivity;
import com.alreyada.app.ui.activity.LoginActivity;
import com.alreyada.app.ui.activity.OrdersActivity;
import com.alreyada.app.ui.activity.WishListActivity;
import com.alreyada.app.ui.dialog.EditAddressDialog;
import com.alreyada.app.util.Utils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class AccountFragment extends Fragment implements EditAddressDialog.EditAddressDialogListener {
    private LinearLayout settings, address, orders, wishList;
    private MaterialButton logout;
    private TextView nameTv, emailTv;
    private SessionManager sessionManager;
    private FragmentAccountBinding binding;
    private NavigationView navView;
    // navigation view header
    private TextView emailHeader;
    private TextView nameHeader;
    private ImageView imageHeader;
    private Dialog progress;
    private Context context;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();
        activity = getActivity();

        initView();
        initVar();
        prepareLoadingDialog();

        if (sessionManager.isLoggedIn()) {
            loadUserData();
            binding.changePassword.setVisibility(View.VISIBLE);
        } else {
            binding.logout.setText("تسجيل الدخول");
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sessionManager.isLoggedIn()) {
                    sessionManager.logout();
                    startActivity(new Intent(context, DashboardActivity.class));
                    activity.finish();
                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivityForResult(intent, Constants.REQUEST_CODE_FINISH_ACTIVITY_AFTER_LOGGED_IN);
                }
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sessionManager.isLoggedIn()) {
                    startActivity(new Intent(context, OrdersActivity.class));
                } else {
                    Utils.showLongToast("الرجاء تسجيل الدخول", context);
                }
            }
        });

        wishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, WishListActivity.class));
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditAddressDialog editAddressDialog = new EditAddressDialog();
                editAddressDialog.setTargetFragment(AccountFragment.this, Constants.REQUEST_CODE_EDIT_ADDRESS_DIALOG_TARGET_FRAGMENT);
                editAddressDialog.show(getFragmentManager(), "edit address dialog");
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "تحت التطوير", Toast.LENGTH_SHORT).show();
            }
        });

/*        binding.changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkAvailable(context)) {
                    EditPasswordDialog editPasswordDialog = new EditPasswordDialog();
                    editPasswordDialog.show(getActivity().getSupportFragmentManager(), "edit password dialog");
                    editPasswordDialog.setOnGetCurrentPasswordSuccess(new EditPasswordDialog.EditPasswordDialogListener() {
                        @Override
                        public void onPasswordSuccess(String newPassword) {
                            showLoadingDialog();
                            Customer customer = new Customer();
                            customer.setPassword(newPassword);
                            ApiClient.getApiInterface(true)
                                    .updateCustomer(sessionManager.getId(), customer)
                                    .enqueue(new Callback<Customer>() {
                                        @Override
                                        public void onResponse(Call<Customer> call, Response<Customer> response) {
                                            if (response.isSuccessful() && response.body() != null) {
                                                Utils.showSuccessMessage("تم تغير كلمة المرور بنجاح", activity);
                                                SessionManager.getInstance(context).setPassword(newPassword);
                                            } else {
                                                try {
                                                    Gson gson = new Gson();
                                                    String errorResponse = response.errorBody().string();
                                                    Customer errorCustomer = gson.fromJson(errorResponse, Customer.class);
                                                    String code = errorCustomer.getCode();

                                                    switch (response.code()) {
                                                        case 400:
                                                            if (code.equalsIgnoreCase("woocommerce_rest_invalid_id")) {
                                                                Utils.showErrorMessage("عذرا , المستخدم الحالي غير موجود", activity);
                                                                Utils.showLongToast("تم تسجيل الخروج لان المستخدم المسجل حاليا لم يعد موجود", context);
                                                                sessionManager.logout();
                                                                startActivity(new Intent(context, LoginActivity.class));
                                                                activity.finish();
                                                            }
                                                            break;
                                                        default:
                                                            Utils.showErrorMessage("خطأ : " + errorCustomer.getMessage(), activity);
                                                            break;

                                                    }
                                                } catch (IOException e) {
                                                    Utils.showErrorMessage("خطأ غير معروف " + response.code(), activity);
                                                }
                                                dismissLoadingDialog();
                                            }
                                            dismissLoadingDialog();
                                        }

                                        @Override
                                        public void onFailure(Call<Customer> call, Throwable t) {
                                            dismissLoadingDialog();
                                            if (Utils.isNetworkAvailable(context)) {
                                                Utils.showErrorMessage("لايوجد اتصال بالانترنيت", activity);
                                            } else {
                                                Utils.showErrorMessage("هناك خطأ , الرجاء التأكد من اتصالك بالانترنيت واعادة المحاولة", activity);
                                            }
                                        }
                                    });
                        }
                    });
                } else {
                    Utils.showErrorMessage("لايوجد اتصال بالانترنيت", activity);
                }
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

    private void hideLayout(View ly) {
        if (ly.getVisibility() == View.VISIBLE) {
            ly.setVisibility(View.GONE);
        }
    }

    private void showLayout(View ly) {
        if (ly.getVisibility() == View.INVISIBLE || ly.getVisibility() == View.GONE) {
            ly.setVisibility(View.VISIBLE);
        }
    }

    private void prepareLoadingDialog() {
        if (progress == null) {
            progress = new Dialog(getActivity());
            progress.setContentView(R.layout.custom_dialog);
            progress.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            progress.setCanceledOnTouchOutside(false);
        }
    }


    private void initVar() {
        sessionManager = new SessionManager(getActivity());
    }

    private void loadUserData() {
        User user = sessionManager.getUser();

        if (user.getFirstName() != null) {
            nameTv.setText("مرحبا " + user.getFirstName());
            nameHeader.setText("مرحبا " + user.getFirstName());
        } else if (user.getUserName() != null) {
            nameTv.setText("مرحبا " + user.getUserName());
            nameHeader.setText("مرحبا " + user.getUserName());
        } else {
            Toast.makeText(getActivity(), "تعذر تحميل بيانات المستخدم", Toast.LENGTH_SHORT).show();
            nameTv.setText("الرجاء تسجيل الخروج واعادة المحاولة ");
        }

        if (user.getEmail() != null) {
            emailTv.setText(user.getEmail());
            emailHeader.setText(user.getEmail());
        } else {
            Toast.makeText(getActivity(), "تعذر تحميل بيانات المستخدم", Toast.LENGTH_SHORT).show();
            emailTv.setText("الرجاء تسجيل الخروج واعادة المحاولة ");
        }
//
//        if (user.getImgUrl() != null && circularIv != null) {
//            try {
//                Picasso.get()
//                        .load(user.getImgUrl())
//                        .fit()
//                        .centerCrop()
//                        .into(circularIv);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    private void initView() {
        settings = binding.settings;
        address = binding.address;
        orders = binding.orders;
        wishList = binding.wishList;
        logout = binding.logout;
        nameTv = binding.nameTv;
        emailTv = binding.emailTv;
//        circularIv = binding.circularImageView;

        navView = activity.findViewById(R.id.nav_view);

        View headerView = navView.getHeaderView(0);

        emailHeader = headerView.findViewById(R.id.header_email);
        nameHeader = headerView.findViewById(R.id.header_name);
        imageHeader = headerView.findViewById(R.id.header_picture);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_FINISH_ACTIVITY_AFTER_LOGGED_IN) {
            if (resultCode == Activity.RESULT_OK) {
                startActivity(new Intent(context, DashboardActivity.class));
                activity.finish();
            }
        }
    }

    @Override
    public void onReceiveAddress(String jsonAddress) {
        /*ShippingCommon shipping = new Gson().fromJson(jsonAddress, ShippingCommon.class);
        setData();*/
        Toast.makeText(context, "تم حفظ العنوان بنجاح", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
