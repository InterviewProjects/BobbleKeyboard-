package com.example.noone.mybobblekeyboard.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.noone.mybobblekeyboard.AppApplication;
import com.example.noone.mybobblekeyboard.R;
import com.example.noone.mybobblekeyboard.base.BaseFragment;
import com.example.noone.mybobblekeyboard.dictionarydata.DictionaryDataDownloadService;
import com.example.noone.mybobblekeyboard.home.di.DaggerHomeComponent;
import com.example.noone.mybobblekeyboard.home.di.HomeComponent;
import com.example.noone.mybobblekeyboard.home.di.modules.HomeModule;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeView {

    Button button;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // call api
        mPresenter.actionOnActivityCreated();
    }

    @Override
    protected void setupFragmentDIComponent() {
        HomeComponent dummyComponent = DaggerHomeComponent.builder()
                .applicationComponent(AppApplication.getApplicationComponent())
                .homeModule(new HomeModule(this))
                .build();
        dummyComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView =  inflater.inflate(R.layout.fragment_home, container, false);

        button = parentView.findViewById(R.id.open_settings);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.actionOnSettingButtonClick();
            }
        });
        return parentView;
    }

    @Override
    public void startDictionaryDataDownloadService() {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), DictionaryDataDownloadService.class);
            getActivity().startService(intent);
        }
    }

    @Override
    public void askForStoragePermission(int requestCode) {
        if (getActivity() != null) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
        }
    }

    @Override
    public boolean isStoragePermissionGranted() {
        return Build.VERSION.SDK_INT < 23 || getActivity() == null ||
                getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

    }

    @Override
    public void openSettingScreen() {
        startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void showToast(String message) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }
}
