package fr.lauparr.apigen.config.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.DBRef;
import org.bson.types.ObjectId;

public class CustomObjectMapper extends ObjectMapper {

  public CustomObjectMapper() {
    SimpleModule module = new SimpleModule("ObjectIdmodule");
    module.addSerializer(ObjectId.class, new ObjectIdSerializer());
    module.addSerializer(DBRef.class, new DBRefSerializer());
    this.registerModule(module);

    this.registerModule(new JavaTimeModule());
  }

}
