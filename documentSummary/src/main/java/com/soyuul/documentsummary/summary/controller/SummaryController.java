package com.soyuul.documentsummary.summary.controller;

import com.soyuul.documentsummary.common.ResponseDTO;
import com.soyuul.documentsummary.summary.dto.SummaryDTO;
import com.soyuul.documentsummary.summary.service.SummaryService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/summary")
public class SummaryController {

    private static final Logger log = LoggerFactory.getLogger(SummaryController.class);

    private final SummaryService summaryService;

    @Autowired
    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }


    @Operation(summary = "문서 요약 리스트 전체 조회", description = "문서에 대한 요약 리스트 전체 조회가 진행됩니다.", tags = {"SummaryController"})
    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> findListSummary(){

        log.info("[SummaryController] findListSummary start...");

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "요약 리스트 전체 조회 성공", summaryService.findListSummary()));
    }


    @Operation(summary = "문서 요약 등록 요청", description = "문서에 대한 요약 등록이 진행됩니다.", tags = {"SummaryController"})
    @PostMapping("")
    public ResponseEntity<ResponseDTO> saveSummary(@RequestParam("file") MultipartFile file,
                                                   @RequestParam(value = "keyword", required = false) String keyword) throws IOException {

        log.info("[SummaryController] saveSummary start...");

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "요약 저장 성공", summaryService.saveSummary(file, keyword)));
    }



}