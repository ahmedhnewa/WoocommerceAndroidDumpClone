package com.alreyada.app.ui.modal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alreyada.app.databinding.BottomSheetAddToCartBinding;
import com.alreyada.app.util.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

public class AddToCartBottomSheet extends BottomSheetDialogFragment {
    private BottomSheetAddToCartBinding binding;
    private AddToCartBottomSheetListener listener;
    private int stock;
    private int quantity = 1;

    private Context context;
    private Activity activity;

    private String imageUrl, regularPrice, salePrice, productName;
    private boolean isProductInCart;


    private static final String ARG_IMG_URL = "paramImageUrl";
    private static final String ARG_STOCK = "paramStock";
    private static final String ARG_REGULAR_PRICE = "paramOriginalPrice";
    private static final String ARG_SALE_PRICE = "paramSalePrice";
    private static final String ARG_PRODUCT_NAME = "paramProductName";
    private static final String ARG_IS_PRODUCT_IN_CART = "paramProductInCart";


    public static AddToCartBottomSheet newInstance(String imageUrl, String regularPrice, String salePrice, String productName, boolean isProductInCart,int stock) {
        AddToCartBottomSheet addToCartBottomSheet = new AddToCartBottomSheet();
        Bundle args = new Bundle();
        args.putString(ARG_IMG_URL, imageUrl);
        args.putString(ARG_REGULAR_PRICE, salePrice);
        args.putString(ARG_SALE_PRICE, regularPrice);
        args.putString(ARG_PRODUCT_NAME, productName);
        args.putInt(ARG_STOCK, stock);
        args.putBoolean(ARG_IS_PRODUCT_IN_CART, isProductInCart);
        addToCartBottomSheet.setArguments(args);
        return addToCartBottomSheet;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageUrl = getArguments().getString(ARG_IMG_URL);
            regularPrice = getArguments().getString(ARG_REGULAR_PRICE);
            salePrice = getArguments().getString(ARG_SALE_PRICE);
            productName = getArguments().getString(ARG_PRODUCT_NAME);
            isProductInCart = getArguments().getBoolean(ARG_IS_PRODUCT_IN_CART);
            stock = getArguments().getInt(ARG_STOCK);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetAddToCartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        context = getContext();
        activity = getActivity();

        updateTextViewQuantity();

        binding.tvProductName.setText(productName);

        binding.llButton.setVisibility(View.VISIBLE);

        Picasso.get()
                .load(imageUrl)
                .fit()
                .into(binding.ivProduct);

        binding.ivIncreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if there is stock management
                if (stock != -1) {
                    // stock is more than inventory stock
                    if (quantity < stock) {
                        quantity++;
                    } else {
                        Utils.showShortToast("عذرا لايمكن ان تكون الكمية المضافة للسلة اكثر من المتوفرة بالمخزن", context);
                    }
                } else {
                    quantity++;
                }
                updateTextViewQuantity();
            }
        });

        binding.ivDecreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--;
                    updateTextViewQuantity();
                }
            }
        });

        binding.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setResult(Activity.RESULT_OK);
                listener.onButtonClicked(quantity, isProductInCart);
                dismiss();
            }
        });

        return view;
    }

    private void updateTextViewQuantity() {
        binding.edtQty.setText(String.valueOf(quantity));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AddToCartBottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement AddToCartBottomSheetListener ");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface AddToCartBottomSheetListener {
        void onButtonClicked(int quantity, boolean isProductInCart);
    }
}
