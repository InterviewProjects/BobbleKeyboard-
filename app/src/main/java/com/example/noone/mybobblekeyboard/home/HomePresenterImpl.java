package com.example.noone.mybobblekeyboard.home;

import com.example.noone.mybobblekeyboard.base.BasePresenterImpl;

public class HomePresenterImpl extends BasePresenterImpl<HomeView, HomeInteractor> implements HomePresenter {
    private static final int REQUEST_STORAGE_PERMISSION_CODE = 0x10;

    public HomePresenterImpl(HomeView view, HomeInteractor interacter) {
        this.mView = view;
        this.mInteractor = interacter;
    }

    @Override
    public void actionOnActivityCreated() {
        if (mView.isStoragePermissionGranted()) {
            mView.startDictionaryDataDownloadService();
        } else {
            mView.askForStoragePermission(REQUEST_STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void actionOnSettingButtonClick() {
        mView.openSettingScreen();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSION_CODE:
                if (mView.isStoragePermissionGranted()) {
                    mView.startDictionaryDataDownloadService();
                } else {
                    mView.showToast("We required storage Permission. Please allow this permission in App Settings.");
                }
                break;
        }
    }
}
