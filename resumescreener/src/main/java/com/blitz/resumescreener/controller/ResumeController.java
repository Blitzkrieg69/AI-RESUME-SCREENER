package com.blitz.resumescreener.controller;

import com.blitz.resumescreener.model.Candidate;
import com.blitz.resumescreener.model.ResumeDocument;
import com.blitz.resumescreener.model.User;
import com.blitz.resumescreener.repository.ResumeDocumentRepository;
import com.blitz.resumescreener.repository.UserRepository;
import com.blitz.resumescreener.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class ResumeController {

    @Autowired private AnalysisService analysisService;
    @Autowired private UserRepository userRepository;
    @Autowired private ResumeDocumentRepository resumeDocumentRepository;

    @GetMapping("/upload")
    public String showUploadPage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("files") MultipartFile[] files,
                                   @AuthenticationPrincipal UserDetails userDetails, Model model) {
        
        User uploader = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Candidate> sessionCandidates = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    // This now returns a Candidate object
                    Candidate candidate = analysisService.analyzeAndPrepare(file, uploader);
                    
                    // Save the document to the database
                    ResumeDocument doc = new ResumeDocument();
                    doc.setFileName(candidate.getFilename());
                    doc.setData(file.getBytes());
                    doc.setFileType(file.getContentType());
                    doc.setScore(candidate.getScore());
                    doc.setUploader(uploader);
                    resumeDocumentRepository.save(doc);

                    // Add the candidate to the list for immediate display
                    sessionCandidates.add(candidate);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        // Sort the results and add them to the model
        Collections.sort(sessionCandidates);
        model.addAttribute("candidates", sessionCandidates);
        
        // Return the results page
        return "results";
    }
}