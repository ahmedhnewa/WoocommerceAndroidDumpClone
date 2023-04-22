package com.alreyada.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alreyada.app.R;
import com.alreyada.app.model.categories.Category;
import com.alreyada.app.ui.activity.ViewAllProductsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private Context mContext;
    private List<Category> itemList;

    public CategoriesAdapter(Context mContext, List<Category> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_blog, parent, false);
        return new ViewHolder(view,itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category categories = itemList.get(position);

        String name = categories.getName();
        String description = categories.getDescription();
        String imageUrl = null;
        int parent = categories.getParent();
        int count = categories.getCount();
        int id = categories.getId();

        //Credentials.basic("username","password");
        holder.categoriesName.setText(name);

        if (categories.getImage() != null) {
            imageUrl = categories.getImage().getSrc();
            try {
                Picasso.get()
                        .load(imageUrl)
                        .into(holder.categoriesImage);
            } catch (Exception e) {
                Picasso.get()
                        .load(R.drawable.ic_error)
                        .into(holder.categoriesImage);
            }
        } else if (categories.getImage() == null) {
            holder.categoriesImage.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView categoriesImage;
        public TextView categoriesName;

        public ViewHolder(@NonNull View itemView,List<Category> categoryList) {
            super(itemView);
            categoriesImage = itemView.findViewById(R.id.img);
            categoriesName = itemView.findViewById(R.id.title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        Category category = categoryList.get(position);
                        Intent intent = new Intent(context, ViewAllProductsActivity.class);
                        intent.putExtra("category", category.getId());
                        /*intent.putExtra("category_details", category);*/
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
