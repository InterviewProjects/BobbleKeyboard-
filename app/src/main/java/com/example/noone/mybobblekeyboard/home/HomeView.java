package com.example.noone.mybobblekeyboard.home;

import com.example.noone.mybobblekeyboard.base.BaseView;

public interface HomeView extends BaseView {
    void showToast(String message);
    void startDictionaryDataDownloadService();
    void askForStoragePermission(int requestCode);
    boolean isStoragePermissionGranted();
    void openSettingScreen();
}
