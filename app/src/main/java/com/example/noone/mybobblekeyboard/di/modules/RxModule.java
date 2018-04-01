package com.example.noone.mybobblekeyboard.di.modules;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class RxModule {
    @Provides
    public CompositeDisposable provideDisposable() {
        return new CompositeDisposable();
    }
}
