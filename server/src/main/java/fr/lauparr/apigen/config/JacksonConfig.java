package fr.lauparr.apigen.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lauparr.apigen.config.object_id.CustomObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

  @Bean
  public ObjectMapper objectMapper() {
    return new CustomObjectMapper();
  }

}
