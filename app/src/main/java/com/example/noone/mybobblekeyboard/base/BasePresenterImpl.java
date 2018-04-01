package com.example.noone.mybobblekeyboard.base;

public class BasePresenterImpl<T, V extends BaseInteracter> implements BasePresenter<T> {
    protected T mView;
    protected V mInteractor;

    @Override
    public void onViewActive(T view) {
        this.mView = view;
    }

    @Override
    public void onViewInactive() {
        this.mView = null;

        if (mInteractor != null) {
            mInteractor.disposeAllCallbacks();
        }
    }
}
