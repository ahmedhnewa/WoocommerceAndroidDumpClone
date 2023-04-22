package com.alreyada.app.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alreyada.app.R;
import com.alreyada.app.data.preference.AppPreference;
import com.alreyada.app.data.preference.PrefKey;
import com.alreyada.app.listener.FirebaseAuthenticationListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

public class FirebaseAuthentication {
    private static FirebaseAuthentication authentication = null;
    private static FirebaseAuth auth;
    private static Context context = null;
    private static GoogleSignInClient mSignInClient;
    private static final String ANONYMOUS = "مجهول";

    public FirebaseAuthentication(Context context) {
        this.context = context;
        auth = FirebaseAuth.getInstance();
    }


    public static FirebaseAuthentication getInstance(Context context) {
        if (authentication == null) {
            authentication = new FirebaseAuthentication(context);
        }
        return authentication;
    }

    @Nullable
    public String getUserPhotoUrl() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null && user.getPhotoUrl() != null) {
            return user.getPhotoUrl().toString();
        }

        return null;
    }

    public String getProviderId() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            return user.getProviderId();
        }
        return null;
    }

    public String getUserName() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            return user.getDisplayName();
        }

        return ANONYMOUS;
    }

    public String getUserUid() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            return user.getUid();
        }

        return null;
    }


    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    public void signInAnonymously(FirebaseAuthenticationListener authenticationListener) {
        auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    authenticationListener.onSuccessListener(task.getResult().getUser());
                } else {
                    authenticationListener.onFailureListener(task.getException().getMessage());
                }
            }
        });
    }

//    public FirebaseUserAnonyMous getAnonymousData() {
//        Gson gson = new Gson();
//        return gson.fromJson(AppPreference.getInstance(context).getString(PrefKey.USER_ANONYMOUS), FirebaseUserAnonyMous.class);
//    }

//    public void setAnonymousData(FirebaseUserAnonyMous firebaseUserAnonyMous) {
//        Gson gson = new Gson();
//        AppPreference.getInstance(context).setString(PrefKey.USER_ANONYMOUS,gson.toJson(firebaseUserAnonyMous));
//    }

    public boolean isLoggedIn() {
        return auth.getCurrentUser() != null;
    }

    public void signOut() {
        auth.signOut();
        mSignInClient.signOut();
    }

    public GoogleSignInClient getSignInClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mSignInClient = GoogleSignIn.getClient(context, gso);
        return mSignInClient;
    }

}
