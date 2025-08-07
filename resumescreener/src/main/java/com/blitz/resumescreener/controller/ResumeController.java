package com.blitz.resumescreener.controller;

import com.blitz.resumescreener.model.Candidate;
import com.blitz.resumescreener.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class ResumeController {

    private final AnalysisService analysisService;

    @Autowired
    public ResumeController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @GetMapping("/upload")
    public String showUploadPage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("files") MultipartFile[] files, Model model) {
        List<Candidate> candidates = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                Candidate candidate = analysisService.analyze(file);
                if (candidate != null) {
                    candidates.add(candidate);
                }
            }
        }
        Collections.sort(candidates);
        model.addAttribute("candidates", candidates);
        return "results";
    }
}