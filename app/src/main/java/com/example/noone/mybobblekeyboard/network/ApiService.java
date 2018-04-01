package com.example.noone.mybobblekeyboard.network;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface ApiService {
    @GET("kunaldawn/test_words/master/10000_words.txt")
    Observable<ResponseBody> fetchDictionaryFromNetwork();
}
