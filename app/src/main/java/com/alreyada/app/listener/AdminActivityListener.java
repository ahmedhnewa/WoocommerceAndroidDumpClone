package com.alreyada.app.listener;

import com.alreyada.app.model.authentication.customer.Customer;

public interface AdminActivityListener {
    void onGetResult(boolean isWordpressAdmin, boolean isFirebaseAdmin, Customer customer);
    void onWooResponseNotSuccessful(int responseCode, String jsonError);
    void onWooFailed(String msg);
    void onFirebaseFailed(String msg, int errorCode);
}