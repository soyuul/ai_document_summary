package com.soyuul.documentsummary.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfig {

    /*
    * Dotenv.load() : .env 파일을 찾아서 Key-Value 쌍으로 로드
    * */
    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public String openAiApikey(){
        /*
        * dotenv.get("KEY") : 이 형식으로 Java 코드에서 언제든지 꺼내서 사용할 수 있게 한다.
        * */
        return dotenv.get("OPENAI_API_KEY");
    }

    @Bean
    public String openAiApiUrl(){
        return dotenv.get("OPENAI_API_URL");
    }

    @Bean
    public String openAiModel(){
        return dotenv.get("OPENAI_MODEL");
    }
}
