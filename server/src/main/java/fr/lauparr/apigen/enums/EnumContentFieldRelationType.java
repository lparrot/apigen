package fr.lauparr.apigen.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumContentFieldRelationType {
  ONETOONE("One to One"),
  ONETOMANY("One to Many"),
  MANYTOONE("Many to One"),
  MANYTOMANY("Many to Many");

  @Getter
  private final String label;

  EnumContentFieldRelationType(String label) {
    this.label = label;
  }

  public String getCode() {
    return this.name();
  }
}
