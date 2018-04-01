package com.example.noone.mybobblekeyboard.dictionarydata;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.noone.mybobblekeyboard.AppApplication;
import com.example.noone.mybobblekeyboard.dictionarydata.di.DaggerDictionaryDataComponent;
import com.example.noone.mybobblekeyboard.dictionarydata.di.DictionaryDataComponent;
import com.example.noone.mybobblekeyboard.dictionarydata.di.module.DictionaryDataModule;

import javax.inject.Inject;

public class DictionaryDataDownloadService extends Service implements DictionaryDataView {
    @Inject
    protected DictionaryDataPresenter mPresenter;
    @Override
    public void onCreate() {
        super.onCreate();

        setupServiceDIComponent();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPresenter.actionOnServiceCreated();


        return super.onStartCommand(intent, flags, startId);
    }

    private void setupServiceDIComponent() {
        DictionaryDataComponent dummyComponent = DaggerDictionaryDataComponent.builder()
                .applicationComponent(AppApplication.getApplicationComponent())
                .dictionaryDataModule(new DictionaryDataModule(this))
                .build();
        dummyComponent.inject(this);
    }

    @Override
    public void stopService() {
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onViewInactive();
    }
}
