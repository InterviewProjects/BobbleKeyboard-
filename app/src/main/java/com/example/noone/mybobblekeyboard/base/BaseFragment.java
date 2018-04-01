package com.example.noone.mybobblekeyboard.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;


import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {
    protected ProgressBar progressBar;

    @Inject
    protected T mPresenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        mPresenter.onViewActive(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupFragmentDIComponent();
    }

    protected void setupFragmentDIComponent() {}

    public void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.onViewInactive();
    }
}
