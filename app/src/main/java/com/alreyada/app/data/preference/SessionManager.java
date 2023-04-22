package com.alreyada.app.data.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.alreyada.app.model.commons.BillingCommon;
import com.alreyada.app.model.User;

import static com.alreyada.app.data.preference.PrefKey.ATTEMPT_FAILED_LOGIN_COUNT;
import static com.alreyada.app.data.preference.PrefKey.ATTEMPT_FAILED_LOGIN_EMAIL;
import static com.alreyada.app.data.preference.PrefKey.IS_LOGIN_BY_SOCIAL_MEDIA;
import static com.alreyada.app.data.preference.PrefKey.KEY_ADDRESS1;
import static com.alreyada.app.data.preference.PrefKey.KEY_ADDRESS2;
import static com.alreyada.app.data.preference.PrefKey.KEY_CITY;
import static com.alreyada.app.data.preference.PrefKey.KEY_COMPANY;
import static com.alreyada.app.data.preference.PrefKey.KEY_COUNTRY;
import static com.alreyada.app.data.preference.PrefKey.KEY_EMAIL;
import static com.alreyada.app.data.preference.PrefKey.KEY_FIRST_NAME;
import static com.alreyada.app.data.preference.PrefKey.KEY_ID;
import static com.alreyada.app.data.preference.PrefKey.KEY_IMG_URL;
import static com.alreyada.app.data.preference.PrefKey.KEY_LAST_NAME;
import static com.alreyada.app.data.preference.PrefKey.KEY_PHONE;
import static com.alreyada.app.data.preference.PrefKey.KEY_POST_CODE;
import static com.alreyada.app.data.preference.PrefKey.KEY_ROLE;
import static com.alreyada.app.data.preference.PrefKey.KEY_STATE;
import static com.alreyada.app.data.preference.PrefKey.KEY_TOKEN;
import static com.alreyada.app.data.preference.PrefKey.KEY_USERNAME;

public class SessionManager {

    private static SessionManager mInstance;
    private static Context mContext;
    private static final String SHARED_PREF_NAME = "user_token";

    public SessionManager(Context mContext) {
        SessionManager.mContext = mContext;
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SessionManager(context);
        }
        return mInstance;
    }

    public void loginUser(User user, boolean isLoginBySocialMedia) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_FIRST_NAME, user.getFirstName());
        editor.putString(KEY_LAST_NAME, user.getLastName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_ROLE, user.getRole());
        editor.putString(KEY_USERNAME, user.getUserName());
        editor.putString(KEY_IMG_URL, user.getImgUrl());
        editor.putString(KEY_TOKEN, user.getToken());
        editor.putBoolean(IS_LOGIN_BY_SOCIAL_MEDIA, isLoginBySocialMedia);
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int checkId = sharedPreferences.getInt(KEY_ID, -1);
        if (checkId == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isLoginBySocialMedia() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_LOGIN_BY_SOCIAL_MEDIA, false);
    }

    public int getId() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(sharedPreferences.getInt(KEY_ID, -1)).getId();
    }

    public String getToken() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(sharedPreferences.getString(KEY_TOKEN, null)).getToken();
    }

    public void logout() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_FIRST_NAME, null),
                sharedPreferences.getString(KEY_LAST_NAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_ROLE, "customer"),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_IMG_URL, null),
                sharedPreferences.getString(KEY_TOKEN, null)
        );
    }

    public void setPassword(String newPassword) {
        if (isLoggedIn()) {
            AppPreference.getInstance(mContext).setString(PrefKey.PASSWORD,newPassword);
        }
    }

    public String getUserPassword() {
        if (isLoggedIn()) {
            return AppPreference.getInstance(mContext).getString(PrefKey.PASSWORD);
        }
        return null;
    }

    public void updateToken(String token) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_TOKEN);
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public void updateUserNameAndEmail(User user) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putInt(KEY_ID, user.getId());
        //editor.putString(KEY_FIRST_NAME, user.getFirstName());
        //editor.putString(KEY_LAST_NAME, user.getLastName());
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_USERNAME);

        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_USERNAME, user.getUserName());

        editor.apply();
    }

    public BillingCommon getAddress() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new BillingCommon(
                sharedPreferences.getString(KEY_FIRST_NAME, ""),
                sharedPreferences.getString(KEY_LAST_NAME, ""),
                sharedPreferences.getString(KEY_COMPANY, ""),
                sharedPreferences.getString(KEY_ADDRESS1, ""),
                sharedPreferences.getString(KEY_ADDRESS2, ""),
                sharedPreferences.getString(KEY_CITY, ""),
                sharedPreferences.getString(KEY_POST_CODE, ""),
                sharedPreferences.getString(KEY_COUNTRY, ""),
                sharedPreferences.getString(KEY_STATE, ""),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PHONE, "")
        );
    }

    public void setAddress(BillingCommon address) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_FIRST_NAME, address.getFirstName());
        editor.putString(KEY_LAST_NAME, address.getLastName());
        editor.putString(KEY_COMPANY, address.getCompany());
        editor.putString(KEY_ADDRESS1, address.getAddressOne());
        editor.putString(KEY_ADDRESS2, address.getAddressTow());
        editor.putString(KEY_CITY, address.getCity());
        editor.putString(KEY_POST_CODE, address.getPostcode());
        editor.putString(KEY_COUNTRY, address.getCountry());
        editor.putString(KEY_STATE, address.getState());
        editor.putString(KEY_PHONE, address.getPhone());
        editor.apply();
    }

    public void setAttemptFailedLoginCount(int attemptFailedLoginCount, String email) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(ATTEMPT_FAILED_LOGIN_COUNT, attemptFailedLoginCount);
        editor.putString(ATTEMPT_FAILED_LOGIN_EMAIL, email);
        editor.apply();
    }

//    public AttemptLoginFailedLogin getAttemptFailedLogin() {
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//
//        return new AttemptLoginFailedLogin(
//                sharedPreferences.getInt(ATTEMPT_FAILED_LOGIN_COUNT, -1),
//                sharedPreferences.getString(ATTEMPT_FAILED_LOGIN_EMAIL, "")
//        );
//    }
}
