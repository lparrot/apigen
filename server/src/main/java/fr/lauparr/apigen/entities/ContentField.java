package fr.lauparr.apigen.entities;

import fr.lauparr.apigen.enums.EnumContentFieldRelationType;
import fr.lauparr.apigen.enums.EnumContentFieldType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "content-field")
@NoArgsConstructor
public class ContentField {

  @Id
  private ObjectId _id;
  @Indexed
  private String name;
  private String fieldName;
  private String length;
  private boolean primaryKey;
  private boolean nullable;
  private EnumContentFieldType type;

  private EnumContentFieldRelationType relationType;
  @DBRef
  private Content relationContent;

  @Builder
  public ContentField(String name, String length, boolean primaryKey, boolean nullable, EnumContentFieldType type, EnumContentFieldRelationType relationType, Content relationContent) {
    this.name = name;
    this.length = length;
    this.primaryKey = primaryKey;
    this.nullable = nullable;
    this.type = type;
    this.relationType = relationType;
    this.relationContent = relationContent;
  }
}
