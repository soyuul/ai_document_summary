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
    private TblDocument Document;

    @Size(max = 255)
    @Column(name = "keyword")
    private String keyword;

    @Lob
    @Column(name = "summary_content", nullable = false)
    private String summaryContent;

    @CreationTimestamp
    @Column(name = "summary_created_at", nullable = false)
    private LocalDateTime summaryCreatedAt;

    @Lob
    @Column(name = "section_reference")
    private String sectionReference;

    public TblSummary() {
    }

    public TblSummary(Long summaryId, TblDocument document, String keyword, String summaryContent, LocalDateTime summaryCreatedAt, String sectionReference) {
        this.summaryId = summaryId;
        Document = document;
        this.keyword = keyword;
        this.summaryContent = summaryContent;
        this.summaryCreatedAt = summaryCreatedAt;
        this.sectionReference = sectionReference;
    }
}
