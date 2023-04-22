package com.alreyada.app.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageButton;

public class ChatButtonObserver implements TextWatcher {

    private ImageButton mButton;

    public ChatButtonObserver(ImageButton button) {
        this.mButton = button;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().trim().length() > 0) {
            mButton.setEnabled(true);
        } else {
            mButton.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

}
