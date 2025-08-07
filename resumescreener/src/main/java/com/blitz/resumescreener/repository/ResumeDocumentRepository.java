package com.blitz.resumescreener.repository;

import com.blitz.resumescreener.model.ResumeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeDocumentRepository extends JpaRepository<ResumeDocument, Long> {
}