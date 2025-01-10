package com.example.webscraping;

//import com.example.webscraping.Tries;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    Map<Character, com.example.webscraping.TrieNode> children;
    boolean isEndOfWord;

    TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
    }
}
