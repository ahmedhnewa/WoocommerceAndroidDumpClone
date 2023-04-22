package com.alreyada.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alreyada.app.R;
import com.alreyada.app.data.preference.AppPreference;
import com.alreyada.app.model.product.Product;
import com.alreyada.app.ui.activity.ProductDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListProductsAdapter extends RecyclerView.Adapter<ListProductsAdapter.ListProductsViewHolder> {
    private final Context mContext;
    private final List<Product> arrayList;
    private ListProductsAdapterListener listener;
    private static Spanned currencySymbol;
    private boolean useDefaultItemClickListener = true;
    @LayoutRes
    private final int layout;

    public ListProductsAdapter(Context mContext, List<Product> arrayList, @LayoutRes int layout) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.layout = layout;
        if (AppPreference.getInstance(mContext).getStoreData().getCurrencySymbol() != null) {
            currencySymbol = Html.fromHtml(AppPreference.getInstance(mContext).getStoreData().getCurrencySymbol());
        }
    }

    public void setUseDefaultItemClickListener(boolean b) {
        useDefaultItemClickListener = b;
    }

    @NonNull
    @Override
    public ListProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(layout, parent, false);
        return new ListProductsViewHolder(view, arrayList, useDefaultItemClickListener, listener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListProductsViewHolder holder, int position) {
        Product product = arrayList.get(position);

        String name = product.getName();
        String date = product.getDateCreated().getDate();
        String modified = product.getDateModified().getDate();
        String description = product.getDescription();
        String price = product.getPrice();
        String originalPrice = product.getRegularPrice();
        String salePrice = product.getSalePrice();
        Integer id = product.getProductId();
        float ratingCount = product.getReviewCount();
        String imageUrl = null;

        if (product.getGallery() != null && product.getGallery().size() > 0) imageUrl = product.getGallery().get(0);

        if (!TextUtils.isEmpty(name)) holder.tvProductName.setText(name);
        else holder.tvProductName.setVisibility(View.GONE);

        if (salePrice.isEmpty()) {
            // no discount
            if (!originalPrice.isEmpty()) holder.salePrice.setText(price + currencySymbol);
            holder.originalPrice.setVisibility(View.INVISIBLE);
        } else if (!salePrice.isEmpty() && !salePrice.equals(originalPrice)) {
            holder.originalPrice.setText(originalPrice + currencySymbol);
            holder.originalPrice.setPaintFlags(holder.originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            if (!salePrice.isEmpty()) holder.salePrice.setText(price + currencySymbol);
        }

        if (ratingCount != -1) holder.ratingBar.setRating(ratingCount);

        if (!TextUtils.isEmpty(imageUrl)) {
            try {
                Picasso.get()
                        .load(imageUrl)
                        .fit()
                        .centerInside()
                        .into(holder.productIv);
            } catch (Exception e) {
                Picasso.get()
                        .load(R.drawable.ic_error)
                        .into(holder.productIv);
            }
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ListProductsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvProductName, originalPrice, salePrice;
        private final RatingBar ratingBar;
        public ImageView productIv;

        public ListProductsViewHolder(@NonNull View itemView, List<Product> productList, boolean useDefaultItemClickListener, ListProductsAdapterListener listener) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            originalPrice = itemView.findViewById(R.id.originalPriceTv);
            salePrice = itemView.findViewById(R.id.discountPriceTv);
            productIv = itemView.findViewById(R.id.productIv);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            productIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        if (useDefaultItemClickListener) {
                            Product product = productList.get(position);
                            Intent intent = new Intent(context, ProductDetailsActivity.class);
                            intent.putExtra("id", product.getProductId());
//                            intent.putExtra(Constants.productDetails, product);
                            context.startActivity(intent);
                        } else {
                            if (listener != null) {
                                listener.onItemClick(v, position);
                            }
                        }
                    }
                }
            });


        }
    }

    public void setOnItemClickListener(ListProductsAdapterListener listener) {
        this.listener = listener;
    }

    public interface ListProductsAdapterListener {
        void onItemClick(View view, int position);
    }
}
