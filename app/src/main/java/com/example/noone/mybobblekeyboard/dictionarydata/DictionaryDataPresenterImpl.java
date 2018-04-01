package com.example.noone.mybobblekeyboard.dictionarydata;

import com.example.noone.mybobblekeyboard.base.BasePresenterImpl;

import okhttp3.ResponseBody;

import static com.example.noone.mybobblekeyboard.dictionarydata.DictionaryDataInteractor.DICTIONARY_NAME;

public class DictionaryDataPresenterImpl extends BasePresenterImpl<DictionaryDataView, DictionaryDataInteractor> implements DictionaryDataPresenter {
    public DictionaryDataPresenterImpl(DictionaryDataView view, DictionaryDataInteractor interacter) {
        this.mView = view;
        this.mInteractor = interacter;
    }

    @Override
    public void actionOnServiceCreated() {
        boolean isFileExist = mInteractor.isFileExist(mInteractor.getExternalStorageDirectory(), DICTIONARY_NAME);
        if (!isFileExist) {
            mInteractor.fetchDictionaryFromNetwork(new DictionaryDataInteractor.FetchDictionaryCallback() {
                @Override
                public void onSuccess(ResponseBody responseBody) {
                    if (responseBody != null) {
                        try {
                            mInteractor.saveDictionaryFileIntoStorage(responseBody.bytes(),
                                    mInteractor.getExternalStorageDirectory(), DICTIONARY_NAME);

                            mView.stopService();
                        } catch (Exception e) {
                            // Exception
                        }
                    }
                }

                @Override
                public void onFailure() {
                    mView.stopService();
                }
            });
        }
    }
}
