package com.alreyada.app.listener;

import com.google.firebase.auth.FirebaseUser;

public interface FirebaseAuthenticationListener {
    void onSuccessListener(FirebaseUser user);
    void onFailureListener(String msg);
}
