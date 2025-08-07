package com.blitz.resumescreener.service;

import com.blitz.resumescreener.model.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@Service
public class AnalysisService {

    private final ResumeParserService resumeParserService;
    private final RankingService rankingService;

    @Autowired
    public AnalysisService(ResumeParserService resumeParserService, RankingService rankingService) {
        this.resumeParserService = resumeParserService;
        this.rankingService = rankingService;
    }

    public Candidate analyze(MultipartFile file) {
        try {
            String text = resumeParserService.parse(file);

            if (text == null || text.isEmpty()) {
                System.err.println("Could not parse text from file: " + file.getOriginalFilename());
                return new Candidate(file.getOriginalFilename(), 0);
            }
            
            Map<String, Integer> keywordMatches = rankingService.getKeywordMatches(text);
            int score = rankingService.calculateScore(keywordMatches);

            Candidate candidate = new Candidate(file.getOriginalFilename(), score);
            candidate.setKeywordMatches(keywordMatches);

            return candidate;
            
        } catch (IOException e) {
            System.err.println("Error processing file: " + file.getOriginalFilename());
            e.printStackTrace();
            return null;
        }
    }
}