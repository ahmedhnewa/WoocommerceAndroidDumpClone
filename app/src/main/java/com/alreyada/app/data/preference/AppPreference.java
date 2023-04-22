package com.alreyada.app.data.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.alreyada.app.data.model.Store;

import static com.alreyada.app.data.preference.PrefKey.APP_PREF_NAME;
import static com.alreyada.app.data.preference.PrefKey.currency;
import static com.alreyada.app.data.preference.PrefKey.currencyPosition;
import static com.alreyada.app.data.preference.PrefKey.currencySymbol;
import static com.alreyada.app.data.preference.PrefKey.decimalSeparator;
import static com.alreyada.app.data.preference.PrefKey.hideErrors;
import static com.alreyada.app.data.preference.PrefKey.secureConnection;
import static com.alreyada.app.data.preference.PrefKey.thousandSeparator;

public class AppPreference {

    // declare context
    private static Context mContext;

    // singleton
    private static AppPreference appPreference = null;

    // common
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static AppPreference getInstance(Context context) {
        if (appPreference == null) {
            mContext = context;
            appPreference = new AppPreference();
        }
        return appPreference;
    }

    private AppPreference() {
        sharedPreferences = mContext.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public Boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void setInteger(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInteger(String key) {
        return sharedPreferences.getInt(key, -1);
    }


    public boolean isNotificationOn() {
        return sharedPreferences.getBoolean("perf_notification", true);
    }

    public Store getStoreData() {
        return new Store(
                sharedPreferences.getString(currency, null),
                sharedPreferences.getString(currencySymbol, "&#36;"),
                sharedPreferences.getString(currencyPosition, null),
                sharedPreferences.getString(thousandSeparator, null),
                sharedPreferences.getString(decimalSeparator, null),
                sharedPreferences.getBoolean(secureConnection, false),
                sharedPreferences.getBoolean(hideErrors, false)
        );
    }

    public void setStoreData(Store storeData) {
        editor.putString(currency, storeData.getCurrency());
        editor.putString(currencySymbol, storeData.getCurrencySymbol());
        editor.putString(currencyPosition, storeData.getThousandSeparator());
        editor.putString(thousandSeparator, storeData.getThousandSeparator());
        editor.putString(decimalSeparator, storeData.getDecimalSeparator());
        editor.putBoolean(secureConnection, storeData.getSecureConnection());
        editor.putBoolean(hideErrors, false);
        editor.commit();
    }

    public void setCurrencySymbol(String currencySymbol) {
        editor.putString(currencySymbol, currencySymbol);
        editor.commit();
    }

}
