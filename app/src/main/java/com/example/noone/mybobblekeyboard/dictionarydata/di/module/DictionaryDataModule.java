package com.example.noone.mybobblekeyboard.dictionarydata.di.module;

import com.example.noone.mybobblekeyboard.di.scopes.PerServiceScope;
import com.example.noone.mybobblekeyboard.dictionarydata.DictionaryDataDownloadService;
import com.example.noone.mybobblekeyboard.dictionarydata.DictionaryDataInteractor;
import com.example.noone.mybobblekeyboard.dictionarydata.DictionaryDataInteractorImpl;
import com.example.noone.mybobblekeyboard.dictionarydata.DictionaryDataPresenter;
import com.example.noone.mybobblekeyboard.dictionarydata.DictionaryDataPresenterImpl;
import com.example.noone.mybobblekeyboard.dictionarydata.DictionaryDataView;
import com.example.noone.mybobblekeyboard.network.ApiService;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@PerServiceScope
@Module
public class DictionaryDataModule {
    private DictionaryDataDownloadService mService;

    public DictionaryDataModule(DictionaryDataDownloadService service) {
        this.mService = service;
    }

    @Provides
    public DictionaryDataPresenter providePresenter(DictionaryDataView view, DictionaryDataInteractor interactor) {
        return new DictionaryDataPresenterImpl(view, interactor);
    }

    @Provides
    public DictionaryDataView provideView() {
        return mService;
    }

    @Provides
    public DictionaryDataInteractor provideInteractor(ApiService apiService, CompositeDisposable compositeDisposable) {
        return new DictionaryDataInteractorImpl(apiService, compositeDisposable);
    }
}