package com.soyuul.documentsummary.document.dto;

import java.time.LocalDateTime;

public class DocumentDTO {

    private Long documentId;
    private String documentTitle;
    private String filePath;
    private LocalDateTime uploadedAt;

    public DocumentDTO() {
    }

    public DocumentDTO(Long documentId, String documentTitle, String filePath, LocalDateTime uploadedAt) {
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

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    @Override
    public String toString() {
        return "DocumentDTO{" +
                "documentId=" + documentId +
                ", documentTitle='" + documentTitle + '\'' +
                ", filePath='" + filePath + '\'' +
                ", uploadedAt=" + uploadedAt +
                '}';
    }
}
