package fr.lauparr.apigen.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.mongodb.DBRef;
import fr.lauparr.apigen.utils.SpringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.util.Objects;

public class DBRefSerializer extends JsonSerializer<DBRef> {

  @Override
  public void serialize(DBRef value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
    Object refItem = Objects.requireNonNull(SpringUtils.getBean(MongoTemplate.class)).findById(value.getId(), Object.class, value.getCollectionName());
    jgen.writeObject(JsonNodeFactory.instance.pojoNode(refItem));
  }
}
