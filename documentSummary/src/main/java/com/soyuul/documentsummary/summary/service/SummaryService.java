package com.soyuul.documentsummary.summary.service;

import com.soyuul.documentsummary.document.repository.DocumentRepository;
import com.soyuul.documentsummary.entity.document.TblDocument;
import com.soyuul.documentsummary.entity.summary.TblSummary;
import com.soyuul.documentsummary.openAi.service.OpenAIService;
import com.soyuul.documentsummary.summary.dto.SummaryDTO;
import com.soyuul.documentsummary.summary.repository.SummaryRepository;
import com.soyuul.documentsummary.util.PdfUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SummaryService {

    private static final Logger log = LoggerFactory.getLogger(SummaryService.class);

    private final SummaryRepository summaryRepository;
    private final OpenAIService openAIService;
    private final ModelMapper modelMapper;

    @Value("${file.upload.path.summary-files}")
    private String FILE_DIR;

    @Autowired
    public SummaryService(SummaryRepository summaryRepository, OpenAIService openAIService, ModelMapper modelMapper) {
        this.summaryRepository = summaryRepository;
        this.openAIService = openAIService;
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
    public Object saveSummary(TblDocument document, String keyword) throws IOException {
        log.info("[SummaryService] saveSummary Transactional start...");
        log.info("summary keyword : {}", keyword);

//      1. 저장된 파일 경로로부터 텍스트 추출
        Path path = Paths.get(FILE_DIR, document.getFilePath());
        String text = PdfUtils.extractTextFromPdf(path);

//      2. OpenAI API 호출해 요약 생성
        String summaryContent = openAIService.summarizeText(text, keyword);

//      3. 요약 DB에 저장
        TblSummary summary = new TblSummary();
        summary.setDocument(document);
        summary.setKeyword(keyword);
        summary.setSummaryContent(summaryContent);
        summary.setSectionReference(null);

        summaryRepository.save(summary);

        return modelMapper.map(summary, TblSummary.class);
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
