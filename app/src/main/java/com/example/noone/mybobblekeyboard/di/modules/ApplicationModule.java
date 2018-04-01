package com.example.noone.mybobblekeyboard.di.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {RxModule.class})
public class ApplicationModule {
    private Context mContext;

    public ApplicationModule(Application application) {
        this.mContext = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mContext;
    }
}