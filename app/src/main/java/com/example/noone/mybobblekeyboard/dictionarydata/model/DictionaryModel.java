package com.example.noone.mybobblekeyboard.dictionarydata.model;

public class DictionaryModel {
    String word;
    int frequency;

    public DictionaryModel(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
