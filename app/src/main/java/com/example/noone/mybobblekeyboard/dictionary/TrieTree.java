package com.example.noone.mybobblekeyboard.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used Trie Tree for storing all the words
 */

public class TrieTree {
    class TrieNode {
        char c;
        Map<Character, TrieNode> children;
        boolean isLeaf;

        TrieNode() {
            this.children = new HashMap<>();
        }

        TrieNode(char c){
            this.children = new HashMap<>();
            this.c = c;
        }
    }

    private TrieNode root;

    TrieTree() {
        root = new TrieNode();
    }

    public void insert(String word) {
        Map<Character, TrieNode> children = root.children;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            TrieNode t;
            if (children.containsKey(c)) {
                t = children.get(c);
            } else {
                t = new TrieNode(c);
                children.put(c, t);
            }

            children = t.children;

            if(i == word.length() - 1) {
                t.isLeaf = true;
            }
        }

//        print();
    }

    public void delete(String word) {
        if (word != null && !word.isEmpty() && root != null) {
            TrieNode parent = root;

            boolean isCompleteWordMatch = false;
            boolean isPrefixOfOtherWords = false;
            boolean isSuffixOfOtherWords = false;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);

                if (parent.children.containsKey(c)) {
                    parent = parent.children.get(c);
                } else {
                    break;
                }

                if (i == word.length() - 1 && parent.isLeaf) {
                    isCompleteWordMatch = true;
                    isPrefixOfOtherWords = !parent.children.isEmpty();
                } else if (parent.isLeaf){
                    isSuffixOfOtherWords = true;
                }
            }

            if (isCompleteWordMatch) {
                parent.isLeaf = false;

                if (!isPrefixOfOtherWords && !isSuffixOfOtherWords) {
                    parent = root;
                    for (int i = 0; i < word.length(); i++) {
                        char c = word.charAt(i);

                        if (parent.children.containsKey(c)) {
                            TrieNode tempNode = parent;
                            parent = parent.children.get(c);
                            tempNode.children.remove(c);
                        }
                    }
                }
            }
        }

//        print();
    }

    public List<String> getAllSuggestedWordStartsWith(String word) {
        List<String> returnValue = new ArrayList<>();

        if (word != null && !word.isEmpty() && root != null) {
            TrieNode parent = root;

            boolean isCompleteWordMatch = true;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);

                if (parent.children.containsKey(c)) {
                    parent = parent.children.get(c);
                } else {
                    isCompleteWordMatch = false;
                    break;
                }
            }

            if (isCompleteWordMatch) {
                getAllWordsStartsFromNode(parent, new StringBuilder(word), returnValue);
            }
        }
        return returnValue;
    }

    private void getAllWordsStartsFromNode(TrieNode trieNode, StringBuilder word, List<String> wordList) {
        if (trieNode == null) return;
        if (trieNode.isLeaf) wordList.add(word.toString());

        for (Map.Entry<Character, TrieNode> entry : trieNode.children.entrySet()) {
            word.append(entry.getKey());
            getAllWordsStartsFromNode(entry.getValue(), word, wordList);
            word.delete(word.length() - 1, word.length());
        }
    }

    private void print() {
        List<String> wordList = new ArrayList<>();
        getAllWordsStartsFromNode(root, new StringBuilder(), wordList);

        System.out.println("trie word List");
        for (String word : wordList) {
            System.out.print(word + " ");
        }
        System.out.println();
    }
}
