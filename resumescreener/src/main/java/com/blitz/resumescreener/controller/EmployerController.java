package com.blitz.resumescreener.controller;

import com.blitz.resumescreener.model.ResumeDocument;
import com.blitz.resumescreener.repository.ResumeDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class EmployerController {

    @Autowired
    private ResumeDocumentRepository resumeDocumentRepository;

    @GetMapping("/employer-dashboard")
    public String showEmployerDashboard(Model model) {
        List<ResumeDocument> documents = resumeDocumentRepository.findAll();
        model.addAttribute("documents", documents);
        return "employer-dashboard";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long fileId) {
        ResumeDocument doc = resumeDocumentRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getFileName() + "\"")
                .body(new ByteArrayResource(doc.getData()));
    }
}