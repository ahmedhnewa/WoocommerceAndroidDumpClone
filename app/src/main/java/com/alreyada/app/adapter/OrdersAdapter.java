package com.alreyada.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alreyada.app.R;
import com.alreyada.app.model.orders.Order;
import com.alreyada.app.util.Utils;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    private Context context;
    private List<Order> orderList;
    private OrdersAdapterListener listener;

    public OrdersAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order item = orderList.get(position);
        ViewHolder view = (ViewHolder) holder;

        int orderId = item.getId();
        String date = item.getDateCreatedGmt();
        String status = item.getStatus();

        view.orderId.setText("#" + orderId);
        view.orderDate.setText(Utils.getFormattedDate(date));

        if (status.equalsIgnoreCase("processing")) {

            view.icon.setImageResource(R.drawable.ic_giftcard);
            view.icon.setColorFilter(context.getResources().getColor(R.color.yellow_700));
            view.layoutIcon.setBackgroundColor(context.getResources().getColor(R.color.yellow_100));
            view.buttonOrderStatus.setBackgroundColor(context.getResources().getColor(R.color.yellow_700));
            view.buttonOrderStatus.setText(context.getString(R.string.order_processing));

        } else if (status.equalsIgnoreCase("pending")) {
            holder.buttonOrderStatus.setText(context.getString(R.string.order_pending));
        } else if (status.equalsIgnoreCase("cancelled")) {

            view.icon.setImageResource(R.drawable.ic_close);
            view.icon.setColorFilter(context.getResources().getColor(R.color.red_700));
            view.layoutIcon.setBackgroundColor(context.getResources().getColor(R.color.red_100));
            view.buttonOrderStatus.setBackgroundColor(context.getResources().getColor(R.color.red_700));
            view.buttonOrderStatus.setText(context.getString(R.string.order_cancelled));

        } else if (status.equalsIgnoreCase("completed")) {

            view.icon.setImageResource(R.drawable.ic_check);
            view.icon.setColorFilter(context.getResources().getColor(R.color.green_700));
            view.layoutIcon.setBackgroundColor(context.getResources().getColor(R.color.green_100));
            view.buttonOrderStatus.setBackgroundColor(context.getResources().getColor(R.color.green_700));
            view.buttonOrderStatus.setText(context.getString(R.string.order_completed));

        } else if (status.equalsIgnoreCase("failed")) {

            view.icon.setImageResource(R.drawable.ic_error);
            view.icon.setColorFilter(context.getResources().getColor(R.color.red_700));
            view.layoutIcon.setBackgroundColor(context.getResources().getColor(R.color.red_100));
            view.buttonOrderStatus.setBackgroundColor(context.getResources().getColor(R.color.red_700));
            view.buttonOrderStatus.setText(context.getString(R.string.order_failed));

        } else if (status.equalsIgnoreCase("refunded")) {

            view.icon.setImageResource(R.drawable.ic_money);
            view.icon.setColorFilter(context.getResources().getColor(R.color.green_700));
            view.layoutIcon.setBackgroundColor(context.getResources().getColor(R.color.green_100));
            view.buttonOrderStatus.setBackgroundColor(context.getResources().getColor(R.color.green_700));
            view.buttonOrderStatus.setText(context.getString(R.string.order_completed));
            view.buttonOrderStatus.setText(context.getString(R.string.order_refunded));

        } else if (status.equalsIgnoreCase("on-hold")) {
            view.icon.setImageResource(R.drawable.ic_clock);
            view.icon.setColorFilter(context.getResources().getColor(R.color.yellow_700));
            view.layoutIcon.setBackgroundColor(context.getResources().getColor(R.color.yellow_100));
            view.buttonOrderStatus.setBackgroundColor(context.getResources().getColor(R.color.yellow_700));
            view.buttonOrderStatus.setText(context.getString(R.string.order_on_hold));
        } else {
            view.buttonOrderStatus.setText(status);
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView orderId, orderDate;
        public MaterialButton buttonOrderStatus;
        public LinearLayout layoutIcon;

        public ViewHolder(@NonNull View itemView,OrdersAdapterListener listener) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            orderId = itemView.findViewById(R.id.order_id);
            orderDate = itemView.findViewById(R.id.date);
            buttonOrderStatus = itemView.findViewById(R.id.buttonStatus);

            layoutIcon = itemView.findViewById(R.id.layout_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClickListener(v,position);
                        }
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(final OrdersAdapterListener listener) {
        this.listener = listener;
    }

    public interface OrdersAdapterListener{
        void onItemClickListener(View view , int position);
    }
}
