package com.alreyada.app.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alreyada.app.R;
import com.alreyada.app.databinding.ActivityOrderDetailsBinding;
import com.alreyada.app.model.commons.BillingCommon;
import com.alreyada.app.model.commons.ShippingCommon;
import com.alreyada.app.model.orders.Order;
import com.alreyada.app.util.Utils;
import com.google.gson.Gson;

public class OrderDetailsActivity extends AppCompatActivity {
    private ActivityOrderDetailsBinding binding;
    private Toolbar toolbar;
    private Order order;
    private boolean isPaid;
    private Dialog progress;
    private static final String TAG = "OrderDetailsActivity";
    private int id = -1;
    private Context context;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        context = OrderDetailsActivity.this;
        activity = OrderDetailsActivity.this;

        initVar();
        initView();
        getData();
        initToolbar();
        prepareLoadingDialog();
        /*prepareRecyclerView();*/
        initStatus();
        binding.orderNumber.setText("رقم طلبك : " + order.getId());
        binding.orderDate.setText(Utils.getFormattedDate(order.getDateModifiedGmt()));
        ShippingCommon shipping = order.getShipping();
        BillingCommon billing = order.getBilling();
        binding.address.setText(shipping.getAddressOne() + " " + shipping.getAddressTow() + "\n" + billing.getPhone() + "\n" + shipping.getCity());
        if (shipping.getAddressOne() != null && !shipping.getAddressOne().isEmpty() && billing != null && !billing.getPhone().isEmpty()) {
            binding.shippingLayout.setVisibility(View.GONE);
        }
    }

    private void initStatus() {
        String status = order.getStatus();
        switch (status) {
            case "processing":
                binding.step1.setVisibility(View.VISIBLE);
                binding.step2.setVisibility(View.VISIBLE);
                binding.step3.setVisibility(View.VISIBLE);
                binding.step1date.setText(Utils.getFormattedDate(order.getDateCreatedGmt()));
                binding.step3date.setText(Utils.getFormattedDate(order.getDateModifiedGmt()));
                break;
            case "pending":
                binding.status.setVisibility(View.GONE);
                binding.chosePaymentType.setVisibility(View.VISIBLE);
                isPaid = false;
                implementOnButtonCLickListener();
                break;
            case "cancelled":
                binding.step1.setVisibility(View.VISIBLE);
                binding.step1Description.setText("تم الغاء طلبك");
                binding.step1Icon.setImageDrawable(getDrawable(R.drawable.ic_close));
                binding.step1Layout.setBackground(getDrawable(R.drawable.round_oval_failed));
                break;
            case "completed":
                binding.step1.setVisibility(View.VISIBLE);
                binding.step2.setVisibility(View.VISIBLE);
                binding.step3.setVisibility(View.VISIBLE);
                binding.step4.setVisibility(View.VISIBLE);
                binding.step4date.setText(Utils.getFormattedDate(order.getDateCompleted()));
                break;
            case "failed":
                binding.step1Description.setText("فشلت اكمال الطلب");
                binding.step1Icon.setImageDrawable(getDrawable(R.drawable.ic_close));
                binding.step1Layout.setBackground(getDrawable(R.drawable.round_oval_failed));
                break;
            case "refunded":
                binding.step1.setVisibility(View.VISIBLE);
                binding.step2.setVisibility(View.VISIBLE);
                binding.step3.setVisibility(View.VISIBLE);
                binding.step3Icon.setImageDrawable(getDrawable(R.drawable.ic_clock));
                binding.step1Layout.setBackground(getDrawable(R.drawable.round_oval_failed));
                binding.step3title.setText("تم استرجاع الاموال");
                binding.step3description.setText("لم يتم الدفع وتم ارجاع اموالك");
                break;
            case "on-hold":
                binding.step1.setVisibility(View.VISIBLE);
                binding.step2.setVisibility(View.VISIBLE);
                binding.step1date.setText(Utils.getFormattedDate(order.getDateCreatedGmt()));
                break;
        }
    }

    private void implementOnButtonCLickListener() {
        binding.chosePaymentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                processPaymentInWebView(order.getId());
            }
        });
    }

    private void processPaymentInWebView(int orderId) {
      /*  ApiClient.getApiInterface(true).getCheckoutUrl(String.valueOf(orderId))
                .enqueue(new Callback<CheckoutUrlResponse>() {
                    @Override
                    public void onResponse(Call<CheckoutUrlResponse> call, Response<CheckoutUrlResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            dismissLoadingDialog();

                            String url = response.body().getCheckoutUrl();
                            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                            CustomTabsIntent customTabsIntent = builder.build();
                            customTabsIntent.launchUrl(context, Uri.parse(url));

                        } else {
                            Log.d(TAG, "onResponse: " + response.code());
                            dismissLoadingDialog();
                        }
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onFailure(Call<CheckoutUrlResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        dismissLoadingDialog();
                    }
                });*/
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            setTitle("الطلب " + id);
            actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>" + getTitle() + "</font>"));
        }
    }

    private void initView() {
        toolbar = binding.toolbarLayout.toolbar;
    }

    private void initVar() {

    }

    private void getData() {
        Gson gson = new Gson();
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        String jsonOrder = intent.getStringExtra("order_details");
        order = gson.fromJson(jsonOrder, Order.class);
        Log.d(TAG, "getData: " + jsonOrder + "\n" + order.getPaymentMethodTitle());
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
                progress = new Dialog(context);
                progress.setContentView(R.layout.custom_dialog);
                progress.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                progress.setCanceledOnTouchOutside(false);
            }
        }

}