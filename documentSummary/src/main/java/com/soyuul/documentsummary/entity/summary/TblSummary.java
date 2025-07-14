package com.soyuul.documentsummary.entity.summary;

import com.soyuul.documentsummary.entity.document.TblDocument;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "summary")
public class TblSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "summary_id", nullable = false)
    private Long summaryId;

    @ManyToOne(fetch = FetchType.LAZY)
//    연관 관계 매칭 어노테이션이라 기본 타입 필드에서 사용하는 어노테이션을 사용하면 안된다
//    @Column(name = "document_id", nullable = false)
    @JoinColumn(name = "document_id", nullable = false)
    private TblDocument document;

    @Size(max = 255)
    @Column(name = "keyword")
    private String keyword;

//    @Lob : TEXT 타입으로 사용했는데 Hibernate가 oid로 매핑하는 오류
    @Column(name = "summary_content", columnDefinition = "TEXT", nullable = false)
    private String summaryContent;

    @CreationTimestamp
    @Column(name = "summary_created_at", nullable = false)
    private LocalDateTime summaryCreatedAt;

    @Column(name = "section_reference", columnDefinition = "TEXT")
    private String sectionReference;

    public TblSummary() {
    }

    public TblSummary(Long summaryId, TblDocument document, String keyword, String summaryContent, LocalDateTime summaryCreatedAt, String sectionReference) {
        this.summaryId = summaryId;
        this.document = document;
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

    public TblDocument getDocument() {
        return document;
    }

    public void setDocument(TblDocument document) {
        this.document = document;
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
}
