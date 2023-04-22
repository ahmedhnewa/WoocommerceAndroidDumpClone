package com.alreyada.app.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
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
import com.alreyada.app.databinding.ActivityViewAllProductsBinding;
import com.alreyada.app.ui.fragment.ViewAllProductsFragment;

public class ViewAllProductsActivity extends AppCompatActivity {
    private ActivityViewAllProductsBinding binding;
    private FragmentManager fm;
    @IdRes
    private int fragmentContainerId = R.id.fragmentContainer;
    private Activity activity;
    private Context context;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        binding = ActivityViewAllProductsBinding.inflate(inflater);
        View view = binding.getRoot();
        setContentView(view);

        context = ViewAllProductsActivity.this;
        activity = ViewAllProductsActivity.this;

        intiView();
        initToolbar();

        final ViewAllProductsFragment viewAllProductsFragment = new ViewAllProductsFragment();
        fm = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fm.beginTransaction().replace(fragmentContainerId, viewAllProductsFragment).commit();
        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>" + getString(R.string.view_all_products) + "</font>"));
        }
    }

    private void intiView() {
        toolbar = binding.toolbarLayout.toolbar;
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
                // Not implemented here
                return false;
            case R.id.action_cart:
                requestOpenCartFragment();
                // Do Activity menu item stuff here
                return true;
            case R.id.action_wish_list:
                startActivity(new Intent(context, WishListActivity.class));
                finish();
                // Do Activity menu item stuff here
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            setResult(RESULT_CANCELED);
        }
    }

    private void requestOpenCartFragment() {
        Intent intent = new Intent();
        intent.putExtra("action", "load_cart_fragment");
        setResult(RESULT_OK, intent);
        finish();
    }
}