package fr.lauparr.apigen.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumContentFieldType {
  STRING("String"),
  TEXT("Text"),
  RICHTEXT("Rich Text"),
  UID("Uid"),
  NUMBER("Number"),
  DATE("Date"),
  TIME("Time"),
  DATETIME("Date/Time"),
  RELATION("Relation");

  @Getter
  private final String name;

  EnumContentFieldType(String name) {
    this.name = name;
  }

  public String getCode() {
    return this.name();
  }
}
