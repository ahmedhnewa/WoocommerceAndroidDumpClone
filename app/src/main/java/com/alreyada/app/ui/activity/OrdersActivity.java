package com.alreyada.app.ui.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.alreyada.app.R;
import com.alreyada.app.databinding.ActivityOrdersBinding;
import com.alreyada.app.ui.fragment.OrdersFragment;

public class OrdersActivity extends AppCompatActivity {
    private ActivityOrdersBinding binding;
    private Toolbar toolbar;
    private FragmentManager fm;
    @IdRes
    private int fragmentContainer = R.id.fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        binding = ActivityOrdersBinding.inflate(inflater);
        View view = binding.getRoot();
        setContentView(view);

        initView();
        initVar();
        initToolbar();
        if (savedInstanceState == null) {
            initOrderFragment();
        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>" + "قائمة الطلبات" + "</font>"));
        }
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }

    private void initView() {
        toolbar = binding.toolbarLayout.toolbar;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void initOrderFragment() {
        final OrdersFragment ordersFragment = new OrdersFragment();
        fm.beginTransaction().replace(fragmentContainer, ordersFragment).commit();
    }

    private void initVar() {
        fm = getSupportFragmentManager();
    }
}