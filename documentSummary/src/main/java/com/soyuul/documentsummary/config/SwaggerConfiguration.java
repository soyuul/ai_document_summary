package com.soyuul.documentsummary.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//  Swagger의 기본 정보를 설정하는 어노테이션
@OpenAPIDefinition(
        info = @Info(title = "문서 요약 서비스 API 명세서",
        description = "React, Spring Boot, JPA, AI까지 진행하는 서비스 API 명세서",
        version = "v1")
)

@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi chatOpenApi(){

        //  Swagger 문서에 포함할 API 경로 설정
        String [] paths = {"/ai/**"};

        return GroupedOpenApi.builder()                     //  새로운 OpenAPI 문서 그룹을 만든다
                .group("AI를 이용한 문서 요약 서비스 API")      //  Swagger UI에서 이 그룹을 어떤 이름으로 보여줄지 지정
                .pathsToMatch(paths)                       //  위에 지정한 경로만 문서에 포함 `/ai/**`
                .build();                                 //  설정 완료
    }
}
