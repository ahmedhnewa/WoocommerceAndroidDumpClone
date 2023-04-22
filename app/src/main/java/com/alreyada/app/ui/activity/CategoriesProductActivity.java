package com.alreyada.app.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alreyada.app.R;
import com.alreyada.app.adapter.ListProductsAdapter;
import com.alreyada.app.model.product.Product;
import com.alreyada.app.util.GridSpacingItemDecoration;
import com.alreyada.app.util.Tools;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class CategoriesProductActivity extends AppCompatActivity {
    private RecyclerView rcvProducts;
    private List<Product> productList;
    private ListProductsAdapter adapter = null;
    private Dialog progress;
    private Toolbar toolbar;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_product);

        rcvProducts = findViewById(R.id.rcvProducts);
        toolbar = findViewById(R.id.toolbar);

        rcvProducts.setLayoutManager(new LinearLayoutManager(this));
        rcvProducts.setHasFixedSize(true);
        rcvProducts.setNestedScrollingEnabled(false);
        int spanCount = 3; // 3 columns
        int spacing = 10; // 15px
        boolean includeEdge = false;
        rcvProducts.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        rcvProducts.setFocusable(false);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        productList = new ArrayList<>();
        productList.clear();

        adapter = new ListProductsAdapter(CategoriesProductActivity.this,
                productList, R.layout.item_products);
        rcvProducts.setAdapter(adapter);

        Tools.setSystemBarColor(this, R.color.grey_3);

        //change system bar icon color
        Tools.setSystemBarLight(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        if (progress == null) {
            progress = new Dialog(this);
            progress.setContentView(R.layout.custom_dialog);
            progress.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            progress.setCanceledOnTouchOutside(false);
        }


        getProductsCategories();
    }

    private void getProductsCategories() {
        showLoadingDialog();
   /*     ApiClient.getApiInterface(true).getProductsOfCategories(id)
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            if (!productList.isEmpty()) {
                                productList.clear();
                            }

                            productList.addAll(response.body());

                            if (productList.size() > 0) {
                                adapter.notifyDataSetChanged();
                            }

                        } else {
                            showErrorMessage("اسستجابة خاطئة من الخادم");
                        }
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        dismissLoadingDialog();
                        showErrorMessage("تعذر الاتصال بالخادم " + t.getMessage());
                    }
                });*/


    }

    private void showErrorMessage(String msg) {
        Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);
        View sbView = snackBar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.tomato));
        snackBar.setTextColor(Color.WHITE);
        snackBar.show();
    }

    private void showSuccessMessage(String msg) {
        Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}