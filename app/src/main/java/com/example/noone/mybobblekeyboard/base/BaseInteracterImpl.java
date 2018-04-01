package com.example.noone.mybobblekeyboard.base;

import io.reactivex.disposables.CompositeDisposable;

public class BaseInteracterImpl implements BaseInteracter {
    protected CompositeDisposable mCompositeDisposable;

    @Override
    public void disposeAllCallbacks() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
