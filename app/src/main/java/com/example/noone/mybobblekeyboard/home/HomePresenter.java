package com.example.noone.mybobblekeyboard.home;

import com.example.noone.mybobblekeyboard.base.BasePresenter;

public interface HomePresenter extends BasePresenter<HomeView> {
    void actionOnActivityCreated();
    void actionOnSettingButtonClick();
    void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults);
}
