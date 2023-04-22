package com.alreyada.app.ui.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.alreyada.app.R;
import com.alreyada.app.databinding.ActivityWishListBinding;
import com.alreyada.app.ui.fragment.WishListFragment;

public class WishListActivity extends AppCompatActivity {
    private FragmentManager fm;
    private ActivityWishListBinding binding;
    private Toolbar toolbar;
    @IdRes
    private int fragmentContainerId = R.id.fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWishListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initView();
        initVar();
        initToolbar();
        if (savedInstanceState == null) {
            initWishListFragment();
        }

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>" + getString(R.string.wishlist) + "</font>"));
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

    private void initWishListFragment() {
        final WishListFragment wishListFragment = new WishListFragment();
        fm.beginTransaction().replace(fragmentContainerId, wishListFragment).commit();
    }

    private void initVar() {
        fm = getSupportFragmentManager();
    }
}