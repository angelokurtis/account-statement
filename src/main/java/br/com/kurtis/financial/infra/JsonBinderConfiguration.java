package br.com.kurtis.financial.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonBinderConfiguration {

    @Bean(name = "objectMapper")
    public ObjectMapper newObjectMapper() {
        return new ObjectMapper();
    }
}
