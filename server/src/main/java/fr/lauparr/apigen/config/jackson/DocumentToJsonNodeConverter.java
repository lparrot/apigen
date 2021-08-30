package fr.lauparr.apigen.config.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.io.IOException;

@ReadingConverter
public enum DocumentToJsonNodeConverter implements Converter<Document, JsonNode> {
  INSTANCE;

  public JsonNode convert(Document source) {

    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readTree(source.toJson());
    } catch (IOException e) {
      throw new RuntimeException("Unable to parse DbObject to JsonNode", e);
    }
  }
}
