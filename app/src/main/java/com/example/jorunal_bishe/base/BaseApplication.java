package com.example.jorunal_bishe.base;

import android.app.Application;
import android.content.Context;

import com.example.jorunal_bishe.exceptions.BaseExceptionHandler;
import com.example.jorunal_bishe.exceptions.LocalFileHandler;

public abstract class BaseApplication extends Application {
    public Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        if (getDefaultUncaughtExceptionHandler() == null) {
            Thread.setDefaultUncaughtExceptionHandler(new LocalFileHandler(
                    applicationContext));
        } else {
            Thread.setDefaultUncaughtExceptionHandler(getDefaultUncaughtExceptionHandler());
        }
    }

    public abstract BaseExceptionHandler getDefaultUncaughtExceptionHandler();
}
