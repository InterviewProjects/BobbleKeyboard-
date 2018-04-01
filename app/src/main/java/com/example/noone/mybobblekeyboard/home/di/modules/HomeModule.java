package com.example.noone.mybobblekeyboard.home.di.modules;

import com.example.noone.mybobblekeyboard.di.scopes.PerFragmentScope;
import com.example.noone.mybobblekeyboard.home.HomeInteractor;
import com.example.noone.mybobblekeyboard.home.HomePresenter;
import com.example.noone.mybobblekeyboard.network.ApiService;
import com.example.noone.mybobblekeyboard.home.HomeFragment;
import com.example.noone.mybobblekeyboard.home.HomeInteractorImpl;
import com.example.noone.mybobblekeyboard.home.HomePresenterImpl;
import com.example.noone.mybobblekeyboard.home.HomeView;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@PerFragmentScope
@Module
public class HomeModule {
    private HomeFragment mFragment;

    public HomeModule(HomeFragment fragment) {
        this.mFragment = fragment;
    }

    @Provides
    public HomePresenter providePresenter(HomeView view, HomeInteractor interactor) {
        return new HomePresenterImpl(view, interactor);
    }

    @Provides
    public HomeView provideView() {
        return mFragment;
    }

    @Provides
    public HomeInteractor provideInteractor(ApiService apiService, CompositeDisposable compositeDisposable) {
        return new HomeInteractorImpl(apiService, compositeDisposable);
    }
}
