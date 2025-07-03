package com.soyuul.documentsummary.entity.document;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "document")
public class TblDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)         //  자동 생성
    @Column(name = "document_id", nullable = false)
    private Long documentId;

    @Size(max = 225)
    @Column(name = "document_title", nullable = false)
    private String documentTitle;

    @Size(max = 500)
    @Column(name = "file_path", nullable = false)
    private String filePath;

    @CreationTimestamp  //  자동으로 시간 입력 가능
    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    public TblDocument() {
    }

    public TblDocument(Long documentId, String documentTitle, String filePath, LocalDateTime uploadedAt) {
        this.documentId = documentId;
        this.documentTitle = documentTitle;
        this.filePath = filePath;
        this.uploadedAt = uploadedAt;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public @Size(max = 225) String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(@Size(max = 225) String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public @Size(max = 500) String getFilePath() {
        return filePath;
    }

    public void setFilePath(@Size(max = 500) String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
