package com.soyuul.documentsummary.summary.service;

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

@Service
public class SummaryService {

    private static final Logger log = LoggerFactory.getLogger(SummaryService.class);

    private final SummaryRepository summaryRepository;
    private final ModelMapper modelMapper;

    @Value("${file.upload.path.summary-files}")
    private String FILE_DIR;

    @Value("${file.upload.path.summary-images}")
    private String IMAGE_DIR;

    @Autowired
    public SummaryService(SummaryRepository summaryRepository, ModelMapper modelMapper) {
        this.summaryRepository = summaryRepository;
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
        

//      3. TblDocument 생성 → 저장
//      4. TblSummary 생성 → 저장
//      5. SummaryDTO 로 변환 후 반환

    }
}
