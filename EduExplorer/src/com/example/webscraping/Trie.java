package com.example.webscraping;

import com.example.webscraping.TrieNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Trie {
    TrieNode root;


    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
        }
        current.isEndOfWord = true;
    }

    public List<String> getWordsWithPrefix(String prefix) {
        List<String> words = new ArrayList<>();
        TrieNode current = root;
        for (char c : prefix.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return words; // No words with this prefix
            }
            current = current.children.get(c);
        }
        collectWords(current, prefix, words);
        return words;
    }

    public void collectWords(TrieNode node, String prefix, List<String> words) {
        if (node.isEndOfWord) {
            words.add(prefix);
        }
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            collectWords(entry.getValue(), prefix + entry.getKey(), words);
        }
    }
}