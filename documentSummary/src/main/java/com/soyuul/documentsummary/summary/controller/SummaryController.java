package com.soyuul.documentsummary.summary.controller;

import com.soyuul.documentsummary.common.ResponseDTO;
import com.soyuul.documentsummary.document.dto.DocumentDTO;
import com.soyuul.documentsummary.summary.dto.SummaryDTO;
import com.soyuul.documentsummary.summary.service.SummaryService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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


    @Operation(summary = "문서 요약 상세 조회", description = "문서에 대한 요약 상세 조회가 진행됩니다.", tags = {"SummaryController"})
    @GetMapping("/list/{summaryId}")
    public ResponseEntity<ResponseDTO> findSummaryDetail(@PathVariable("summaryId") Long summaryId){
        log.info("[SummaryController] findSummary start...");

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "요약 상세 조회 성공", summaryService.findSummaryDetail(summaryId)));
    }


    /*
     * ReponseEntity<Resource> : 파일 리소스를 HTTP 응답으로 감싸서 반환 (브라우저가 다운로드해야 할 파일로 인식)
     *
     * .contentType() : 응답 데이터의 MIME 타입 지정 (여기선 application/octet-stream으로 일반 바이너리 파일 지정)
     *                  => 이 정보로 브라우저가 다운로드해야 할 파일로 인식
     * .header() : 다운로드를 유도하는 HTTP 헤더 설정
     *              - Content-Disposition: attachment; filename="파일명"
     *              => 이걸 지정하지 않으면 브라우저가 그냥 페이지로 열어버릴 수 있다.
     * .body() : 실제 파일 데이터
     * */
    @Operation(summary = "요약한 파일 다운로드", description = "요약한 파일 다운로드가 진행됩니다.", tags = {"SummaryController"})
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadSummary(@RequestParam("filename") String filename) throws IOException {
        log.info("[SummaryController] downloadSummary start...");

        Resource resource = summaryService.downloadSummary(filename);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @Operation(summary = "문서 요약 등록 요청", description = "문서에 대한 요약 등록이 진행됩니다.", tags = {"SummaryController"})
    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO> saveSummary(@RequestBody DocumentDTO dto,
                                                   @RequestParam(value = "keyword", required = false) String keyword) throws IOException {
        log.info("[SummaryController] saveSummary start...");

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "요약 저장 성공", summaryService.saveSummary(dto, keyword)));
    }


    @Operation(summary = "문서 요약 수정 요청", description = "문서에 대한 요약 수정이 진행됩니다.", tags = {"SummaryController"})
    @PutMapping("/update/{summaryId}")
    public ResponseEntity<ResponseDTO> updateSummary(@PathVariable("summaryId") Long summaryId,
                                                     @RequestBody SummaryDTO dto) {
        log.info("[SummaryController] updateSummary start...");

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "요약 수정 성공", summaryService.updateSummary(summaryId, dto)));
    }


    @Operation(summary = "문서 요약 삭제", description = "문서에 대한 요약 삭제가 진행됩니다.", tags = {"SummaryController"})
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteSummary(@RequestParam("summaryId") Long summaryId){
        log.info("[SummaryController] deleteSummary start...");

        summaryService.deleteSummary(summaryId);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "요약 삭제 성공", null));
    }

}