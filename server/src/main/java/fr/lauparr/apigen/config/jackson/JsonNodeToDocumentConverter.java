package fr.lauparr.apigen.config.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public enum JsonNodeToDocumentConverter implements Converter<JsonNode, Document> {
  INSTANCE;

  public Document convert(JsonNode source) {
    return Document.parse(source.toString());
  }
}
