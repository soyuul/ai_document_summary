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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

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


    public Object findListSummary() {
        log.info("[SummaryService] findListSummary start...");

        List<TblSummary> summaryList = summaryRepository.findAll();

        /*
         * 단일 객체를 변환하는 메서드
         * */
//        SummaryDTO res = modelMapper.map(summaryList, SummaryDTO.class);

        List<SummaryDTO> res = summaryList.stream()
                .map(summary -> modelMapper.map(summary, SummaryDTO.class))
                .toList();

        return res;
    }


    public Object findSummaryDetail(Long summaryId) {
        log.info("[SummaryService] findSummaryDetail start...");
        log.info("summaryId : {}", summaryId);

        /*
        * findById(summaryId)는 Optional<T>를 반환
        * */
//        TblSummary summary = summaryRepository.findById(summaryId);   //  컴페일 에러 발생

        /*
        * Optional 예외 처리 코드
        * summaryRepository.findById(summaryId) : 이 메서드는 리턴값이 Optional<TblSummary> 즉, 값이 없을 수도 있다.
        * .orElseThrow(...) : 값이 없을 경우 예외를 던지라는 의미
        * */
        TblSummary summary = summaryRepository.findById(summaryId)
                .orElseThrow(() -> new NoSuchElementException("ID가 존재하지 않습니다: " + summaryId));

        SummaryDTO res = modelMapper.map(summary, SummaryDTO.class);
        return res;
    }


    public Resource downloadSummary(String filename) throws IOException  {
        log.info("[SummaryService] downloadSummary start...");

        /*
        * .yml 에서 설정한 경로와 전달받은 파일 이름을 조합해 실제 정장된 파일의 경로를 만든다.
        * */
        Path filePath = Paths.get(FILE_DIR).resolve(filename);

        /*
        * 만약 같은 이름의 파일이 없으면 "파일이 존재하지 않습니다 파일명" 예외 발생
        * */
        if(!Files.exists(filePath)){
            throw new FileNotFoundException("파일이 존재하지 않습니다: " + filename);
        }

        /*
        * 파일이 존재하면 해당 경로를 기반으로 UrlResource 객체 생성 (실제 파일의 URL을 리소스로 반환)
        * */
        return new UrlResource(filePath.toUri());
    }


    @Transactional
    public Object saveSummary(MultipartFile file, String keyword) throws IOException {
        log.info("[SummaryService] saveSummary Transactional start...");
        log.info("summary file : {}", file);
        log.info("summary keyword : {}", keyword);

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


    @Transactional
    public Object updateSummary(Long summaryId, SummaryDTO dto) {

        log.info("[SummaryService] updateSummary start...");
        log.info("summaryId : {}", summaryId);

        TblSummary summary = summaryRepository.findById(summaryId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID가 존재하지 않습니다 : " + summaryId));

        summary.setKeyword(dto.getKeyword());
        summary.setSummaryContent(dto.getSummaryContent());
        summary.setSectionReference(dto.getSectionReference());

        return modelMapper.map(summary, SummaryDTO.class);

    }


    @Transactional
    public void deleteSummary(Long summaryId) {
        log.info("[SummaryService] deleteSummary start...");
        log.info("summaryId : {}", summaryId);

        /*
        * deleteById 말고 findById를 사용하는 이유
        * - 삭제 전 존재 여부 확인 가능
        * - 추가 로직 삽입 가능 (로그, 연관파일 삭제, 연관 데이터 삭제 등)
        * */

        TblSummary summary = summaryRepository.findById(summaryId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID가 존재하지 않습니다 : " + summaryId));

        summaryRepository.delete(summary);
    }

}
