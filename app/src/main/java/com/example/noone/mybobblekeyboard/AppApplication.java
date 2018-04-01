package com.example.noone.mybobblekeyboard;

import android.app.Application;

import com.example.noone.mybobblekeyboard.di.ApplicationComponent;
import com.example.noone.mybobblekeyboard.di.DaggerApplicationComponent;
import com.example.noone.mybobblekeyboard.di.modules.ApplicationModule;

public class AppApplication extends Application {
    private static ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
