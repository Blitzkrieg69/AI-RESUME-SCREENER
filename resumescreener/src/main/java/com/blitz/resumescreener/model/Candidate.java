package com.blitz.resumescreener.model;

import java.util.Map;

public class Candidate implements Comparable<Candidate> {

    private String filename;
    private int score;
    private Map<String, Integer> keywordMatches; // ADD THIS LINE

    public Candidate(String filename, int score) {
        this.filename = filename;
        this.score = score;
    }

    // --- Getters and Setters ---

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // ADD THIS GETTER AND SETTER
    public Map<String, Integer> getKeywordMatches() {
        return keywordMatches;
    }

    public void setKeywordMatches(Map<String, Integer> keywordMatches) {
        this.keywordMatches = keywordMatches;
    }

    @Override
    public int compareTo(Candidate other) {
        return Integer.compare(other.score, this.score);
    }
}