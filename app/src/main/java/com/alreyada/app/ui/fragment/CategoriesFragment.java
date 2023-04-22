package com.alreyada.app.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alreyada.app.R;
import com.alreyada.app.adapter.CategoriesAdapter;
import com.alreyada.app.data.preference.AppPreference;
import com.alreyada.app.databinding.FragmentCategoriesBinding;
import com.alreyada.app.model.categories.Category;
import com.alreyada.app.util.Utils;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {
    private List<Category> categoriesList;
    private RecyclerView recyclerView;
    private Dialog progress;
    private LinearLayout rlNoData, view_no_internet;
    private CategoriesAdapter categoriesAdapter;
    private FragmentCategoriesBinding binding;
    private Context context;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
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

        if (Utils.isNetworkAvailable(getActivity())) {
//            showLoadingDialog();
            getCategories();
        }
    }

    private void prepareRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoriesAdapter = new CategoriesAdapter(getActivity(), categoriesList);
        recyclerView.setAdapter(categoriesAdapter);
    }

    private void prepareLoadingDialog() {
        if (progress == null) {
            progress = new Dialog(getActivity());
            progress.setContentView(R.layout.custom_dialog);
            progress.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            progress.setCanceledOnTouchOutside(false);
        }
    }

    private void initVar() {
        categoriesList = new ArrayList<>();
        categoriesList.clear();
    }

    private void initView() {
        recyclerView = binding.recyclerView;
        rlNoData = binding.rlNoData.rlNoData;
        view_no_internet = binding.lottieNoInternet.lottieNoInternet;
    }


    private void getCategories() {
        recyclerView.setVisibility(View.VISIBLE);
      /*  ApiClient.getApiInterface(true).getCategories()
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            if (!categoriesList.isEmpty()) {
                                categoriesList.clear();
                            }
                            categoriesList.addAll(response.body());

                            if (categoriesList.size() > 0) {
                                saveData(categoriesList, PrefKey.categoriesArrayList);
                                categoriesAdapter.notifyDataSetChanged();
                            }

                        } else {
                            showErrorMessage("تعذر تحميل التصنيفات : " + response.code());
                        }
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        dismissLoadingDialog();
                        showErrorMessage("تعذر الاتصال بالخادم");
                        recyclerView.setVisibility(View.INVISIBLE);
                        view_no_internet.setVisibility(View.VISIBLE);
                    }
                });*/
    }

    private void saveData(List<Category> categoriesList, String prefKey) {
        Gson gson = new Gson();
        String json = gson.toJson(categoriesList);
        AppPreference.getInstance(context).setString(json, prefKey);
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


    private void showErrorMessage(String msg) {
        Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);
        View sbView = snackBar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.tomato));
        snackBar.setTextColor(Color.WHITE);
        snackBar.show();
    }

    private void showSuccessMessage(String msg) {
        Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);
        View sbView = snackBar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.primaryColor));
        snackBar.setTextColor(Color.WHITE);
        snackBar.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

}
