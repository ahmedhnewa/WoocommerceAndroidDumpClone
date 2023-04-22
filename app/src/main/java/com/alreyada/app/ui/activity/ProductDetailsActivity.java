package com.alreyada.app.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alreyada.app.R;
import com.alreyada.app.data.constant.Constants;
import com.alreyada.app.data.preference.AppPreference;
import com.alreyada.app.data.preference.PrefKey;
import com.alreyada.app.data.sqlite.DbCartController;
import com.alreyada.app.data.sqlite.DbWishListController;
import com.alreyada.app.databinding.ActivityProductDetailsBinding;
import com.alreyada.app.model.product.Product;
import com.alreyada.app.ui.modal.AddToCartBottomSheet;
import com.alreyada.app.util.Tools;
import com.alreyada.app.util.Utils;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.List;

import at.blogc.android.views.ExpandableTextView;


public class ProductDetailsActivity extends AppCompatActivity implements AddToCartBottomSheet.AddToCartBottomSheetListener {
    private ActivityProductDetailsBinding binding;
    private ExpandableTextView descriptionContainer;
    private ImageView productImg;
    private TextView productRateTv, productNameTv, regularPriceTv, salePriceTv, stockCountTv, stockText, discountTv;
    private LinearLayout linearLayoutStock;
    private Toolbar toolbar;
    private String date_created, date_modified, price, description, imageUrl, salePrice, regularPrice, name;
    private Integer id;
    private boolean virtual, purchasable, shippingRequired, onSale;
    private Product product;
    private static DecimalFormat df = new DecimalFormat("#.##");
    private MaterialButton addToCart;
    private DbCartController controller;
    private DbWishListController dbWishListController;
    private Context context;
    private Activity activity;
    private Spanned currencySymbol;
    private Float rate;
    private static final String TAG = "ProductDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        activity = ProductDetailsActivity.this;
        context = ProductDetailsActivity.this;

        currencySymbol = Html.fromHtml(AppPreference.getInstance(context).getStoreData().getCurrencySymbol());
        initView();
        initToolbar();
        initVar();
        setData();

        Drawable icFavoriteBorder = getDrawable(R.drawable.ic_favorite_border);
        Drawable icFavorite = getDrawable(R.drawable.ic_favorite);
        if (dbWishListController.isAlreadyExist(id)) {
            binding.addToWishlist.setCompoundDrawablesWithIntrinsicBounds(icFavorite, null, null, null);
        } else {
            binding.addToWishlist.setCompoundDrawablesWithIntrinsicBounds(icFavoriteBorder, null, null, null);
        }

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!controller.isAlreadyExist(id)) {
                    if (isThereStockManagement()) {
                        AddToCartBottomSheet addToCartBottomSheet = AddToCartBottomSheet.newInstance(imageUrl, regularPrice, salePrice, name, false,product.getStockQuantity());
                        addToCartBottomSheet.show(getSupportFragmentManager(), "bottom sheet add to cart");
                    } else {
                        AddToCartBottomSheet addToCartBottomSheet = AddToCartBottomSheet.newInstance(imageUrl, regularPrice, salePrice, name, false,-1);
                        addToCartBottomSheet.show(getSupportFragmentManager(), "bottom sheet add to cart");
                    }
                } else {
                    Utils.showShortToast("هذة المنتج موجود بالسلة , لتحديث الكمية الرجاء الذهاب للسلة", context);
                }
            }
        });

        binding.addToWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dbWishListController.isAlreadyExist(id)) {
                    dbWishListController.addToWishList(id);
                    binding.addToWishlist.setCompoundDrawablesWithIntrinsicBounds(icFavorite, null, null, null);
                } else {
                    dbWishListController.removeItem(id);
                    binding.addToWishlist.setCompoundDrawablesWithIntrinsicBounds(icFavoriteBorder, null, null, null);
                }
            }
        });

        descriptionContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descriptionContainer.toggle();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        if (!description.isEmpty()) descriptionContainer.setText(Html.fromHtml(description));
        else binding.descriptionLabelTv.setVisibility(View.GONE);

        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.blue_500), PorterDuff.Mode.SRC_ATOP);
        Tools.setSystemBarColor(this, R.color.grey_3);

        //change system bar icon color
        Tools.setSystemBarLight(this);

        if (name != null && !name.isEmpty()) productNameTv.setText(name);
        else productNameTv.setVisibility(View.GONE);

        if (rate != -1) productRateTv.setText(String.valueOf(rate));
        else {
            productRateTv.setVisibility(View.GONE);
            binding.reviewLayout.setVisibility(View.GONE);
        }

        if (regularPrice.equals(salePrice) || salePrice.isEmpty()) {
            // there is no discount
            salePriceTv = findViewById(R.id.textViewPrice2);
            regularPriceTv = findViewById(R.id.textViewPrice1);

            salePriceTv.setVisibility(View.GONE);
            discountTv.setVisibility(View.GONE);

            if (regularPrice != null && !regularPrice.isEmpty())
                regularPriceTv.setText(regularPrice + currencySymbol);
            discountTv.setVisibility(View.GONE);
        } else {
            // discount
            salePriceTv = findViewById(R.id.textViewPrice1);
            regularPriceTv = findViewById(R.id.textViewPrice2);

            if (regularPrice != null && !regularPrice.isEmpty())
                regularPriceTv.setText(regularPrice + currencySymbol);

            if (salePrice != null && !salePrice.isEmpty())
                salePriceTv.setText(salePrice + currencySymbol);

            regularPriceTv.setPaintFlags(salePriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            try {
                double numberRegularPrice = Double.parseDouble(regularPrice);
                double numberSalePrice = Double.parseDouble(salePrice);

                discountTv.setText(calculateDiscount(numberRegularPrice, numberSalePrice)
                        + "%");
            } catch (Exception e) {
                discountTv.setVisibility(View.GONE);
            }

        }

        if (isThereStockManagement()) {
            // There is Stock Management
            if (product.getStockQuantity() != 0 && product.getStockQuantity() == 0) {
                stockText.setText("نفذت الكمية");
                Toast.makeText(context, "نفذت كمية هذة المنتج , لايمكن اضافته للسلة", Toast.LENGTH_SHORT).show();
                addToCart.setEnabled(false);
                stockText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_remove_shopping, 0, 0, 0);
            }
            stockCountTv.setText(String.valueOf(product.getStockQuantity()));
        } else {
            // No Stock Management
            // hide LinearLayout
            linearLayoutStock.setVisibility(View.GONE);
        }

        //final int productId = id;
    }

    private void initVar() {
        controller = new DbCartController(ProductDetailsActivity.this);
        dbWishListController = new DbWishListController(context);

        Intent intent = getIntent();

        product = intent.getParcelableExtra(Constants.productDetails);

        if (product.getDescription() != null) {
            description = product.getDescription();
        }

//        List<ImageCommon> images = product.getGallery();
//        if (images != null && images.size() > 0) {
//            imageUrl = product.getImages().get(0).getSrc();
//        }
        if (imageUrl != null) {
            try {
                Picasso.get()
                        .load(imageUrl)
                        .fit()
                        .centerInside()
                        .into(productImg);
            } catch (Exception e) {

                Picasso.get()
                        .load(R.drawable.ic_error)
                        .fit()
                        .centerInside()
                        .into(productImg);
            }
        } else {
            Toast.makeText(this, "لايوجد صورة لهذة المنتج", Toast.LENGTH_SHORT).show();
        }


        rate = product.getReviewCount();
        salePrice = product.getSalePrice();
        regularPrice = product.getRegularPrice();
        name = product.getName();
        date_created = product.getDateCreated().getDate();
        date_modified = product.getDateModified().getDate();
        price = product.getPrice();
        id = product.getProductId();

        virtual = product.isVirtual();
        purchasable = product.isPurchasable();
        shippingRequired = product.isShippingRequired();
        onSale = product.isOnSale();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private void initView() {
        descriptionContainer = binding.descriptionTv;
        productImg = binding.productImg;
        productNameTv = binding.productName;
        productRateTv = binding.productRate;
        toolbar = binding.toolbar;
        addToCart = binding.addToCart;

        stockCountTv = binding.stockTv;
        stockText = binding.stockText;
        linearLayoutStock = binding.linearLayoutStock;
        discountTv = binding.discount;
    }

    private List<Product> loadDataRecentProducts() {
        Gson gson = new Gson();
        String json = AppPreference.getInstance(ProductDetailsActivity.this).getString(PrefKey.recentProductsArrayList);
        Type type = new TypeToken<List<Product>>() {
        }.getType();
        try {
            return gson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }


    private boolean isThereStockManagement() {
        if (!product.isManageStock()) {
            return false;
        } else {
            return true;
        }
    }

    private double calculateDiscount(double regularPrice, double salePrice) {
        double amount = (salePrice / regularPrice) * 100;
        return (int) amount;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onButtonClicked(int quantity, boolean isProductInCart) {
        if (!isProductInCart) {
            controller.addToCart(id, quantity);
            Toast.makeText(ProductDetailsActivity.this, "تم اضافة المنتج للسلة", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
        } else {
            Toast.makeText(this, "in cart", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
        }
    }

}