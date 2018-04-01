package com.example.noone.mybobblekeyboard.base;

public interface BasePresenter<T> {
    void onViewActive(T view);

    void onViewInactive();
}
