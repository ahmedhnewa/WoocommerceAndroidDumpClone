package com.alreyada.app.adapter.viewholder;

import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alreyada.app.R;
import com.alreyada.app.model.Message;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener,MenuItem.OnMenuItemClickListener {
    private MessageViewHolderListener listener;
    private static final String TAG = "MessageViewHolder";
    TextView messageTextView, messengerTextView, messengerTextViewTime;
    CircleImageView photoUrl;
    ImageView messageImageView;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        messageTextView = itemView.findViewById(R.id.messageTextView);
        messageImageView = itemView.findViewById(R.id.messageImageView);
        messengerTextView = itemView.findViewById(R.id.messengerTextView);
        photoUrl = itemView.findViewById(R.id.photoUrl);
        messengerTextViewTime = itemView.findViewById(R.id.messengerTextViewTime);

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

        itemView.setOnCreateContextMenuListener(this::onCreateContextMenu);
    }

    public void bindMessage(Message message) {
        if (message.getMessage() != null) {
            // set text content
            messageTextView.setText(message.getMessage());
            // show message view
            showLayout(messageTextView);
            // usually the message is not content a image
            hideLayout(messageImageView);
        } else if (message.getImageUrl() != null) {
            String imageUrl = message.getImageUrl();
            // if image uploaded to firebase storage
            if (imageUrl.startsWith("gs://")) {
                // trying to get image reference from firebase storage using image url
                StorageReference storageReference = FirebaseStorage.getInstance()
                        .getReferenceFromUrl(imageUrl);
                // after get the reference , now get download url
                storageReference.getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // image url getted  successfully
                                String downloadUrl = uri.toString();
                                Glide.with(messageImageView.getContext())
                                        .load(downloadUrl)
                                        .into(messageImageView);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // failed to get image url and it in firebase storage , maybe it removed
                                Log.w(TAG, "Getting download url was not successful.", e);
                            }
                        });
            } else {
                // if it not uploaded to firebase storage
                // load it
                Glide.with(messageImageView.getContext())
                        .load(message.getImageUrl())
                        .into(messageImageView);
            }

            // show image because image is not null
            showLayout(messageImageView);
            // hide text because it didn't have a text
            hideLayout(messageTextView);
        }
        if (message.getPhotoUrl() != null) {
            Glide.with(photoUrl.getContext())
                    .load(message.getPhotoUrl())
                    .into(photoUrl);
        }
        if (message.getSenderName() != null) {
            messengerTextView.setText(message.getSenderName());
        }
        if (message.getDate() != null && !message.getDate().isEmpty()) {
            messengerTextViewTime.setText(message.getDate());
        }
    }

    private void hideLayout(View ly) {
        if (ly.getVisibility() == View.VISIBLE) {
            ly.setVisibility(View.GONE);
        }
    }

    private void showLayout(View ly) {
        if (ly.getVisibility() == View.INVISIBLE || ly.getVisibility() == View.GONE) {
            ly.setVisibility(View.VISIBLE);
        }
    }

    public void setOnItemClickListener(MessageViewHolderListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("اختار الاجراء");
        MenuItem delete = menu.add(Menu.NONE, 1, 1, "حذف الرسالة");
        MenuItem copy = menu.add(Menu.NONE, 2, 2, "نسخ الرسالة");

        delete.setOnMenuItemClickListener(this::onMenuItemClick);
        copy.setOnMenuItemClickListener(this::onMenuItemClick);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (listener != null) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                switch (item.getItemId()) {
                    case 1:
                        listener.onDeleteClick(itemView.getRootView(), position);
                        return true;
                    case 2:
                        listener.onCopyClick(itemView.getRootView(), position);
                        return true;
                }
            }
        }
        return false;
    }

    public interface MessageViewHolderListener {
        void onItemClick(View view, int position);

        void onDeleteClick(View view, int position);

        void onCopyClick(View view, int position);
    }
}
