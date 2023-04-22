package com.alreyada.app.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alreyada.app.R;
import com.alreyada.app.adapter.WishListAdapter;
import com.alreyada.app.data.sqlite.DbCartController;
import com.alreyada.app.data.sqlite.DbWishListController;
import com.alreyada.app.databinding.FragmentWishListBinding;
import com.alreyada.app.model.product.Product;
import com.alreyada.app.ui.activity.ProductDetailsActivity;
import com.alreyada.app.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class WishListFragment extends Fragment implements WishListAdapter.OnItemClickListener {
    private FragmentWishListBinding binding;
    private DbWishListController dbWishListController;
    private DbCartController dbCartController;
    private List<Product> wishList;
    private List<Integer> wishListProductsIds;
    private WishListAdapter adapter;
    private RecyclerView wishListProducts;
    private Context context;
    private Activity activity;
    private LinearLayout noData;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWishListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();
        activity = getActivity();

        initView();
        initVar();
        prepareRecyclerView();

        checkWishList();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkWishList();
            }
        });

    }

    private void checkWishList() {
        if (dbWishListController.getWishList() != null && Utils.isNetworkAvailable(context)) {

            wishListProductsIds.addAll(dbWishListController.getWishList());

            if (wishListProductsIds.size() > 0) {
                setSwipeRefreshing(true);
                hideNoData();
                getWishList();
            } else {
                // noData
                showNoData();
                setSwipeRefreshing(false);
            }

        } else {
            // usually there is no data or thee is problem with sqlite database
            showNoData();
            setSwipeRefreshing(false);
        }

    }

    private void getWishList() {

        String ids = "";
        for (int i = 0; i < wishListProductsIds.size(); i++) {
            String id = String.valueOf(wishListProductsIds.get(i));
            ids += id + ",";
        }

      /*  ApiClient.getApiInterface(true).getSpecificsProducts(ids, 1, 10)
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            if (!wishList.isEmpty()) {
                                wishList.clear();
                            }

                            wishList.addAll(response.body());

                            if (wishList.size() > 0) {
                                hideNoData();
                                adapter.notifyDataSetChanged();
                            } else {
                                showNoData();
                            }

                        } else {
                            showNoData();
                            setSwipeRefreshing(false);
                            Utils.showErrorMessage("تعذر جلب بيانات القائمة", activity);
                        }
                        setSwipeRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        setSwipeRefreshing(false);
                    }
                });*/
    }

    private void prepareRecyclerView() {
        wishListProducts.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        wishListProducts.setLayoutManager(layoutManager);
        adapter = new WishListAdapter(context, wishList, R.layout.item_wishlist);
        wishListProducts.setAdapter(adapter);
        adapter.setOnItemClickListener(WishListFragment.this);
    }

    private void initVar() {
        dbCartController = new DbCartController(context);
        dbWishListController = new DbWishListController(context);
        wishList = new ArrayList<>();
        wishListProductsIds = new ArrayList<>();
    }

    private void initView() {
        wishListProducts = binding.wishList;
        noData = binding.noData.rlNoData;
        swipeRefreshLayout = binding.swipe;
    }

    private void hideNoData() {
        // hide no data linear layout
        if (noData.getVisibility() == View.VISIBLE) {
            noData.setVisibility(View.INVISIBLE);
        }
        // show wishlist products
        if (wishListProducts.getVisibility() == View.GONE || wishListProducts.getVisibility() == View.INVISIBLE) {
            wishListProducts.setVisibility(View.VISIBLE);
        }
    }

    private void showNoData() {
        // show no data linear layout
        if (noData.getVisibility() == View.GONE || noData.getVisibility() == View.INVISIBLE) {
            noData.setVisibility(View.VISIBLE);
        }
        // hide wishlist products
        if (wishListProducts.getVisibility() == View.VISIBLE) {
            wishListProducts.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDeleteClick(View view, int position) {
        int id = wishList.get(position).getProductId();
        if (dbWishListController.isAlreadyExist(id)) {

            // remove it from wishlist sqlite
            dbWishListController.removeItem(id);

            // remove it from arrayList
            wishList.remove(position);
            wishListProductsIds.remove(position);
            // notify adapter
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(context, "العنصر الذي تحاول حذفه هو غير موجود", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
        intent.putExtra("id", wishList.get(position).getProductId());
//        intent.putExtra(Constants.productDetails, wishList.get(position));
        startActivity(intent);
    }

    @Override
    public void onAddToCartClick(View view, int position) {
        int id = wishList.get(position).getProductId();
        if (!dbCartController.isAlreadyExist(id)) {
            // add item to the cart sqlite
            dbCartController.addToCart(id, 1);
            // remove it from wishlist sqlite
            dbWishListController.removeItem(id);
            // remove it from arrayList
            wishList.remove(position);
            wishListProductsIds.remove(position);
            // notify adapter
            adapter.notifyDataSetChanged();
            Utils.showLongToast("تم نقل المنتج للسلة , لتغير الكمية الرجاء الذهاب للسلة", context);
        } else {
            Toast.makeText(context, "موجود بالسلة", Toast.LENGTH_SHORT).show();
        }
    }

    private void setSwipeRefreshing(boolean swipeRefreshing) {
        swipeRefreshLayout.setRefreshing(swipeRefreshing);
    }
}
