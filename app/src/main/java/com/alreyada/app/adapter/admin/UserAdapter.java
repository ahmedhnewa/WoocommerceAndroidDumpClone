package com.alreyada.app.adapter.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alreyada.app.R;
import com.alreyada.app.model.authentication.customer.Customer;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<Customer> userList;

    public UserAdapter(Context context, List<Customer> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Customer user = userList.get(position);

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String username = user.getUsername();

        String phoneNumber = null;
        if (user.getBilling() != null && user.getBilling().getPhone() != null && !user.getBilling().getPhone().isEmpty()) {
            phoneNumber = user.getBilling().getPhone();
            holder.userDetails.setText("رقم الهاتف : " + phoneNumber);
        }

        if (firstName != null && lastName != null && !firstName.isEmpty() && !lastName.isEmpty())
            holder.userNameTv.setText("" + firstName + " " + lastName);
        else holder.userNameTv.setText("" + username);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNameTv, userDetails;
        CircleImageView UserImageIv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTv = itemView.findViewById(R.id.userNameTv);
            userDetails = itemView.findViewById(R.id.userDetails);
            UserImageIv = itemView.findViewById(R.id.UserImageIv);
        }
    }
}
