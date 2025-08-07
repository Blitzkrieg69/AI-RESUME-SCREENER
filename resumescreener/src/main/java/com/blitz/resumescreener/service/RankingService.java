package com.blitz.resumescreener.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RankingService {

    private static final List<String> KEYWORDS = Arrays.asList(
        "java", "spring", "boot", "sql", "api", "microservices",
        "python", "docker", "kubernetes", "aws", "react", "agile"
    );

    public Map<String, Integer> getKeywordMatches(String text) {
        Map<String, Integer> matches = new HashMap<>();
        if (text == null || text.isEmpty()) {
            return matches;
        }

        String lowerCaseText = text.toLowerCase();

        for (String keyword : KEYWORDS) {
            int count = StringUtils.countMatches(lowerCaseText, keyword.toLowerCase());
            if (count > 0) {
                matches.put(keyword, count);
            }
        }
        return matches;
    }

    public int calculateScore(Map<String, Integer> matches) {
        return matches.values().stream().mapToInt(Integer::intValue).sum();
    }
}