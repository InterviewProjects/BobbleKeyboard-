package com.example.noone.mybobblekeyboard.dictionarydata;

import com.example.noone.mybobblekeyboard.base.BaseInteracter;

import java.io.File;

import okhttp3.ResponseBody;

public interface DictionaryDataInteractor extends BaseInteracter {
    String DICTIONARY_NAME = "bobble_dictionary";
    interface FetchDictionaryCallback {
        void onSuccess(ResponseBody responseBody);
        void onFailure();
    }
    void fetchDictionaryFromNetwork(FetchDictionaryCallback callback);

    boolean isFileExist(File externalStorageDirectory, String fileName);
    File getExternalStorageDirectory();
    void saveDictionaryFileIntoStorage(byte[] dictionaryData, File externalStorageDirectory, String fileName);
}
