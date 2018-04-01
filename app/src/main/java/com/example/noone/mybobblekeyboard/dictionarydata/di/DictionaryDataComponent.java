package com.example.noone.mybobblekeyboard.dictionarydata.di;

import com.example.noone.mybobblekeyboard.di.ApplicationComponent;
import com.example.noone.mybobblekeyboard.di.scopes.PerServiceScope;
import com.example.noone.mybobblekeyboard.dictionarydata.DictionaryDataDownloadService;
import com.example.noone.mybobblekeyboard.dictionarydata.di.module.DictionaryDataModule;

import dagger.Component;

@PerServiceScope
@Component(dependencies = {ApplicationComponent.class}, modules = {DictionaryDataModule.class})
public interface DictionaryDataComponent {
    void inject(DictionaryDataDownloadService service);
}