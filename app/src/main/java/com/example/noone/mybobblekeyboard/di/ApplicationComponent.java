package com.example.noone.mybobblekeyboard.di;

import android.content.Context;

import com.example.noone.mybobblekeyboard.di.modules.ApplicationModule;
import com.example.noone.mybobblekeyboard.di.modules.NetworkModule;
import com.example.noone.mybobblekeyboard.network.ApiService;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {
    ApiService getApiService();
    Context getContext();
    CompositeDisposable getCompositeDisposable();
}
