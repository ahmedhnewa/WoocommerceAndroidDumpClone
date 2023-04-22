package com.alreyada.app.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alreyada.app.R;
import com.alreyada.app.adapter.CartAdapter;
import com.alreyada.app.data.constant.Constants;
import com.alreyada.app.data.preference.AppPreference;
import com.alreyada.app.data.sqlite.DbCartController;
import com.alreyada.app.databinding.FragmentCartBinding;
import com.alreyada.app.model.product.Product;
import com.alreyada.app.ui.activity.CheckoutActivity;
import com.alreyada.app.ui.activity.ProductDetailsActivity;
import com.alreyada.app.util.Utils;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class CartFragment extends Fragment implements CartAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private List<Product> productsList;
    private List<Integer> cartListProductsIds;
    private CartAdapter adapter;
    private FragmentCartBinding binding;
    private Dialog progress;
    private DbCartController controller;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = "CartFragment";
    private Activity activity;
    private Context context;
    private CartFragmentListener listener;
    private boolean isInTask = false;
    private Spanned currencySymbol;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
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

        activity = getActivity();
        context = getContext();

        currencySymbol = Html.fromHtml(AppPreference.getInstance(context).getStoreData().getCurrencySymbol());

        initView();
        intiVar();
        prepareLoadingDialog();
        prepareRecyclerView();

        checkCart();


        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (controller.getCartList() != null && Utils.isNetworkAvailable(getActivity())) {
                    cartListProductsIds.clear();
                    cartListProductsIds.addAll(controller.getCartList());
                    if (cartListProductsIds.size() > 0) {
                        hideNoData();
                        showLayout(binding.cartHeaderActions);
                        binding.totalItemTv.setText(getString(R.string.total_items) + " " + cartListProductsIds.size());
                        isInTask = true;
                        listener.onRequestRefreshCartIcon();
                        getCart();
                        // hide create order btn when loading
                        /*hideLayout(binding.createOrder);*/
                    } else {
                        showNoData();
                        hideLayout(binding.cartHeaderActions);
                        hideLayout(binding.createOrder);
                        setSwipeRefreshing(false);
                    }
                } else {
                    setSwipeRefreshing(false);
                    Utils.showErrorMessage("لايوجد اتصال بالانترنيت", activity);
                }
            }
        });

        binding.createOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*showLoadingDialog();
                createOrder();*/
                if (!isInTask) {
                    Intent intent = new Intent(context, CheckoutActivity.class);
                    Gson gson = new Gson();
                    intent.putExtra("cart_list", gson.toJson(productsList));
                    startActivityForResult(intent, Constants.REQUEST_CODE_CLEAR_CART_AFTER_ORDER_CREATED);
                } else {
                    Toast.makeText(activity, "جاري تحميل البيانات , يرجى الانتظار", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.clearTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isInTask) {
                    controller.deleteAllItems();
                    listener.onRequestRefreshCartIcon();
                    checkCart();
                } else {
                    Toast.makeText(activity, "يرجى الانتظار قبل حذف البيانات", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkCart() {
        if (controller.getCartList() != null && Utils.isNetworkAvailable(getActivity())) {
            cartListProductsIds.clear();
            cartListProductsIds.addAll(controller.getCartList());
            if (cartListProductsIds.size() > 0) {
                hideNoData();
                /*showLayout(binding.cartHeaderActions);*/
                binding.totalItemTv.setText(getString(R.string.total_items) + " " + cartListProductsIds.size());
                isInTask = true;
                getCart();
                binding.createOrder.setVisibility(View.VISIBLE);
            } else {
                showNoData();
                hideLayout(binding.cartHeaderActions);
                binding.createOrder.setVisibility(View.GONE);
            }
        }
    }

    private void showNoData() {
        hideLayout(binding.recyclerView);
        showLayout(binding.noData.rlNoData);
    }

    private void hideNoData() {
        showLayout(binding.recyclerView);
        hideLayout(binding.noData.rlNoData);
    }


    private void prepareRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        adapter = new CartAdapter(getActivity(), productsList, R.layout.item_cart2, false);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(CartFragment.this);
        recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down));
    }

    private void intiVar() {
        productsList = new ArrayList<>();
        cartListProductsIds = new ArrayList<>();
        controller = new DbCartController(getActivity());
    }

    private void initView() {
        recyclerView = binding.recyclerView;
        swipeRefreshLayout = binding.swipe;
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

    private void setSwipeRefreshing(boolean swipeRefreshing) {
        swipeRefreshLayout.setRefreshing(swipeRefreshing);
    }

    private void prepareLoadingDialog() {
        if (progress == null) {
            progress = new Dialog(getActivity());
            progress.setContentView(R.layout.custom_dialog);
            progress.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            progress.setCanceledOnTouchOutside(false);
        }
    }

    private void getCart() {
        setSwipeRefreshing(true);

        /*String ids = Arrays.toString(new int[]{0,1236,1223,1052,882,864,829});*/
        String ids = "";

        // this is way not work for items more than 3
        for (int i = 0; i < cartListProductsIds.size(); i++) {
            String id = String.valueOf(cartListProductsIds.get(i));
            // if the item is the last item
            if (!id.equals(cartListProductsIds.get(cartListProductsIds.size() - 1))) {
                ids += id + ",";
            } else {
                // if not
                ids += id;
            }

        }
/*
        Log.d(TAG, "getCart: " + ApiClient.getApiInterface(true).getSpecificsProducts(ids, 1, 10).request().url().toString());

        ApiClient.getApiInterface(true).getSpecificsProducts(ids, 1, 20)
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            if (!productsList.isEmpty()) {
                                productsList.clear();
                            }

                            if (response.body().size() > 0) {
                                productsList.addAll(response.body());
                                adapter.notifyDataSetChanged();
                                showLayout(binding.createOrder);
                                showLayout(binding.cartHeaderActions);
                                *//*Toast.makeText(activity, "" + response.body().size(), Toast.LENGTH_SHORT).show();*//*
                            } else {
                                showNoData();
                            }

                        } else {
                            Toast.makeText(getActivity(), "فشل", Toast.LENGTH_SHORT).show();
                            binding.createOrder.setVisibility(View.GONE);
                        }
                        isInTask = false;
                        setSwipeRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        isInTask = false;
                        setSwipeRefreshing(false);
                        binding.createOrder.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "فشل", Toast.LENGTH_SHORT).show();
                    }
                });*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDeleteClick(View view, int position) {
        DbCartController controller = new DbCartController(getActivity());
        boolean isShouldReloadCart = false;
        if (cartListProductsIds.get(position).equals(cartListProductsIds.get(cartListProductsIds.size() - 1))) {
            isShouldReloadCart = true;
        } else {
            isShouldReloadCart = false;
        }
        controller.removeItem(productsList.get(position).getProductId());
        cartListProductsIds.remove(position);
        productsList.remove(position);
        if (isShouldReloadCart) {
            checkCart();
        }
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
        intent.putExtra("id", productsList.get(position).getProductId());
//        intent.putExtra(Constants.productDetails, productsList.get(position));
        //intent.putExtra("image_url", arrayList.get(position).getImages().get(0).getSrc());
        startActivity(intent);
    }

    @Override
    public void onIncrease(View view, int position, TextView amount, TextView price) {
        // get product id
        int id = productsList.get(position).getProductId();
        // get qty of the item in Sqlite
        int qty = controller.getQuantityOfItem(id);
        // check if have stock management
        boolean isHaveProductManagement = productsList.get(position).isManageStock();
        int stockQty = -1;
        if (isHaveProductManagement) {
            // get stock qty in woocommerce inventory
            stockQty = productsList.get(position).getStockQuantity();
        }
        // get currentPrice (String)
        String currentPriceStr = productsList.get(position).getPrice();
        // if the price value is number only and is the string is not empty
        if (currentPriceStr != null && !currentPriceStr.isEmpty()) {
            // convert the price from string to int (parse)
            double currentPrice = Double.parseDouble(currentPriceStr);
            // check if there is stock management
            if (stockQty != -1) {

                // check if the qty is not less than stock qty in the inventory
                if (qty < stockQty) {
                    // if condition is true
                    // increase The Qty in Sqlite
                    controller.increaseItem(id);
                    // update the Change
                    qty = controller.getQuantityOfItem(id);
                    amount.setText(String.valueOf(controller.getQuantityOfItem(id)));
                    // update the price after Increase qty in sqlite
                    double endPrice = currentPrice * qty;

                    price.setText("" + df2.format(endPrice) + currencySymbol);
                } else {
                    // in qty in sqlite is less than the qty in inventory in woocommerce
                    Toast.makeText(activity, "نعتذر , الكمية المحددة اكثر من الكمية المتوفرة بالمخزن", Toast.LENGTH_SHORT).show();
                }

            } else {
                // no stock management
                // increase The Qty in Sqlite
                controller.increaseItem(id);
                // update the Change
                qty = controller.getQuantityOfItem(id);
                amount.setText(String.valueOf(controller.getQuantityOfItem(id)));
                // update the price after Increase qty in sqlite
                double endPrice = currentPrice * qty;

                price.setText("" + df2.format(endPrice) + currencySymbol);
            }
        } else {
            Utils.showShortToast("عذرا لايوجد سعر لهذة المنتج حاليا", context);
        }
    }


    @Override
    public void onDecrease(View view, int position, TextView amount, TextView price) {
        // get product id
        int id = productsList.get(position).getProductId();
        // init qty variable && get qty of the item in Sqlite
        int qty = controller.getQuantityOfItem(id);
        // get currentPrice (String)
        String currentPriceStr = productsList.get(position).getPrice();
        // if the price value is number only and is the string is not empty
        if (currentPriceStr != null && !currentPriceStr.isEmpty()) {
            // convert the price from string to int (parse)
            double currentPrice = Double.parseDouble(currentPriceStr);
            if (qty > 1) {
                // increase The Qty in Sqlite
                controller.decreaseItem(id);
                // update the Change
                qty = controller.getQuantityOfItem(id);
                amount.setText(String.valueOf(controller.getQuantityOfItem(id)));
                // update the price after Increase qty in sqlite
                double endPrice = (currentPrice * qty);
                price.setText("" + df2.format(endPrice) + currencySymbol);
            }
        } else {
            Toast.makeText(activity, "السعر لهذة المنتج غير مضاف بعد", Toast.LENGTH_SHORT).show();
        }
    }


    public interface CartFragmentListener {
        void onRequestRefreshCartIcon();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (CartFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CartFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_CLEAR_CART_AFTER_ORDER_CREATED) {
            if (resultCode == RESULT_OK) {
                hideLayout(binding.createOrder);
                hideLayout(binding.cartHeaderActions);
                cartListProductsIds.clear();
                productsList.clear();
                listener.onRequestRefreshCartIcon();
                showNoData();
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
