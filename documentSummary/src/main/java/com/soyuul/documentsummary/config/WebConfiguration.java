package com.soyuul.documentsummary.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
/*
* Spring MVC 설정을 직접 커스터마이징하겠다는 선언
* 내부적으로 WebMvcConfigurer 인터페이스 활성화
* */
@EnableWebMvc
/*
* implements WebMvcConfigurer
* WebMvcConfigurer를 구현해서 MVC관련 설정을 오버라이팅 할 수 있다.
* */
public class WebConfiguration implements WebMvcConfigurer {

    //  사용자 업로드 파일 경로
    private static final String USER_UPLOAD_HANDLER = "/summaryfiles/**";
    private static final String USER_UPLOAD_LOCATION =
            "file:" + System.getProperty("user.dir") + "/summaryfiles/";

    //  프로젝트 리소스 이미지 경로
    private static final String STATIC_IMAGE_HANDLER = "/summaryimages/**";
    private static final String STATIC_IMAGE_LOCATION =
            "classpath:/summaryimages/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        /*
        * 브라우저가 /summaryimages/로 시작하는 URL로 요청하면
        * 내부적으로 src/main/resources/summaryimages/ 디랙토리 참조
        * */

        //  사용자 업로드 파일 경로 등록
        registry.addResourceHandler(USER_UPLOAD_HANDLER)
                .addResourceLocations(USER_UPLOAD_LOCATION)
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS));     //  1시간 캐싱 (속도 향상)

        //  프로젝트 리소스 이미지 경로 등록
        registry.addResourceHandler(STATIC_IMAGE_HANDLER)
                .addResourceLocations(STATIC_IMAGE_LOCATION)
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS));     //  1시간 캐싱 (속도 향상)
    }

    //  CORS 설정 (프론트와 API 연동 시 필요)
    @Override
    public  void addCorsMappings(CorsRegistry registry){
        //  모든 경로에 대해 CORS 적용
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                //  모든 요청 헤더를 허용
                .allowedHeaders("*")
                //  쿠키, 인증 정보 등을 포함한 요청도 허용
                .allowCredentials(true);
    }
}
