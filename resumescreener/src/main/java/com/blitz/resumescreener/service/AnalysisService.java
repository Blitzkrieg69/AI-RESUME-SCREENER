package com.blitz.resumescreener.service;

import com.blitz.resumescreener.model.Candidate;
import com.blitz.resumescreener.model.ResumeDocument;
import com.blitz.resumescreener.model.User;
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

    public Candidate analyzeAndPrepare(MultipartFile file, User uploader) throws IOException {
        String text = resumeParserService.parse(file);

        if (text == null || text.isEmpty()) {
            System.err.println("Could not parse text from file: " + file.getOriginalFilename());
            return new Candidate(file.getOriginalFilename(), 0); // Return a candidate with 0 score
        }
        
        // Get the detailed keyword matches
        Map<String, Integer> keywordMatches = rankingService.getKeywordMatches(text);
        
        // Calculate the score from those matches
        int score = rankingService.calculateScore(keywordMatches);

        // Create the candidate for immediate display and set all details
        Candidate candidate = new Candidate(file.getOriginalFilename(), score);
        candidate.setKeywordMatches(keywordMatches);
        
        // Prepare the document for saving
        ResumeDocument doc = new ResumeDocument();
        doc.setFileName(file.getOriginalFilename());
        doc.setFileType(file.getContentType());
        doc.setData(file.getBytes());
        doc.setScore(score);
        doc.setUploader(uploader);
        
        // We'll save the doc in the controller, but return the candidate for display
        return candidate;
    }
}