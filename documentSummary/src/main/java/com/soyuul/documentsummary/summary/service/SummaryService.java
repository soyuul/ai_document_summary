package com.soyuul.documentsummary.summary.service;

import com.soyuul.documentsummary.document.repository.DocumentRepository;
import com.soyuul.documentsummary.entity.document.TblDocument;
import com.soyuul.documentsummary.entity.summary.TblSummary;
import com.soyuul.documentsummary.summary.dto.SummaryDTO;
import com.soyuul.documentsummary.summary.repository.SummaryRepository;
import com.soyuul.documentsummary.util.FileUploadUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class SummaryService {

    private static final Logger log = LoggerFactory.getLogger(SummaryService.class);

    private final SummaryRepository summaryRepository;
    private final DocumentRepository documentRepository;
    private final ModelMapper modelMapper;

    @Value("${file.upload.path.summary-files}")
    private String FILE_DIR;

    public SummaryService(SummaryRepository summaryRepository, DocumentRepository documentRepository, ModelMapper modelMapper) {
        this.summaryRepository = summaryRepository;
        this.documentRepository = documentRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Object saveSummary(MultipartFile file, String keyword) throws IOException {
        log.info("[SummaryService] saveSummary Transactional start...");
        log.info("[SummaryService] summary file : {}", file);
        log.info("[SummaryService] summary keyword : {}", keyword);

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


//      2. 파일 내용 읽기
        /*
        * uploadPath : 지정된 경로
        * savedFileName : 저장된 파일 이름
        * => 두개가 합성된 새로운 경로로 구성된 파일 정보 불러오기
        * */
        Path filePath = Paths.get(uploadPath, savedFileName);

        /*
        * Files.readString() : 지정된 파일 내용 문자로 읽어오기
        * => Java 11 에서 추가
        * */
        String fileContent = Files.readString(filePath);

        log.info("fileContent : {}", fileContent);

        /*
        * Files.readAllLines() : 파일을 한 줄씩 읽어서 각 줄을 List<String>의 한 요소로 만들어서 반환
        * => List<String> = ["안녕하세요", "저는", ...]
        *
        * String.join() : 한 줄씩 나눴던걸 하나로 합치기 위해
        * */
//        List<String> lines = Files.readAllLines(filePath);
//        String fileContent = String.join("\n", lines);

//      3. TblDocument 생성 → 저장
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
        document = documentRepository.save(document);

        log.info("Document 저장 완료 : {}", document.getDocumentId());

//      4. TblSummary 생성 → 저장
        TblSummary summary = new TblSummary();
        summary.setDocument(document);
        summary.setKeyword(keyword);
        summary.setSummaryContent(fileContent);
        summary.setSectionReference(null);

        TblSummary savedSummary = summaryRepository.save(summary);
        log.info("Summary 저장 완료 : {}", summary.getSummaryId());

//      5. SummaryDTO 로 변환 후 반환
        /*
        * modelMapper : Entity에서 DTO로 쉽게 변환하기 위해
        * */
        SummaryDTO res = modelMapper.map(savedSummary, SummaryDTO.class);
        return res;

    }
}
