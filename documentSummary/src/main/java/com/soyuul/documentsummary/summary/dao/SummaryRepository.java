package com.soyuul.documentsummary.summary.dao;

import com.soyuul.documentsummary.entity.summary.TblSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryRepository extends JpaRepository<TblSummary, Long> {

}
