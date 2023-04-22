package com.alreyada.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alreyada.app.R;

import java.util.List;

public class OrderStatusAdapter extends RecyclerView.Adapter<OrderStatusAdapter.ViewHolder> {
    private Context context;
    private List<String> status;

    public OrderStatusAdapter(Context context, List<String> status) {
        this.context = context;
        this.status = status;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_order_status,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String itemStatus = status.get(position);
        ViewHolder view = (ViewHolder) holder;

        if (itemStatus.equalsIgnoreCase("processing")) {
            view.title.setText(context.getString(R.string.order_processing));
        } else if (itemStatus.equalsIgnoreCase("pending")) {
            view.title.setText(context.getString(R.string.order_pending));
        } else if (itemStatus.equalsIgnoreCase("cancelled")) {
            view.title.setText(context.getString(R.string.order_cancelled));
        } else if (itemStatus.equalsIgnoreCase("completed")) {
            view.title.setText(context.getString(R.string.order_completed));
        } else if (itemStatus.equalsIgnoreCase("failed")) {
            view.title.setText(context.getString(R.string.order_failed));
        } else if (itemStatus.equalsIgnoreCase("refunded")) {
            view.title.setText(context.getString(R.string.order_refunded));
        } else if (itemStatus.equalsIgnoreCase("on-hold")) {
            view.title.setText(context.getString(R.string.order_on_hold));

        } else {
            view.title.setText(itemStatus);
        }
    }

    @Override
    public int getItemCount() {
        return status.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout layoutStatus;
        public ImageView statusIcon;
        public TextView title,description,time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutStatus = itemView.findViewById(R.id.layoutStatus);
            statusIcon = itemView.findViewById(R.id.statusIcon);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            time = itemView.findViewById(R.id.time);
        }
    }
}
