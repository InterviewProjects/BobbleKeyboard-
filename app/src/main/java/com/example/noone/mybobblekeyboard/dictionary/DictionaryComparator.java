package com.example.noone.mybobblekeyboard.dictionary;

import com.example.noone.mybobblekeyboard.dictionarydata.model.DictionaryModel;

import java.util.Comparator;

/**
 * Comparator for sorting into decreasing order
 */
public class DictionaryComparator implements Comparator<DictionaryModel> {
    @Override
    public int compare(DictionaryModel o1, DictionaryModel o2) {
        int returnValue;

        if (o1.getFrequency() < o2.getFrequency()) {
            returnValue = 1;
        } else if (o1.getFrequency() > o2.getFrequency()) {
            returnValue = -1;
        } else {
            returnValue = o2.getWord().compareTo(o1.getWord());
        }

        return returnValue;
    }
}
