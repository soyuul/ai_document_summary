package com.soyuul.documentsummary.document.controller;

import com.soyuul.documentsummary.common.ResponseDTO;
import com.soyuul.documentsummary.document.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private static final Logger log = LoggerFactory.getLogger(DocumentController.class);

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }


    @Operation(summary = "문서 저장 요청", description = "문서 저장이 진행됩니다.", tags = {"DocumentController"})
    @PostMapping("")
    public ResponseEntity<ResponseDTO> saveDocument(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("[DocumentController] saveDocument start...");

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "문서 저장 성공", documentService.saveDocument(file)));
    }

}
