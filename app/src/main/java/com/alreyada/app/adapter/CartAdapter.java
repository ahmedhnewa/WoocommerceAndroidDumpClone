package com.alreyada.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alreyada.app.R;
import com.alreyada.app.model.product.Product;
import com.alreyada.app.data.preference.AppPreference;
import com.alreyada.app.data.sqlite.DbCartController;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ListProductsViewHolder> {
    private final Context mContext;
    private final List<Product> arrayList;
    private OnItemClickListener listener;
    private final boolean isShouldAllowModify;
    private static Spanned currencySymbol;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    @LayoutRes
    private int layout;

    public CartAdapter(Context mContext, List<Product> arrayList, @LayoutRes int layout, boolean isShouldHideRemoveBtn) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.layout = layout;
        this.isShouldAllowModify = isShouldHideRemoveBtn;
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
        /*String date = products.getDateCreatedGmt();
        String modified = products.getDateModifiedGmt();
        String description = products.getDescription();*/
        String priceSt = products.getPrice();
        String imageUrl = null;

        if (products.getThumbnail() != null && products.getGallery().size() > 0) {
            imageUrl = products.getGallery().get(0);
        }

        Integer id = products.getProductId();
        if (name != null && !name.isEmpty()) holder.productName.setText(name);

        if (priceSt != null && priceSt.isEmpty())
            holder.productPrice.setText(Html.fromHtml(products.getPrice()));

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
                        .fit()
                        .centerInside()
                        .into(holder.productImg);
            }
        }

        if (isShouldAllowModify) {
            holder.removeImg.setVisibility(View.GONE);
            holder.decrease.setVisibility(View.GONE);
            holder.increase.setVisibility(View.GONE);
            holder.quantity.setText("الكمية : " + new DbCartController(mContext).getQuantityOfItem(id));
            if (products.getPriceHtml() != null && !products.getPriceHtml().isEmpty()) {
                holder.productPrice.setText(Html.fromHtml(products.getPriceHtml()));
            }
        } else {
            holder.quantity.setText(String.valueOf(new DbCartController(mContext).getQuantityOfItem(id)));
        }

        int amount = new DbCartController(mContext).getQuantityOfItem(id);

        if (priceSt != null && !priceSt.isEmpty() /*&& TextUtils.isDigitsOnly(priceSt)*/) {
            double price = Double.parseDouble(priceSt);
            double endPrice = price * amount;
            holder.productPrice.setText(df2.format(endPrice) + currencySymbol);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ListProductsViewHolder extends RecyclerView.ViewHolder {
        public TextView productName, productPrice, quantity;
        public ImageView productImg, removeImg;
        private ImageButton decrease, increase;


        public ListProductsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            quantity = itemView.findViewById(R.id.quantity);

            productImg = itemView.findViewById(R.id.productImg);
            removeImg = itemView.findViewById(R.id.remove);
            decrease = itemView.findViewById(R.id.decrease);
            increase = itemView.findViewById(R.id.increase);

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

            increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onIncrease(v, position, quantity, productPrice);
                        }
                    }
                }
            });

            decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDecrease(v, position, quantity, productPrice);
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

        void onIncrease(View view, int position, TextView amount, TextView price);

        void onDecrease(View view, int position, TextView amount, TextView price);
    }
}
