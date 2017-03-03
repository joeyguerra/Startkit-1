package com.thoughtworks.android.startkit;


import android.app.Application;
import android.content.Context;

public class StartkitApplication extends Application {

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    public static Context getApplication() {
        return instance;
    }
}
