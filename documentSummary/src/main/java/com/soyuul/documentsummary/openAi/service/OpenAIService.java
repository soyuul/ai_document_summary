package com.soyuul.documentsummary.openAi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {

    private final static Logger log = LoggerFactory.getLogger(OpenAIService.class);

    private final String apiKey;
    private final String apiUrl;
    private final String model;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    /*
    * OpenAIConfig 클래스에 String 타입의 Bean을 여러개 등록했기 때문에
    * @Qualifier 어노테이션으로 명확하게 주입하지 않으면 모호성이 생긴다
    * 때문에 @Qualifier 어노테이션을 사용
    * */
    public OpenAIService(@Qualifier("openAiApikey") String apiKey,
                         @Qualifier("openAiApiUrl") String apiUrl,
                         @Qualifier("openAiModel") String model) {
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.model = model;
        this.objectMapper = new ObjectMapper();
        this.restTemplate = new RestTemplate();
    }


    @Transactional
    public String summarizeText(String text, String keyword) {
        try{
//            1. 메시지 구성
            /*
            * user 역할을 가진 사람이 text를 보낸다
            * OpenAI에게 text를 요약해달라는 의미
            * => OpenAI에게 지시할때 작성하는 부분
            * */

            String prompt;

            if (keyword != null && !keyword.trim().isEmpty()) {
                prompt = String.format("다음 문서에서 '%s'와 관련된 내용만 200자 이내로 요약해줘:\n%s", keyword, text);
            } else {
                prompt = "다음 문서를 200자 이내로 요약해줘:\n" + text;
            }

            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(Map.of(
                    "role", "user",
                    "content", prompt
            ));

//            2. 요청 본문 생성
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", model);
            requestBody.put("messages", messages);
            requestBody.put("temperature", 0.7);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

//            3. 요청 보내기
            ResponseEntity<String> res = restTemplate.postForEntity(apiUrl, request, String.class);

            if(res.getStatusCode() == HttpStatus.OK){
                JsonNode root = objectMapper.readTree(res.getBody());
                return root
                        .path("choices")
                        .get(0)
                        .path("message")
                        .path("content")
                        .asText()
                        .trim();
            }else{
                throw new RuntimeException("OpenAI 요청 실패: " +res.getStatusCode());
            }
        }catch(Exception e){
            throw new RuntimeException("OpenAI 요약 처리 중 예외 발생: ", e);
        }

    }
}
