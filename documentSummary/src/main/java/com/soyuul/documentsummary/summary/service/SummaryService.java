package com.soyuul.documentsummary.summary.service;

import com.soyuul.documentsummary.summary.repository.SummaryRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummaryService {

    private static final Logger log = LoggerFactory.getLogger(SummaryService.class);

    private final SummaryRepository summaryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SummaryService(SummaryRepository summaryRepository, ModelMapper modelMapper) {
        this.summaryRepository = summaryRepository;
        this.modelMapper = modelMapper;
    }
}
