package com.alreyada.app.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alreyada.app.R;
import com.alreyada.app.adapter.CartAdapter;
import com.alreyada.app.data.preference.AppPreference;
import com.alreyada.app.data.preference.SessionManager;
import com.alreyada.app.data.sqlite.DbCartController;
import com.alreyada.app.databinding.ActivityCheckoutBinding;
import com.alreyada.app.model.authentication.customer.Customer;
import com.alreyada.app.model.commons.BillingCommon;
import com.alreyada.app.model.commons.LineItems;
import com.alreyada.app.model.commons.ShippingCommon;
import com.alreyada.app.model.ordernote.OrderNoteRequest;
import com.alreyada.app.model.orders.OrderRequest;
import com.alreyada.app.model.product.Product;
import com.alreyada.app.ui.dialog.ConfirmOrderDialog;
import com.alreyada.app.ui.dialog.EditAddressDialog;
import com.alreyada.app.ui.dialog.NotLoggedInOrdersDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity implements CartAdapter.OnItemClickListener, EditAddressDialog.EditAddressDialogListener, NotLoggedInOrdersDialog.NotLoggedInOrdersDialogListener {
    private RadioButton radioBtnExpress, radioBtnNormal, creditCard, cod, website;
    private List<Product> cartProductsList;
    private RecyclerView cartProducts;
    private CartAdapter adapter;
    private ActivityCheckoutBinding binding;
    private Context context;
    private DbCartController controller;
    private SessionManager sessionManager;
    private Activity activity;
    private static Spanned currencySymbol;
    private String checkedItem = "";
    private boolean isCanOrderNow = false;
    private BillingCommon shipping;
    private static final String TAG = "CheckoutActivity";
    private Dialog progress;
    private Toolbar toolbar;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        context = CheckoutActivity.this;
        activity = CheckoutActivity.this;

        if (AppPreference.getInstance(context).getStoreData().getCurrencySymbol() != null) {
            currencySymbol = Html.fromHtml(AppPreference.getInstance(context).getStoreData().getCurrencySymbol());
        }

        // This Is Program Made by AhmedRiyadh
        initView();
        initVar();
        initToolbar();
        setData();

        if (savedInstanceState == null) {
            cod.setChecked(true);
            checkedItem = "cod";
            checkIsCanOrderNow();
        }

        prepareLoadingDialog();
        prepareRecyclerView();

        binding.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditAddressDialog editAddressDialog = new EditAddressDialog();
                editAddressDialog.show(getSupportFragmentManager(), "edit address dialog");
            }
        });

        binding.checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCanOrderNow) {
                    ConfirmOrderDialog confirmOrderDialog = new ConfirmOrderDialog();
                    confirmOrderDialog.show(getSupportFragmentManager(), "confirm order dialog");

                    confirmOrderDialog.setOnClickButtonListener(new ConfirmOrderDialog.ConfirmOrderDialogListener() {
                        @Override
                        public void onOrderConfirmed() {
                            if (!sessionManager.isLoggedIn()) {
                                NotLoggedInOrdersDialog notLoggedInOrdersDialog = new NotLoggedInOrdersDialog();
                                notLoggedInOrdersDialog.show(getSupportFragmentManager(), "not logged in order dialog");
                            } else {
                                showLoadingDialog();
                                createOrder();
                            }
                        }

                        @Override
                        public void onDismiss() {
                            // ...
                        }
                    });

                } else {
                    setData();
                    Toast.makeText(context, "يرجى اختيار طرق الدفع ومكان الشحن", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>" + "اتمام الطلب" + "</font>"));
        }
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void setData() {

        double total = 0;
        double totalBeforeDiscount = 0;

        for (int i = 0; i < cartProductsList.size(); i++) {

            int id = cartProductsList.get(i).getProductId();
            int amount = controller.getQuantityOfItem(id);
            String priceRStr = cartProductsList.get(i).getPrice();
            String priceSStr = cartProductsList.get(i).getRegularPrice();

            if (priceRStr != null && !priceRStr.isEmpty()) {
                double price = Double.parseDouble(priceRStr) * amount;
                total += price;
            }

            if (priceSStr != null && !priceSStr.isEmpty()) {
                double discount = Double.parseDouble(priceSStr) * amount;
                totalBeforeDiscount += discount;
            }


        }
        binding.totalPrice.setText("السعر الاجمالي بعد الخصم هو " + df2.format(total) + currencySymbol + " " + " وقبل الخصم هو " + df2.format(totalBeforeDiscount) + currencySymbol);
        shipping = sessionManager.getAddress();

        checkIsCanOrderNow();
    }

    private void checkIsCanOrderNow() {
        if (shipping != null) {
            if (!(shipping != null ? shipping.getAddressOne().isEmpty() : false) && !shipping.getCity().isEmpty() && !shipping.getPhone().isEmpty()) {
                binding.shippingAddress.setText(shipping.getCity() + " " + shipping.getAddressOne() + " " + shipping.getPhone());
            } else {
                binding.shippingAddress.setText("ليس لديك عنوان مدخل , اضغط على تعديل لاضافة عنوان");
                isCanOrderNow = false;
            }
        }

        if (!checkedItem.isEmpty() && !(shipping != null && shipping.getAddressOne().isEmpty()) && !shipping.getCity().isEmpty() && !shipping.getPhone().isEmpty()) {
            isCanOrderNow = true;
        }
    }

    private void initVar() {
        cartProductsList = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Product>>() {
        }.getType();
        Intent intent = getIntent();
        cartProductsList.addAll(gson.fromJson(intent.getStringExtra("cart_list"), type));
        sessionManager = new SessionManager(context);
        controller = new DbCartController(context);
    }

    private void prepareRecyclerView() {
        cartProducts.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        cartProducts.setLayoutManager(layoutManager);
        adapter = new CartAdapter(context, cartProductsList, R.layout.item_cart2, true);
        cartProducts.setAdapter(adapter);
    }

    private void initView() {
        radioBtnExpress = (RadioButton) findViewById(R.id.radioBtnExpress);
        radioBtnNormal = (RadioButton) findViewById(R.id.radioBtnNormal);

        creditCard = (RadioButton) findViewById(R.id.creditCard);
        cod = (RadioButton) findViewById(R.id.cod);
        /*paytm = (RadioButton) findViewById(R.id.paytm);*/
        website = (RadioButton) findViewById(R.id.website);
        cartProducts = (RecyclerView) binding.cartProducts;
        toolbar = (Toolbar) binding.toolbar;
    }

    public void onRadioButtonClicked2(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.creditCard:
                // set inch button to unchecked
                cod.setChecked(!checked);
                /*paytm.setChecked(!checked);*/
                website.setChecked(!checked);
                checkedItem = "credit_card";
                break;
            case R.id.cod:
                // set MM button to unchecked
                creditCard.setChecked(!checked);
                /*paytm.setChecked(!checked);*/
                website.setChecked(!checked);
                checkedItem = "cod";
                break;

            /*case R.id.paytm:
                // set MM button to unchecked
                creditCard.setChecked(!checked);
                cod.setChecked(!checked);
                website.setChecked(!checked);
                checkedItem = "";
                break;*/
            case R.id.website:
                // set MM button to unchecked
                creditCard.setChecked(!checked);
                /*paytm.setChecked(!checked);*/
                cod.setChecked(!checked);
                checkedItem = "website";
                break;
        }
        checkIsCanOrderNow();
    }

    @Override
    public void onDeleteClick(View view, int position) {

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onIncrease(View view, int position, TextView amount, TextView price) {

    }

    @Override
    public void onDecrease(View view, int position, TextView amount, TextView price) {

    }


    private void createOrder() {
        String orderNote = binding.orderNoteEt.getText().toString().trim();
        OrderRequest request = new OrderRequest();
        List<LineItems> products = new ArrayList<>();
        for (int i = 0; i < cartProductsList.size(); i++) {
            products.add(new LineItems(
                    cartProductsList.get(i).getProductId(),
                    controller.getQuantityOfItem(cartProductsList.get(i).getProductId())
            ));
        }
        request.setLineItems(products);
        request.setSetPaid(false);
        request.setBilling(shipping);
        request.setShipping(getShippingFromBilling());
        if (checkedItem.equalsIgnoreCase("cod")) {
            request.setPaymentMethod("cod");
            request.setPaymentMethodTitle("الدفع عند الاستلام");
        }

        int customerId = 0;
        if (sessionManager.isLoggedIn()) {
            customerId = sessionManager.getId();
            request.getBilling().setEmail(sessionManager.getUser().getEmail());
        }

        /*ApiClient.getApiInterface(true).createOrder(request, customerId)
                .enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            Integer id = response.body().getId();

                            controller.deleteAllItems();
                            cartProductsList.clear();
                            adapter.notifyDataSetChanged();

                            setResult(RESULT_OK);

                            if (checkedItem.equalsIgnoreCase("cod")) {
                                Utils.showLongToast("تم انشاء طلبك", activity);
                                dismissLoadingDialog();
                            } else if (checkedItem.equalsIgnoreCase("website")) {
                                processPaymentInWebView(id);
                            } else {
                                dismissLoadingDialog();
                                Utils.showErrorMessage("بوابة دفع غير مدعومة يعتذر الدفع , لاكمال طلبك ", activity);
                            }

                            if (!orderNote.isEmpty()) {
                                createOrderNote(id, orderNote);
                            }

                            dismissLoadingDialog();
                            finish();

                        } else {

                            Order orderResponse = null;
                            try {
                                String errorBody = response.errorBody().string();
                                Gson gson = new Gson();
                                orderResponse = gson.fromJson(errorBody, Order.class);

                                switch (response.code()) {
                                    case 400:
                                        if (orderResponse != null) {
                                            if (orderResponse.getCode().equalsIgnoreCase("woocommerce_rest_invalid_customer_id")) {
                                                Utils.showErrorMessage("تم تسجيل الخروج , المستخدم المسجل حاليا غير صحيح", activity);
                                                Toast.makeText(context, "تم تسجيل الخروج , المستخدم المسجل حاليا غير صحيح", Toast.LENGTH_LONG).show();
                                                sessionManager.logout();
                                                setResult(RESULT_CANCELED);
                                                finish();
                                            } else {
                                                Utils.showErrorMessage("خطأ : " + orderResponse.getMessage(), activity);
                                            }
                                        }
                                        break;
                                    case 500:
                                        Utils.showErrorMessage("هناك خطأ من الخادم", activity);
                                        break;
                                    default:
                                        Utils.showErrorMessage("خطأ غير معروف", activity);
                                        break;
                                }

                            } catch (IOException e) {
                                Utils.showSuccessMessage("خطأ : " + response.code(), activity);
                            }

                            Log.d(TAG, "onResponse: e1" + response.code());
                            dismissLoadingDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        Utils.showErrorMessage("تعذر انشاء طلبك", activity);
                        dismissLoadingDialog();
                    }
                });*/
    }

    private void createOrderNote(Integer id, String orderNote) {
        OrderNoteRequest request = new OrderNoteRequest(orderNote);
       /* ApiClient.getApiInterface(true).createOrderNote(id, request, true, true)
                .enqueue(new Callback<OrderNoteResponse>() {
                    @Override
                    public void onResponse(Call<OrderNoteResponse> call, Response<OrderNoteResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            *//*Toast.makeText(context, "تم ارسال ملاحظة الطلب", Toast.LENGTH_SHORT).show();*//*
                            Utils.showLongToast("تم ارسال ملاحظة الطلب", activity);
                        } else {
                            Utils.showErrorMessage("تعذر ارسال ملاحظة الطلب", activity);
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderNoteResponse> call, Throwable t) {
                        Utils.showErrorMessage("تعذر ارسال ملاحظة الطلب", activity);
                    }
                });*/
    }
/*
    private void processPaymentInWebView(int orderId) {
        ApiClient.getApiInterface(true).getCheckoutUrl(String.valueOf(orderId))
                .enqueue(new Callback<CheckoutUrlResponse>() {
                    @Override
                    public void onResponse(Call<CheckoutUrlResponse> call, Response<CheckoutUrlResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            dismissLoadingDialog();

                            String url = response.body().getCheckoutUrl();
                            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                            CustomTabsIntent customTabsIntent = builder.build();
                            customTabsIntent.launchUrl(activity, Uri.parse(url));

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
                });
    }*/

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

    private void prepareLoadingDialog() {
        if (progress == null) {
            progress = new Dialog(activity);
            progress.setContentView(R.layout.custom_dialog);
            progress.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            progress.setCanceledOnTouchOutside(false);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            dismissLoadingDialog();
            setResult(RESULT_CANCELED);
        }
    }

    private ShippingCommon getShippingFromBilling() {
        return new ShippingCommon(
                shipping.getFirstName(),
                shipping.getLastName(),
                shipping.getCompany(),
                shipping.getAddressOne(),
                shipping.getAddressTow(),
                shipping.getCity(),
                shipping.getPostcode(),
                shipping.getCountry(),
                shipping.getState()/*,
                shipping.getEmail(),
                shipping.getPhone()*/
        );
    }

    @Override
    public void onReceiveAddress(String jsonAddress) {
        Gson gson = new Gson();
        shipping = gson.fromJson(jsonAddress, BillingCommon.class);
        setData();
        if (sessionManager.isLoggedIn()) {
            Customer customer = new Customer();
            customer.setShipping(getShippingFromBilling());

            customer.setBilling(shipping);
            showLoadingDialog();
           /* ApiClient.getApiInterface(true)
                    .updateCustomer(sessionManager.getId(), customer)
                    .enqueue(new Callback<Customer>() {
                        @Override
                        public void onResponse(Call<Customer> call, Response<Customer> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Utils.showSuccessMessage("تم تحديث العنوان", activity);
                                Log.d(TAG, "onResponse: " + response.body().getShipping().getFirstName() + " \n" + response.code() + "\n" + response.body().getId());
                            } else {
                                Utils.showErrorMessage("تم تحديث العنوان على التطبيق ولكن يتعذر تحديثه بقاعدة البيانات على موقعنا", activity);
                            }
                            dismissLoadingDialog();
                        }

                        @Override
                        public void onFailure(Call<Customer> call, Throwable t) {
                            dismissLoadingDialog();
                            Utils.showErrorMessage("تم تحديث العنوان على التطبيق ولكن يتعذر تحديثه بقاعدة البيانات على موقعنا", activity);
                        }
                    });*/
        }
    }

    @Override
    public void onOkButtonClicked() {
        showLoadingDialog();
        createOrder();
    }

}