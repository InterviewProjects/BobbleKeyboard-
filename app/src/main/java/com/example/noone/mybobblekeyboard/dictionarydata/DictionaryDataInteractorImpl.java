package com.example.noone.mybobblekeyboard.dictionarydata;

import android.os.Environment;

import com.example.noone.mybobblekeyboard.base.BaseInteracterImpl;
import com.example.noone.mybobblekeyboard.network.ApiService;

import java.io.File;
import java.io.FileOutputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class DictionaryDataInteractorImpl extends BaseInteracterImpl implements DictionaryDataInteractor {
    private ApiService mApiService;

    public DictionaryDataInteractorImpl(ApiService apiService, CompositeDisposable compositeDisposable) {
        this.mApiService = apiService;
        this.mCompositeDisposable = compositeDisposable;
    }

    @Override
    public void fetchDictionaryFromNetwork(final DictionaryDataInteractor.FetchDictionaryCallback listener) {
        mCompositeDisposable.add(mApiService.fetchDictionaryFromNetwork()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody o) {
                        listener.onSuccess(o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public boolean isFileExist(File externalStorageDirectory, String fileName) {
        boolean returnValue = false;
        try {
            File file = new File(externalStorageDirectory, fileName);
            returnValue = file.exists();
        } catch (Exception e) {
            // Exception
        }

        return returnValue;
    }

    @Override
    public File getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }

    @Override
    public void saveDictionaryFileIntoStorage(byte[] dictionaryData, File externalStorageDirectory, String fileName) {
        try {
            File file = new File(externalStorageDirectory, fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(dictionaryData);
        }
        catch (Exception ex) {
            // Exception
        }
    }
}
