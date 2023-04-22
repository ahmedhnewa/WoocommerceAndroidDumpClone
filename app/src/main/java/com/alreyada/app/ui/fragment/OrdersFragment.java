package com.alreyada.app.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alreyada.app.R;
import com.alreyada.app.adapter.OrdersAdapter;
import com.alreyada.app.data.preference.AppPreference;
import com.alreyada.app.data.preference.PrefKey;
import com.alreyada.app.data.preference.SessionManager;
import com.alreyada.app.databinding.FragmentOrdersBinding;
import com.alreyada.app.model.orders.Order;
import com.alreyada.app.ui.activity.OrderDetailsActivity;
import com.alreyada.app.util.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {
    private FragmentOrdersBinding binding;
    private Dialog progress;
    private RecyclerView ordersRv;
    private List<Order> orderList;
    private OrdersAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;
    private Activity activity;
    private SessionManager sessionManager;
    private ProgressBar progressBar;
    private int page = 1;
    private int itemPerPage = 10;
    private boolean userScrolled = true;
    private boolean showOneTime = true;
    private boolean isInTask = false;
    private boolean isReachedLastProducts = false;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    private static final String TAG = "OrdersFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();
        activity = getActivity();

        initView();
        intiVar();
        prepareRecyclerView();
        prepareLoadingDialog();

        if (Utils.isNetworkAvailable(context)) {
            showLayout(progressBar);
            getOrders(true, page, itemPerPage);
            implementScrollListener();
        } else {
            hideLayout(progressBar);
        }

        implementOnSwipeToRefreshListener();
    }

    private void implementScrollListener() {
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                if (userScrolled && (visibleItemCount + pastVisibleItems) == totalItemCount) {
                    userScrolled = false;

                    if (!isInTask) {
                        if (!isReachedLastProducts) {
                            page++;
                            showLayout(progressBar);
                            getOrders(false, page, itemPerPage);
                        } else {
                            if (showOneTime) {
                                Toast.makeText(context, getString(R.string.no_more_products), Toast.LENGTH_SHORT).show();
                                showOneTime = false;
                            }
                        }
                    } else {
                        Toast.makeText(context, "الرجاء الانتظار لأكمال العملية الحالية", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private void implementOnSwipeToRefreshListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utils.isNetworkAvailable(context)) {

                    if (!isInTask) {
                        page = 1;
                        getOrders(true, page, itemPerPage);
                    } else {
                        Toast.makeText(context, "الرجاء الانتظار لأكمال العملية الحالية", Toast.LENGTH_SHORT).show();
                        setSwipeRefreshing(false);
                    }


                } else {
                    setSwipeRefreshing(false);
                }
            }
        });
    }

    private void getOrders(boolean isShouldClearList, int page, int itemPerPage) {
      /*  ApiClient.getApiInterface(true)
                .getUserOrders(sessionManager.getId(), page, itemPerPage)
                .enqueue(new Callback<List<Order>>() {
                    @Override
                    public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            if (!orderList.isEmpty() && isShouldClearList) {
                                orderList.clear();
                            }

                            if (response.body().size() > 0) {
                                orderList.addAll(response.body());
                                adapter.notifyDataSetChanged();
                                saveData(orderList);
                                // hide progress bar and set the refreshing of (swipe layout) to false
                                hideLayout(progressBar);
                                setSwipeRefreshing(false);
                            } else {
                                isReachedLastProducts = true;
                            }

                        } else {
                            Gson gson = new Gson();
                            Order order = null;
                            try {
                                order = gson.fromJson(response.errorBody().string(), Order.class);
                                Utils.showErrorMessage("تعذر تحميل الطلبات : " + order.getMessage(), activity);
                            } catch (IOException e) {
                                e.printStackTrace();
                                Utils.showErrorMessage("تعذر تحميل الطلبات : " + response.code(), activity);
                            }
                        }
                        setSwipeRefreshing(false);
                        hideLayout(progressBar);
                    }

                    @Override
                    public void onFailure(Call<List<Order>> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        setSwipeRefreshing(false);
                        hideLayout(progressBar);
                    }
                });*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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

    private void prepareRecyclerView() {
        ordersRv.setHasFixedSize(true);
        adapter = new OrdersAdapter(context, orderList);
        ordersRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OrdersAdapter.OrdersAdapterListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Order clickedItem = orderList.get(position);
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("id", clickedItem.getId());
                Gson gson = new Gson();
                intent.putExtra("order_details", gson.toJson(clickedItem));
                startActivity(intent);
            }
        });
    }

    private void intiVar() {
        orderList = new ArrayList<>();
        sessionManager = new SessionManager(context);
    }

    private void initView() {
        swipeRefreshLayout = binding.swipe;
        ordersRv = binding.recyclerView;
        progressBar = binding.progressCircular;
    }

    private void saveData(List<Order> orders) {
        Gson gson = new Gson();
        String json = gson.toJson(orders);
        if (!json.equals("[]") && !json.isEmpty() && json != null) {
            AppPreference.getInstance(context).setString(PrefKey.USER_ORDERS, json);
        }
    }

}
