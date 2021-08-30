package fr.lauparr.apigen.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContentVM {

  private String name;
  private String displayedField;

  @Builder
  public ContentVM(String name, String displayedField) {
    this.name = name;
    this.displayedField = displayedField;
  }
}
