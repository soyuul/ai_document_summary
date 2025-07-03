package com.soyuul.documentsummary.document.repository;

import com.soyuul.documentsummary.entity.document.TblDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<TblDocument, Long> {
}
