package com.alreyada.app.ui.fragment.admin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.alreyada.app.adapter.admin.UserAdapter;
import com.alreyada.app.databinding.FragmentUserBinding;
import com.alreyada.app.model.authentication.customer.Customer;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {
    private FragmentUserBinding binding;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private UserAdapter adapter;
    private List<Customer> userList;
    private Context context;
    private Activity activity;
    private int page = 1;
    private int itemPerPage = 10;
    private static final String TAG = "UsersFragment";

    public UsersFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);
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

        getUsers(true);
    }

    private void getUsers(boolean isShouldClearList) {
        /*ApiClient.getApiInterface(true).getCustomers(page, itemPerPage)
                .enqueue(new Callback<List<Customer>>() {
                    @Override
                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            if (!userList.isEmpty() && isShouldClearList) {
                                userList.clear();
                            }

                            userList.addAll(response.body());

                            if (userList.size() > 0) {
                                adapter.notifyDataSetChanged();
                                hideView(progressBar);
                            }

                        } else {
                            Utils.showErrorMessage("تعذر تحميل قائمة المستخدمين : " + response.code(), activity);
                            try {
                                Log.d(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        hideView(progressBar);
                    }

                    @Override
                    public void onFailure(Call<List<Customer>> call, Throwable t) {
                        hideView(progressBar);
                        Utils.showErrorMessage("تعذر تحميل قائمة المستخدمين : " + t.getMessage(), activity);
                    }
                });*/
    }


    private void hideView(View ly) {
        if (ly.getVisibility() == View.VISIBLE) {
            ly.setVisibility(View.GONE);
        }
    }

    private void showView(View ly) {
        if (ly.getVisibility() == View.INVISIBLE || ly.getVisibility() == View.GONE) {
            ly.setVisibility(View.VISIBLE);
        }
    }

    private void prepareRecyclerView() {
        recyclerView.setHasFixedSize(true);
        adapter = new UserAdapter(context, userList);
        recyclerView.setAdapter(adapter);
    }

    private void initVar() {
        userList = new ArrayList<>();
    }

    private void initView() {
        recyclerView = binding.userRv;
        progressBar = binding.progressCircular;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
