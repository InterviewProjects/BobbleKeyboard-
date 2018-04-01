package com.example.noone.mybobblekeyboard.home.di;

import com.example.noone.mybobblekeyboard.di.ApplicationComponent;
import com.example.noone.mybobblekeyboard.di.scopes.PerFragmentScope;
import com.example.noone.mybobblekeyboard.home.HomeFragment;
import com.example.noone.mybobblekeyboard.home.di.modules.HomeModule;

import dagger.Component;

@PerFragmentScope
@Component(dependencies = {ApplicationComponent.class}, modules = {HomeModule.class})
public interface HomeComponent {
    void inject(HomeFragment fragment);
}
