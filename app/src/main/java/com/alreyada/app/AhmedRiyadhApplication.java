package com.alreyada.app;

import android.app.Application;


public class AhmedRiyadhApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initOneSignal();
    }

    void initOneSignal() {
    }
}
