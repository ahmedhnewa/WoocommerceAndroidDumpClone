package com.alreyada.app.api;


import android.content.Context;

import com.alreyada.app.data.constant.Constants;
import com.alreyada.app.data.preference.SessionManager;
import com.alreyada.app.util.AuthenticationInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static Retrofit retrofit = null;
    private static String token = null;

    public static ApiInterface getApiInterface(boolean addJwt, Context context) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder().connectTimeout(3, TimeUnit.MINUTES).readTimeout(3, TimeUnit.MINUTES);

        if (token == null) {
            token = SessionManager.getInstance(context).getToken();
        }

        if (addJwt && token != null && !token.isEmpty()) {
            AuthenticationInterceptor authenticationInterceptor = new AuthenticationInterceptor.Builder().token(token).build();
            builder.addInterceptor(authenticationInterceptor);
        }

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(ApiInterface.class);
    }

}
