package com.alreyada.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alreyada.app.R;
import com.alreyada.app.model.product.Product;
import com.alreyada.app.data.preference.AppPreference;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ListProductsViewHolder> {
    private Context mContext;
    private List<Product> arrayList;
    private OnItemClickListener listener;
    private static Spanned currencySymbol;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    @LayoutRes
    private int layout;

    public WishListAdapter(Context mContext, List<Product> arrayList, @LayoutRes int layout) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.layout = layout;
        if (AppPreference.getInstance(mContext).getStoreData().getCurrencySymbol() != null) {
            currencySymbol = Html.fromHtml(AppPreference.getInstance(mContext).getStoreData().getCurrencySymbol());
        }
    }

    @NonNull
    @Override
    public ListProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(layout, parent, false);
        return new ListProductsViewHolder(view, listener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListProductsViewHolder holder, int position) {
        Product products = arrayList.get(position);

        String name = products.getName();
        String priceSt = products.getPrice();
        String imageUrl = null;

        if (products.getGallery() != null && products.getGallery().size() > 0) {
            imageUrl = products.getGallery().get(0);
        }

        Integer id = products.getProductId();
        if (name != null && !name.isEmpty()) holder.productName.setText(name);

        if (priceSt != null && priceSt.isEmpty())
            holder.productPrice.setText(Html.fromHtml(products.getPriceHtml()));

        if (imageUrl != null) {
            try {
                Picasso.get()
                        .load(imageUrl)
                        .fit()
                        .centerInside()
                        .into(holder.productImg);
            } catch (Exception e) {

                Picasso.get()
                        .load(R.drawable.ic_error)
                        .into(holder.productImg);
            }
        }

        if (priceSt != null && !priceSt.isEmpty() /*&& TextUtils.isDigitsOnly(priceSt)*/) {
            double price  = Double.parseDouble(priceSt);
            holder.productPrice.setText(df2.format(price) + currencySymbol);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ListProductsViewHolder extends RecyclerView.ViewHolder {
        public TextView productName, productPrice;
        public ImageView productImg, removeImg,addToCart;


        public ListProductsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);

            productImg = itemView.findViewById(R.id.productImg);
            removeImg = itemView.findViewById(R.id.remove);
            addToCart = itemView.findViewById(R.id.moveToCart);

            removeImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(v, position);
                        }
                    }
                }
            });

            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAddToCartClick(v, position);
                        }
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(v, position);
                        }
                    }
                }
            });

        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onDeleteClick(View view, int position);
        void onItemClick(View view, int position);
        void onAddToCartClick(View view,int position);
    }
}
