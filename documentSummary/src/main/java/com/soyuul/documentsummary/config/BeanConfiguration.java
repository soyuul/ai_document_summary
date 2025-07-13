package com.soyuul.documentsummary.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class BeanConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public MessageSource messageSource(){

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        //  불러올 메시지 파일 경로 지정
        messageSource.setBasename("classpath:/messages/message");

        //  한글 깨짐 방지를 위해 꼭 설정
        messageSource.setDefaultEncoding("UTF-8");

        /*개발 중에 'field.required=필드는 필수입니다' 라는 메시지를 수정했는데 아래 코드가 없으면
        * 서버를 재시작 하지 않는 이상 바뀐 메시지가 적용하지 않는다.
        * 따라서 아래 코드를 작성해줘야 한다.*/
        //  30초 마다 새로 로딩 (개발시에 유용)
        messageSource.setCacheSeconds(30);

        //  기본 로케일을 한국어로 설정
        Locale.setDefault(Locale.KOREA);

        return messageSource;
    }
}
