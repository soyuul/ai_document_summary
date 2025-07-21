package com.soyuul.documentsummary.document.service;

import com.soyuul.documentsummary.document.dto.DocumentDTO;
import com.soyuul.documentsummary.document.repository.DocumentRepository;
import com.soyuul.documentsummary.entity.document.TblDocument;
import com.soyuul.documentsummary.summary.dto.SummaryDTO;
import com.soyuul.documentsummary.util.FileUploadUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.Document;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DocumentService {

    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);

    private final DocumentRepository documentRepository;
    private final ModelMapper modelMapper;

    @Value("${file.upload.path.summary-files}")
    private String FILE_DIR;

    @Autowired
    public DocumentService(DocumentRepository documentRepository, ModelMapper modelMapper) {
        this.documentRepository = documentRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    public DocumentDTO saveDocument(MultipartFile file) throws IOException{

        log.info("[DocumentService] saveDocument Transactional start...");
        log.info("document file : {}", file);

        //      1. 파일 저장
        /*파일 경로 지정 (.yml에 지정된 경로)*/
        String uploadPath = FILE_DIR;

        String savedFileName;
        try{
            /*
             * FileUploadUtils.saveFile()
             * - uploadPath : 실제 저장될 경로
             * - file.getOriginalFilename() : 사용자가 업로드한 파일명
             * - file : 업로드된 MultipartFile 객체
             *
             * => 파일이 없으면 디렉토리 생성, 고유한 파일명으로 저장
             * */
            savedFileName = FileUploadUtils.saveFile(uploadPath, file.getOriginalFilename(), file);
        }catch (IOException ex){
            throw new RuntimeException("파일 저장 실패", ex);
        }

        //      2. TblDocument 생성 → 저장
        /*
         * TblDocument 생성
         * setDocumentTitle() : file에 원래 이름 저장
         * setFilePath() : 경로 새로 저장
         * */
        TblDocument document = new TblDocument();
        document.setDocumentTitle(file.getOriginalFilename());
        document.setFilePath(savedFileName);

        /*
         * TblDocument에 새로 등록된 내용 저장
         * */
        TblDocument saveDocument = documentRepository.save(document);

        log.info("Document 저장 완료 : {}", saveDocument.getDocumentId());

        DocumentDTO res = modelMapper.map(saveDocument, DocumentDTO.class);
        return res;

    }

    public TblDocument findDocumentById(Long documentId){
//        DB에서 document 조회
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException(("해당 문서를 찾을 수 없습니다. ID: "+ documentId)));
    }

    public String loadTextByDocument(TblDocument document){

//        파일 경로 읽기
        Path path = Paths.get(FILE_DIR, document.getFilePath());

//        파일에서 텍스트 읽기
        try{
            return Files.readString(path, StandardCharsets.UTF_8);
        }catch (IOException e){
            throw new RuntimeException("문서 파일을 읽는데 실패했습니다. 경로: " + path, e);
        }

//        String filePath = document.getFilePath();
//
//        if (filePath.endsWith(".pdf")) {
//            return PdfUtils.extractTextFromPdf(Paths.get(filePath));
//        } else if (filePath.endsWith(".txt")) {
//            return TxtUtils.extractTextFromTxt(Paths.get(filePath));
//        } else {
//            throw new IllegalArgumentException("지원하지 않는 파일 형식입니다: " + filePath);
//        }
    }
}
