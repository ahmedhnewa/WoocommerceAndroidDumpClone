package com.alreyada.app.ui.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.alreyada.app.R;
import com.alreyada.app.databinding.ActivitySearchBinding;
import com.alreyada.app.ui.fragment.ViewAllProductsFragment;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private FragmentManager fm;
    private Toolbar toolbar;
    @IdRes
    private int fragmentContainerId = R.id.fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initView();
        initVar();
        initToolbar();
        if (savedInstanceState == null) {
            initSearchFragment();
        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>" + getString(R.string.search) + "</font>"));
        }
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }

    private void initView() {
        toolbar = binding.toolbarLayout.toolbar;
    }

    private void initSearchFragment() {
        final ViewAllProductsFragment searchFragment = new ViewAllProductsFragment();
        fm.beginTransaction().replace(fragmentContainerId, searchFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_all_products, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:

                return false;

            default:
                break;
        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void initVar() {
        fm = getSupportFragmentManager();
    }
}