package fr.lauparr.apigen.config.object_id;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bson.types.ObjectId;

import java.io.IOException;

public class ObjectIdSerializer extends JsonSerializer<ObjectId> {

  @Override
  public void serialize(ObjectId value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
    jgen.writeString(value.toString());
  }
}