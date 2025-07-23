package com.soyuul.documentsummary.summary.dto;

import java.time.LocalDateTime;

public class SummaryDTO {

    private Long summaryId;
    private Long documentId;
    private String documentTitle;     // ✅ 문서 제목
    private String savedFileName;     // ✅ 저장된 파일명 (다운로드용)
    private String keyword;
    private String summaryContent;
    private LocalDateTime summaryCreatedAt;
    private String sectionReference;

    public SummaryDTO() {
    }

    public SummaryDTO(Long summaryId, Long documentId, String documentTitle, String savedFileName, String keyword, String summaryContent, LocalDateTime summaryCreatedAt, String sectionReference) {
        this.summaryId = summaryId;
        this.documentId = documentId;
        this.documentTitle = documentTitle;
        this.savedFileName = savedFileName;
        this.keyword = keyword;
        this.summaryContent = summaryContent;
        this.summaryCreatedAt = summaryCreatedAt;
        this.sectionReference = sectionReference;
    }

    public Long getSummaryId() {
        return summaryId;
    }

    public void setSummaryId(Long summaryId) {
        this.summaryId = summaryId;
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

    public String getSavedFileName() {
        return savedFileName;
    }

    public void setSavedFileName(String savedFileName) {
        this.savedFileName = savedFileName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSummaryContent() {
        return summaryContent;
    }

    public void setSummaryContent(String summaryContent) {
        this.summaryContent = summaryContent;
    }

    public LocalDateTime getSummaryCreatedAt() {
        return summaryCreatedAt;
    }

    public void setSummaryCreatedAt(LocalDateTime summaryCreatedAt) {
        this.summaryCreatedAt = summaryCreatedAt;
    }

    public String getSectionReference() {
        return sectionReference;
    }

    public void setSectionReference(String sectionReference) {
        this.sectionReference = sectionReference;
    }

    @Override
    public String toString() {
        return "SummaryDTO{" +
                "summaryId=" + summaryId +
                ", documentId=" + documentId +
                ", documentTitle='" + documentTitle + '\'' +
                ", savedFileName='" + savedFileName + '\'' +
                ", keyword='" + keyword + '\'' +
                ", summaryContent='" + summaryContent + '\'' +
                ", summaryCreatedAt=" + summaryCreatedAt +
                ", sectionReference='" + sectionReference + '\'' +
                '}';
    }
}