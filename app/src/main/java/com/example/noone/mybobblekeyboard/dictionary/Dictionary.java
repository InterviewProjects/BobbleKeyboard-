package com.example.noone.mybobblekeyboard.dictionary;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;

import com.example.noone.mybobblekeyboard.dictionarydata.DictionaryDataDownloadService;
import com.example.noone.mybobblekeyboard.dictionarydata.model.DictionaryModel;
import com.example.noone.mybobblekeyboard.home.SuggestionTrackingHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Dictionary for keyboard suggestion
 */
public class Dictionary {
    private static String DICTIONARY_NAME = "bobble_dictionary";
    private static final int CACHE_SIZE = 11000;
    private LFUCache mCache;
    private TrieTree mTrie;

    public Dictionary() {
        mCache = new LFUCache(CACHE_SIZE);
        mTrie = new TrieTree();
    }

    public void prepareCache(Context context) {
        if (mCache.isEmpty()) {
            List<DictionaryModel> dictionaryList = getDictionaryMapFromStorage();

            if (dictionaryList.isEmpty()) {
                // start service to fetch data
                Intent intent = new Intent(context, DictionaryDataDownloadService.class);
                context.startService(intent);
            } else {
                int minFreq = Integer.MAX_VALUE;
                for (DictionaryModel model : dictionaryList) {
                    put(model.getWord(), model.getFrequency());
                    minFreq = Math.min(minFreq, model.getFrequency());
                }

                mCache.setMinFreq(minFreq);
            }
        }
    }

    private List<DictionaryModel> getDictionaryMapFromStorage() {
        List<DictionaryModel> returnValue = new ArrayList<>();
        try {
            File file = new File(Environment.getExternalStorageDirectory(), DICTIONARY_NAME);
            BufferedReader in = new BufferedReader(new FileReader(file.getPath()));
            String str;
            while ((str = in.readLine()) != null) {
                returnValue.add(new DictionaryModel(str.split(" ")[0], Integer.valueOf(str.split(" ")[1])));
            }
            in.close();
        } catch (IOException e) {
            // Exception
        }

        return returnValue;
    }

    private void put(String word) {
        mCache.put(word, new LFUCache.LFUCallback() {
            @Override
            public void onWordDeletionFromCache(String word) {
                mTrie.delete(word);
            }

            @Override
            public void onWordInsertionIntoCache(String word) {
                mTrie.insert(word);
            }
        });
    }

    private void put(String word, int freq) {
        mCache.put(word, freq, new LFUCache.LFUCallback() {
            @Override
            public void onWordDeletionFromCache(String word) {
                mTrie.delete(word);
            }

            @Override
            public void onWordInsertionIntoCache(String word) {
                mTrie.insert(word);
            }
        });
    }

    public void onWordSelection(Context context, String typedWord, String pickedFromSuggestionsWord, List<String> suggestionList) {
        if (!TextUtils.isEmpty(pickedFromSuggestionsWord)) {
            // add suggestion into cache
            put(pickedFromSuggestionsWord);

            // send tracking
            SuggestionTrackingHelper.sendTracking(context, typedWord, pickedFromSuggestionsWord, suggestionList);

        } else if (!TextUtils.isEmpty(typedWord)) {
            // add suggestion into cache
            put(typedWord);

            // send tracking
            SuggestionTrackingHelper.sendTracking(context, typedWord, pickedFromSuggestionsWord, suggestionList);
        }
    }

    public List<String> getMostFrequentWordMatchesToSuffix(String word) {
        List<String> returnValue = new ArrayList<>();

        List<String> trieList = mTrie.getAllSuggestedWordStartsWith(word.toLowerCase());
        Map<String, LFUCache.Node> cacheMap = mCache.cacheMap();

        List<DictionaryModel> dictionaryModelList = new ArrayList<>();
        for (String trieWord : trieList) {
            dictionaryModelList.add(new DictionaryModel(trieWord, cacheMap.get(trieWord).freq));
        }
        Collections.sort(dictionaryModelList, new DictionaryComparator());

        int i;
        for (i = 0; i < dictionaryModelList.size() && returnValue.size() < 2; i++) {
            if (!word.equals(dictionaryModelList.get(i).getWord())) {
                returnValue.add(dictionaryModelList.get(i).getWord());
            }
        }

        returnValue.add(word);

        for (; i < dictionaryModelList.size() && returnValue.size() < 5; i++) {
            if (!word.equals(dictionaryModelList.get(i).getWord())) {
                returnValue.add(dictionaryModelList.get(i).getWord());
            }
        }

        return returnValue;
    }
}
