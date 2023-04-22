package com.alreyada.app.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alreyada.app.R;
import com.alreyada.app.databinding.ActivityChatBinding;
import com.alreyada.app.ui.fragment.ChatFragment;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    private ActivityChatBinding binding;
    private Toolbar toolbar;
    private FragmentManager fm;
    @IdRes
    private int fragmentContainerId = R.id.fragmentContainer;
    private Context context;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        context = ChatActivity.this;
        activity = ChatActivity.this;

        initView();
        initVar();
        initToolbar();
        if (savedInstanceState == null) {
            initFragment();
        }

        SwipeRefreshLayout swipeRefreshLayout = new SwipeRefreshLayout(getApplicationContext());
//        swipeRefreshLayout.setEnabled();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void initView() {
        toolbar = binding.toolbarLayout.toolbar;
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>" + "تواصل معنا" + "</font>"));
        }
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }

    private void initFragment() {
        final ChatFragment chatFragment = new ChatFragment();
        fm.beginTransaction().replace(fragmentContainerId,chatFragment).commit();
    }

    private void initVar() {
        fm = getSupportFragmentManager();
    }
}