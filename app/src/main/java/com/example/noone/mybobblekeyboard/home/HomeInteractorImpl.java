package com.example.noone.mybobblekeyboard.home;

import com.example.noone.mybobblekeyboard.base.BaseInteracterImpl;
import com.example.noone.mybobblekeyboard.network.ApiService;

import io.reactivex.disposables.CompositeDisposable;

public class HomeInteractorImpl extends BaseInteracterImpl implements HomeInteractor {
    private ApiService mApiService;

    public HomeInteractorImpl(ApiService apiService, CompositeDisposable compositeDisposable) {
        this.mApiService = apiService;
        this.mCompositeDisposable = compositeDisposable;
    }
}
