package com.example.noone.mybobblekeyboard.home;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

public class SuggestionTrackingHelper {

    public static void sendTracking(Context context, String typedWord, String pickedFromSuggestionsWord, List<String> suggestionList) {
        System.out.println(typedWord + " " + pickedFromSuggestionsWord + " " + suggestionList);


        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

        Bundle params = new Bundle();
        params.putString("typedWord", typedWord);
        params.putStringArrayList("suggestionList", new ArrayList<>(suggestionList));
        params.putString("SelectedWord", pickedFromSuggestionsWord);
        mFirebaseAnalytics.logEvent("SearchEvent", params);

    }
}
