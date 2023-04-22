package com.alreyada.app.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alreyada.app.R;
import com.alreyada.app.adapter.ListProductsAdapter;
import com.alreyada.app.data.constant.Constants;
import com.alreyada.app.data.preference.AppPreference;
import com.alreyada.app.data.preference.PrefKey;
import com.alreyada.app.data.preference.SessionManager;
import com.alreyada.app.databinding.FragmentHomeBinding;
import com.alreyada.app.model.product.Product;
import com.alreyada.app.ui.activity.ChatActivity;
import com.alreyada.app.ui.activity.ProductDetailsActivity;
import com.alreyada.app.ui.activity.ViewAllProductsActivity;
import com.alreyada.app.ui.activity.admin.AdminActivity;
import com.alreyada.app.ui.dialog.EnterNameDialog;
import com.alreyada.app.util.FirebaseAuthentication;
import com.alreyada.app.util.Utils;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    private RecyclerView featuredProducts, onSaleProducts, newestProducts, recentProducts;
    private ListProductsAdapter featuredAdapter, onSaleAdapter, newestAdapter, recentAdapter;
    private List<Product> featuredArrayList, onSaleArrayList, newestArrayList, recentArrayList;
    private TextView featuredProductsTv, onSaleProductsTv;
    private List<SlideModel> sliderItems;
    private FragmentHomeBinding binding;
    private Context context;
    private Activity activity;
    private HomeFragmentListener listener;
    private SessionManager sessionManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
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

        context = getContext();
        activity = getActivity();

        /*if (products.getPriceHtml().contains("&#36;")) {
            AppPreference.getInstance(mContext).setCurrencySymbol("&#36;");
        }*/

        initView();
        initVar();
        prepareRecyclerView();
        setOnNavigationItemSelectedListener();

        if (Utils.isNetworkAvailable(activity)) {
/*            getSliderImages();
            getOnSaleProducts();
            getNewestProducts();
            getRecentProducts();
            getBanners();*/
        }

        binding.newestMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ViewAllProductsActivity.class);
                intent.putExtra("type", "newestProducts");
                startActivityForResult(intent, Constants.REQUEST_CODE_OPEN_CART_AFTER_CLICK_MENU_CART_IN_VIEW_ALL_PRODUCTS);
            }
        });

        binding.featuredMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ViewAllProductsActivity.class);
                intent.putExtra("type", "featuredProducts");
                startActivityForResult(intent, Constants.REQUEST_CODE_OPEN_CART_AFTER_CLICK_MENU_CART_IN_VIEW_ALL_PRODUCTS);
            }
        });

        binding.onSaleMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ViewAllProductsActivity.class);
                intent.putExtra("type", "onSaleProducts");
                startActivityForResult(intent, Constants.REQUEST_CODE_OPEN_CART_AFTER_CLICK_MENU_CART_IN_VIEW_ALL_PRODUCTS);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!Utils.isNetworkAvailable(context)) {
                    binding.swipe.setRefreshing(false);
                }/*
                getSliderImages();
                getOnSaleProducts();
                getNewestProducts();
                getBanners();
                getRecentProducts();*/
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.swipe.setRefreshing(false);
                    }
                }, 4000);
            }
        });
    }

    private void setOnNavigationItemSelectedListener() {
        NavigationView navigationView = activity.findViewById(R.id.nav_view);

        if (sessionManager.isLoggedIn()) {
            if (sessionManager.getUser() != null) {
                navigationView.getMenu().findItem(R.id.nav_admin).setVisible(sessionManager.getUser().getRole().equals("administrator"));
            } else {
                navigationView.getMenu().findItem(R.id.nav_admin).setVisible(false);
            }
        } else {
            navigationView.getMenu().findItem(R.id.nav_admin).setVisible(false);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_account:
                        listener.onRequestLoadAccountFragment();
                        break;
                    case R.id.nav_view_all_products:
                        Intent intent = new Intent(context, ViewAllProductsActivity.class);
                        intent.putExtra("type", "");
                        startActivityForResult(intent, Constants.REQUEST_CODE_OPEN_CART_AFTER_CLICK_MENU_CART_IN_VIEW_ALL_PRODUCTS);
                        break;
                    case R.id.nav_chat:
                        if (FirebaseAuthentication.getInstance(context).isLoggedIn()) {
                            startActivity(new Intent(context, ChatActivity.class));
                        } else {
                            EnterNameDialog enterNameDialog = new EnterNameDialog();
                            enterNameDialog.show(getActivity().getSupportFragmentManager(), "enter name dialog");
                        }
                        break;
                    case R.id.nav_admin:
                        startActivity(new Intent(context, AdminActivity.class));
                        break;
                }
                DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawerLayout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

/*

    private void getBanners() {
        reference.child(Constants.BANNERS_KEY).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot != null) {

                    if (snapshot.child(Constants.BANNER_1).exists()) {
                        showLayout(binding.banner1Layout);
                        showLayout(binding.todayDeal);
                        Picasso.get()
                                .load(snapshot.child(Constants.BANNER_1).child("imageUrl").getValue(String.class))
                                .into(binding.banner1Iv);
                        binding.banner1Layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Utils.openUrl(snapshot.child(Constants.BANNER_1).child("link").getValue(String.class), activity);
                            }
                        });
                    } else {
                        hideLayout(binding.todayDeal);
                        hideLayout(binding.banner1Layout);
                    }


                    if (snapshot.child(Constants.BANNER_2).exists()) {
                        showLayout(binding.banner2Layout);
                        Picasso.get()
                                .load(snapshot.child(Constants.BANNER_2).child("imageUrl").getValue(String.class))
                                .into(binding.banner2Iv);
                        binding.banner2Layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Utils.openUrl(snapshot.child(Constants.BANNER_2).child("link").getValue(String.class), activity);
                            }
                        });
                    } else {
                        hideLayout(binding.banner2Layout);
                    }

                } else {
                    binding.banner1Layout.setVisibility(View.GONE);
                    binding.todayDealTv.setVisibility(View.GONE);
                    binding.banner2Layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (Utils.isNetworkAvailable(context)) {
                    hideLayout(binding.banner2Layout);
                    hideLayout(binding.todayDeal);
                    hideLayout(binding.banner1Layout);
                }
            }
        });
    }


    private void getSliderImages() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.SLIDER_KEY);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!sliderItems.isEmpty()) {
                    sliderItems.clear();
                }

                if (snapshot.exists()) {
                    showLayout(binding.imageSlider);
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        SliderItem model = ds.getValue(SliderItem.class);
                        //new SliderItem(ds.child("imageUrl").getValue(String.class), ds.child("title").getValue(String.class));
                        sliderItems.add(new SlideModel(model.getImageUrl(), model.getDescription(), ScaleTypes.CENTER_CROP));
                    }
                    binding.imageSlider.setImageList(sliderItems);
                } else {
                    hideLayout(binding.imageSlider);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (Utils.isNetworkAvailable(context)) {
                    hideLayout(binding.imageSlider);
                }
            }
        });
    }
*/


    private void prepareRecyclerView() {
        featuredProducts.setHasFixedSize(true);
        onSaleProducts.setHasFixedSize(true);
        newestProducts.setHasFixedSize(true);
        recentProducts.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity
                , LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(activity
                , LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(
                activity, RecyclerView.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(
                activity, RecyclerView.HORIZONTAL, false
        );

        featuredProducts.setLayoutManager(layoutManager);
        onSaleProducts.setLayoutManager(layoutManager1);
        newestProducts.setLayoutManager(layoutManager2);
        recentProducts.setLayoutManager(layoutManager3);

        if (loadDataFeaturedProducts() != null) {
            featuredArrayList.addAll(loadDataFeaturedProducts());
            showLayout(binding.featuredProducts);
            showLayout(binding.rvFeaturedProducts);
        }

        if (loadDataOnSaleProducts() != null) {
            onSaleArrayList.addAll(loadDataOnSaleProducts());
            showLayout(binding.onSaleProduct);
            showLayout(binding.onSaleProductsLayout);
        }

        if (loadDataNewestProducts() != null) {
            newestArrayList.addAll(loadDataNewestProducts());
            showLayout(binding.newestProducts);
            showLayout(binding.rvNewestProducts);
        }

        featuredAdapter = new ListProductsAdapter(activity, featuredArrayList, R.layout.item_products_2);
        onSaleAdapter = new ListProductsAdapter(activity, onSaleArrayList, R.layout.item_products_2);
        newestAdapter = new ListProductsAdapter(activity, newestArrayList, R.layout.item_products_2);
        recentAdapter = new ListProductsAdapter(activity, recentArrayList, R.layout.item_products_2);

        featuredAdapter.setUseDefaultItemClickListener(false);
        onSaleAdapter.setUseDefaultItemClickListener(false);
        newestAdapter.setUseDefaultItemClickListener(false);
        recentAdapter.setUseDefaultItemClickListener(false);

        featuredProducts.setAdapter(featuredAdapter);
        onSaleProducts.setAdapter(onSaleAdapter);
        newestProducts.setAdapter(newestAdapter);
        recentProducts.setAdapter(recentAdapter);

        getRecentProducts();
        Log.d(TAG, "prepareRecyclerView: " + recentArrayList.size());

        featuredAdapter.setOnItemClickListener(new ListProductsAdapter.ListProductsAdapterListener() {
            @Override
            public void onItemClick(View view, int position) {
                Product product = featuredArrayList.get(position);
                Intent intent = new Intent(context, ProductDetailsActivity.class);
//                intent.putExtra("id", product.getId());
//                intent.putExtra(Constants.productDetails, product);
                startActivityForResult(intent, Constants.REQUEST_CODE_WHEN_ADD_PRODUCT_TO_CART);
            }
        });

        onSaleAdapter.setOnItemClickListener(new ListProductsAdapter.ListProductsAdapterListener() {
            @Override
            public void onItemClick(View view, int position) {
                Product product = onSaleArrayList.get(position);
                Intent intent = new Intent(context, ProductDetailsActivity.class);
//                intent.putExtra("id", product.getId());
//                intent.putExtra(Constants.productDetails, product);
                startActivityForResult(intent, Constants.REQUEST_CODE_WHEN_ADD_PRODUCT_TO_CART);
            }
        });

        newestAdapter.setOnItemClickListener(new ListProductsAdapter.ListProductsAdapterListener() {
            @Override
            public void onItemClick(View view, int position) {
                Product product = newestArrayList.get(position);
                Intent intent = new Intent(context, ProductDetailsActivity.class);
//                intent.putExtra("id", product.getId());
//                intent.putExtra(Constants.productDetails, product);
                startActivityForResult(intent, Constants.REQUEST_CODE_WHEN_ADD_PRODUCT_TO_CART);
            }
        });

        recentAdapter.setOnItemClickListener(new ListProductsAdapter.ListProductsAdapterListener() {
            @Override
            public void onItemClick(View view, int position) {
                Product product = recentArrayList.get(position);
                Intent intent = new Intent(context, ProductDetailsActivity.class);
//                intent.putExtra("id", product.getId());
//                intent.putExtra(Constants.productDetails, product);
                startActivityForResult(intent, Constants.REQUEST_CODE_WHEN_ADD_PRODUCT_TO_CART);
            }
        });
    }

    private void getRecentProducts() {
        if (loadDataRecentProducts() != null) {

            if (loadDataRecentProducts().size() > 0) {
                showLayout(binding.recentProducts);
                showLayout(binding.rlRecentProduct);
                recentArrayList.addAll(loadDataRecentProducts());
                recentAdapter.notifyDataSetChanged();
            } else {
                hideLayout(binding.recentProducts);
                hideLayout(binding.rlRecentProduct);
            }

        } else {
            hideLayout(binding.recentProducts);
            hideLayout(binding.rlRecentProduct);
        }
    }


    private void initVar() {
        featuredArrayList = new ArrayList<>();
        onSaleArrayList = new ArrayList<>();
        sliderItems = new ArrayList<>();
        newestArrayList = new ArrayList<>();
        recentArrayList = new ArrayList<>();
        sessionManager = new SessionManager(context);
    }

    private void initView() {
        featuredProducts = binding.featuredProducts;
        onSaleProducts = binding.onSaleProduct;
        featuredProductsTv = binding.featuredProductsTv;
        onSaleProductsTv = binding.onSaleProductsTv;
        newestProducts = binding.newestProducts;
        recentProducts = binding.recentProducts;
        swipeRefreshLayout = binding.swipe;
    }


    private void getOnSaleProducts() {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("on_sale", true);
        /*ApiClient.getApiInterface(true, getContext()).getAllProducts(1, 5, queryMap)
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            if (!onSaleArrayList.isEmpty()) {
                                onSaleArrayList.clear();
                            }
                            onSaleArrayList.addAll(response.body());

                            if (onSaleArrayList.size() > 0) {
                                showLayout(binding.onSaleProduct);
                                showLayout(binding.onSaleProductsLayout);
                                onSaleAdapter.notifyDataSetChanged();
                                saveData(onSaleArrayList, PrefKey.onSaleProductsArrayList);
                            } else {
                                *//*onSaleProductsTv.setText("المنتجات الاكثر مبيعا " + "(غير موجود حاليا)");*//*
                                hideLayout(binding.onSaleProductsLayout);
                                hideLayout(binding.onSaleProduct);
                            }

                        } else {
                            try {
                                Log.d(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            hideLayout(binding.onSaleProductsLayout);
                            hideLayout(binding.onSaleProduct);
                            if (response.code() == 503) {
                                Utils.showErrorMessage("الخدمة تحت الصيانة حاليا , الرجاء المحاولة بعد دقيقة", activity);
                            }
                        }
                        getFeaturedProducts();
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        if (Utils.isNetworkAvailable(context)) {
                            hideLayout(binding.onSaleProductsLayout);
                            hideLayout(binding.onSaleProduct);
                            showErrorMessage("تعذر تحميل المنتجات الاكثر مبيعا");
                        }
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });*/

    }
/*
    private void getNewestProducts() {
        Map<String, Object> queryMap = new HashMap<>();
        ApiClient.getApiInterface(true)
                .getAllProducts(1, 5, queryMap)
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            if (!newestArrayList.isEmpty()) {
                                newestArrayList.clear();
                            }

                            newestArrayList.addAll(response.body());

                            if (newestArrayList.size() > 0) {
                                showLayout(binding.newestProducts);
                                showLayout(binding.rvNewestProducts);
                                newestAdapter.notifyDataSetChanged();
                                saveData(newestArrayList, PrefKey.newestProductsArrayList);
                            } else {
                                hideLayout(binding.rvNewestProducts);
                                hideLayout(binding.newestProducts);
                            }

                        } else {
                            binding.newestProductsTv.append("(تعذر تحميل) ");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        hideLayout(binding.rvNewestProducts);
                        hideLayout(binding.newestProducts);
                        binding.newestProductsTv.append("(تعذر تحميل) ");
                    }
                });
    }

    private void getFeaturedProducts() {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("featured", true);
        ApiClient.getApiInterface(true).getAllProducts(1, 5, queryMap)
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            if (!featuredArrayList.isEmpty()) {
                                featuredArrayList.clear();
                            }

                            featuredArrayList.addAll(response.body());

                            if (featuredArrayList.size() > 0) {
                                showLayout(binding.featuredProducts);
                                showLayout(binding.rvFeaturedProducts);
                                featuredAdapter.notifyDataSetChanged();
                                saveData(featuredArrayList, PrefKey.featuredProductsArrayList);
                            } else {
                                *//*featuredProductsTv.setText("المنتجات الاكثر مبيعا " + "(غير موجود حاليا)");*//*
                                hideLayout(binding.rvFeaturedProducts);
                                hideLayout(featuredProducts);
                            }

                        } else {
                            try {
                                Log.d(TAG, "onFailure: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        if (Utils.isNetworkAvailable(context)) {
                            hideLayout(binding.rvFeaturedProducts);
                            hideLayout(featuredProducts);
                        }
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });

    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_WHEN_ADD_PRODUCT_TO_CART) {
            if (resultCode == RESULT_OK) {
                listener.onRequestRefreshCart();
            }
        }
        if (requestCode == Constants.REQUEST_CODE_OPEN_CART_AFTER_CLICK_MENU_CART_IN_VIEW_ALL_PRODUCTS) {
            if (resultCode == RESULT_OK) {
                String action = null;
                if (data != null) {
                    action = data.getStringExtra("action");
                }

                if (action != null && action.equalsIgnoreCase("load_cart_fragment")) {
                    // load cart fragment
                    listener.onRequestLoadCartFragment();
                }

            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    private void showErrorMessage(String msg) {
        Snackbar snackBar = Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);
        View sbView = snackBar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.tomato));
        snackBar.setTextColor(Color.WHITE);
        snackBar.show();
    }

    private void showSuccessMessage(String msg) {
        Snackbar snackBar = Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);
        View sbView = snackBar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.primaryColor));
        snackBar.setTextColor(Color.WHITE);
        snackBar.show();
    }

    private void saveData(List<Product> productList, String prefKey) {
        Gson gson = new Gson();
        String json = gson.toJson(productList);
        AppPreference.getInstance(activity).setString(prefKey, json);
    }

    private List<Product> loadDataFeaturedProducts() {
        Gson gson = new Gson();
        String json = AppPreference.getInstance(context).getString(PrefKey.featuredProductsArrayList);
        Type type = new TypeToken<List<Product>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    private List<Product> loadDataOnSaleProducts() {
        Gson gson = new Gson();
        String json = AppPreference.getInstance(activity).getString(PrefKey.onSaleProductsArrayList);
        Type type = new TypeToken<List<Product>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    private List<Product> loadDataNewestProducts() {
        Gson gson = new Gson();
        String json = AppPreference.getInstance(activity).getString(PrefKey.newestProductsArrayList);
        Type type = new TypeToken<List<Product>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    private List<Product> loadDataRecentProducts() {
        Gson gson = new Gson();
        String json = AppPreference.getInstance(activity).getString(PrefKey.recentProductsArrayList);
        Log.d(TAG, "loadDataRecentProducts: " + json);
        Type type = new TypeToken<List<Product>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    private void addBottomDotsImageSlider(LinearLayout layout_dots, int size, int current) {
        ImageView[] dots = new ImageView[size];

        layout_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(activity);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(ContextCompat.getColor(activity, R.color.overlay_dark_10), PorterDuff.Mode.SRC_ATOP);
            layout_dots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current].setColorFilter(ContextCompat.getColor(activity, R.color.grey_60), PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface HomeFragmentListener {
        void onRequestRefreshCart();

        void onRequestLoadCartFragment();

        void onRequestLoadAccountFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (HomeFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement HomeFragmentListener in DashboardActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listener = null;
    }

    private void showLayout(View lb) {
        if (lb.getVisibility() == View.GONE || lb.getVisibility() == View.INVISIBLE)
            lb.setVisibility(View.VISIBLE);
    }

    private void hideLayout(View lb) {
        if (lb.getVisibility() == View.VISIBLE) lb.setVisibility(View.GONE);
    }

    private void toggleRefreshing(boolean enabled) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(enabled);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.action_search).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(context, ViewAllProductsActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE_OPEN_CART_AFTER_CLICK_MENU_CART_IN_VIEW_ALL_PRODUCTS);
                return true;
            default:
                break;
        }
        return false;
    }
}
