package fr.lauparr.apigen.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lauparr.apigen.config.jackson.CustomObjectMapper;
import fr.lauparr.apigen.config.jackson.DocumentToJsonNodeConverter;
import fr.lauparr.apigen.config.jackson.JsonNodeToDocumentConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class JacksonConfig {

  @Bean
  public ObjectMapper objectMapper() {
    return new CustomObjectMapper();
  }

  @Bean
  public MongoCustomConversions mongoCustomConversions() {
    List<Converter<?, ?>> converters = new ArrayList<>();
    converters.add(JsonNodeToDocumentConverter.INSTANCE);
    converters.add(DocumentToJsonNodeConverter.INSTANCE);
    return new MongoCustomConversions(converters);
  }

}
