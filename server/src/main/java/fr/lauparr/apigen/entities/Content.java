package fr.lauparr.apigen.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document
public class Content {

  @Id
  private ObjectId _id;
  @Indexed
  private String name;
  @Indexed
  private String collectionName;
  @Indexed
  private String slug;
  @Indexed
  private String displayedField;
  @DBRef
  private List<ContentField> fields;

  @Builder
  public Content(String name, String displayedField, @Singular List<ContentField> fields) {
    this.name = name;
    this.displayedField = displayedField;
    this.fields = fields;
  }

  public void addField(ContentField field) {
    this.fields.add(field);
  }
}
