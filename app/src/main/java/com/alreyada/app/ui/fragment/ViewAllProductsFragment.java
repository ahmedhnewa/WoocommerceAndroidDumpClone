package com.alreyada.app.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alreyada.app.R;
import com.alreyada.app.adapter.ListProductsAdapter;
import com.alreyada.app.databinding.FragmentViewAllProductsBinding;
import com.alreyada.app.model.product.Product;
import com.alreyada.app.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ViewAllProductsFragment extends Fragment {
    private FragmentViewAllProductsBinding binding;
    private Context context;
    private Activity activity;
    private int page = 1;
    private int itemPerPage = 10;
    private boolean isReachedLastProducts = false;
    private Dialog progress;
    private List<Product> productList;
    private ListProductsAdapter adapter;
    private GridLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private String type = "";
    private Integer category;
    private boolean isInTask = false;
    private ProgressBar progressBar;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    private boolean userScrolled = true;
    private boolean showOneTime = true;
    private String searchQuery = "";
    private static final String TAG = "ViewAllProductsFragment";


    public ViewAllProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentViewAllProductsBinding.inflate(inflater, container, false);
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

        initView();
        initVar();
        prepareRecyclerView();
        prepareLoadingDialog();
        getDataFromIntent();

        if (Utils.isNetworkAvailable(activity) && !isInTask) {
            /*showLoadingDialog();*/
            getAllProducts(true, null);
            isInTask = true;

            implementScrollListener();
        }

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

                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                if (userScrolled && (visibleItemCount + pastVisibleItems) == totalItemCount) {
                    userScrolled = false;

                    if (!isInTask) {
                        if (!isReachedLastProducts) {
                            page++;
                            showLoader();
                            getAllProducts(false, searchQuery);
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

    private void getDataFromIntent() {
        Intent intent = getActivity().getIntent();
        if (intent.getStringExtra("type") != null) {
            type = intent.getStringExtra("type");
        }
        category = intent.getIntExtra("category", -1);
    }


    private void getAllProducts(boolean isShouldClearList, String search) {
        Map<String, Object> queryMap = new HashMap<>();

        if (!type.isEmpty()) {
            if (type.equals("featuredProducts")) {
                queryMap.put("featured", true);
                Log.d(TAG, "getAllProducts: " + "featured");
            } else if (type.equals("onSaleProducts")) {
                queryMap.put("on_sale", true);
                Log.d(TAG, "getAllProducts: " + "onSale");
            }
        }
        if (search != null && !search.isEmpty()) {
            queryMap.put("search", searchQuery);
        }
        if (category != -1) queryMap.put("category", category);

        /*ApiClient.getApiInterface(true).getAllProducts(page, itemPerPage, queryMap)
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            if (!productList.isEmpty() && isShouldClearList) {
                                productList.clear();
                            }

                            if (response.body().size() <= 0) {
                                isReachedLastProducts = true;
                            }

                            for (int i = 0; i < response.body().size(); i++) {
                                String name = response.body().get(i).getName();
                                if (name.contains("import-placeholder-for") || name.contains("Import placeholder for")) {
                                    isReachedLastProducts = true;
                                } else {
                                    productList.add(response.body().get(i));
                                }
                            }

                            if (productList.size() > 0) {
                                adapter.notifyDataSetChanged();
                            } else {
                                isReachedLastProducts = true;
                            }

                        } else {
                            dismissLoader();
                            Toast.makeText(context, "error : " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                        dismissLoadingDialog();
                        dismissLoader();
                        isInTask = false;
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        isInTask = false;
                        dismissLoadingDialog();
                        dismissLoader();
                        Utils.showErrorMessage("تعذر تحميل المنتجات", activity);
                    }
                });*/
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                if (Utils.isNetworkAvailable(context)) {
                    Log.d(TAG, "onQueryTextSubmit: " + query);
                    if (!isInTask) {
                        if (searchItem != null && !searchQuery.isEmpty()) {
                            page = 1;
                            isReachedLastProducts = false;
                            getAllProducts(true, searchQuery);
                            showLoader();
                        } else {
                            page = 1;
                            isReachedLastProducts = false;
                            getAllProducts(true, null);
                        }
                    } else {
                        Utils.showShortToast("الرجاء الانتظار , هناك عملية بالخلفية", context);
                    }

                } else {
                    Utils.showLongToast("" + getString(R.string.no_internet), context);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchQuery = newText;
                if (searchQuery.length() == 0) {
                    page = 1;
                    isReachedLastProducts = false;
                    showLoader();
                    getAllProducts(true, null);
                }
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:

                return true;

            default:
                break;
        }
        return false;
    }

    private void dismissLoader() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showLoader() {
        if (progressBar.getVisibility() == View.GONE) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void prepareRecyclerView() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListProductsAdapter(activity, productList, R.layout.item_products_3);
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        recyclerView = binding.recyclerView;
        progressBar = binding.progressBar;
    }

    private void initVar() {
        productList = new ArrayList<>();
    }


    private void prepareLoadingDialog() {
        if (progress == null) {
            progress = new Dialog(getActivity());
            progress.setContentView(R.layout.custom_dialog);
            progress.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            progress.setCanceledOnTouchOutside(false);
        }
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}