package com.example.noone.mybobblekeyboard.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * Used LFU Cache for storing the word with frequency
 */
public class LFUCache {
    private int minFreq;
    private int capacity;
    private Map<String, Node> nodeMap;
    private Map<Integer, DoublyLinkList> freqLinkListMap;

    LFUCache(int capacity) {
        this.capacity = capacity;
        this.nodeMap = new HashMap<>();
        this.freqLinkListMap = new HashMap<>();
    }

    public void setMinFreq(int minFreq) {
        this.minFreq = minFreq;
    }

    public void put(String word, LFUCallback callback) {
        put(word, 1, callback);
    }

    public void put(String word, int wordFreq, LFUCallback callback) {
        if (capacity == 0) return;

        if (nodeMap.containsKey(word)) {
            Node node = nodeMap.get(word);
            int freq = node.freq;

            freqLinkListMap.get(freq).remove(node);
            if (freqLinkListMap.get(freq).size() == 0) {
                freqLinkListMap.remove(freq);

                if (minFreq == freq) {
                    minFreq = freq + 1;
                }
            }


            if (!freqLinkListMap.containsKey(freq + 1)) {
                freqLinkListMap.put(freq + 1, new DoublyLinkList());
            }

            Node newNode = new Node(word, freq + 1);

            nodeMap.put(word, newNode);
            freqLinkListMap.get(freq + 1).add(newNode);

        } else {
            if (capacity == nodeMap.size()) {
                Node node = freqLinkListMap.get(minFreq).head;

                nodeMap.remove(node.value);

                freqLinkListMap.get(node.freq).remove(node);
                if (freqLinkListMap.get(node.freq).size() == 0) {
                    freqLinkListMap.remove(node.freq);
                }

                callback.onWordDeletionFromCache(node.value);
            }

            if (!freqLinkListMap.containsKey(wordFreq)) {
                freqLinkListMap.put(wordFreq, new DoublyLinkList());
            }

            Node node = new Node(word, wordFreq);

            nodeMap.put(word, node);
            freqLinkListMap.get(wordFreq).add(node);

            minFreq = Math.min(minFreq, wordFreq);
        }

        callback.onWordInsertionIntoCache(word);

//        print();
    }

    public Map<String, Node> cacheMap() {
        return nodeMap;
    }

    public boolean isEmpty() {
        return nodeMap.isEmpty();
    }

    private void print() {
        System.out.print("nodeMap is: " );
        for (Map.Entry<String, Node> entry : nodeMap.entrySet()) {
            System.out.print(entry.getKey() + " " + entry.getValue().value + ":  ");
        }
        System.out.println();

        System.out.println("freqLinkListMap is: " );
        for (Map.Entry<Integer, DoublyLinkList> entry : freqLinkListMap.entrySet()) {
            System.out.print("freq is: " + entry.getKey() + " " );
            entry.getValue().print();
        }
        System.out.println();
    }

    class Node {
        String value;
        int freq;
        Node next;
        Node prev;

        private Node(String value, int freq) {
            this.value = value;
            this.freq = freq;
            this.next = null;
            this.prev = null;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Node && ((Node) obj).value.equals(value);
        }
    }

    class DoublyLinkList {
        Node head;
        Node tail;
        int size;

        private DoublyLinkList() {
            size = 0;
        }

        void add(Node node) {
            if (head == null) {
                head = node;
                tail = head;
            } else if(head.equals(tail)) {
                tail = node;
                head.next = tail;
                tail.prev = head;
            } else {
                tail.next = node;
                node.prev = tail;
                tail = node;
            }

            size++;
        }

        void remove(Node node) {
            if (head.equals(node)) {
                head = head.next;
                if (head != null) head.prev = null;
            } else if (tail.equals(node)) {
                tail = tail.prev;
                if (tail != null) tail.next = null;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
            size--;
        }

        int size() {
            return size;
        }

        void print() {
            System.out.print("front list is: ");
            Node temp = head;
            while(temp != null) {
                System.out.print(temp.value + " ");
                temp = temp.next;
            }
            System.out.println();
        }
    }

    interface LFUCallback {
        void onWordDeletionFromCache(String word);
        void onWordInsertionIntoCache(String word);
    }
}
