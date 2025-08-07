package com.blitz.resumescreener.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ResumeParserService {

    public String parse(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        // The try-with-resources here will automatically close the inputStream
        try (InputStream inputStream = file.getInputStream()) {
            if (filename != null && filename.toLowerCase().endsWith(".docx")) {
                return parseDocx(inputStream);
            } else if (filename != null && filename.toLowerCase().endsWith(".pdf")) {
                return parsePdf(inputStream);
            } else {
                // Return empty string or handle as you see fit for unsupported files
                return ""; 
            }
        } catch (IllegalArgumentException e) {
            // Catching potential exceptions from parsing and returning an empty string
            System.err.println(e.getMessage());
            return "";
        }
    }

    private String parseDocx(InputStream inputStream) throws IOException {
        try (XWPFDocument document = new XWPFDocument(inputStream)) {
            try (XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
                return extractor.getText();
            }
        }
    }

    private String parsePdf(InputStream inputStream) throws IOException {
        // First, read the InputStream into a byte array
        byte[] bytes = inputStream.readAllBytes();
        
        // Now, load the PDF from the byte array
        try (PDDocument document = Loader.loadPDF(bytes)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
}