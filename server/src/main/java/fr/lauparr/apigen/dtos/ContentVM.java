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

  @Builder
  public ContentVM(String name) {
    this.name = name;
  }
}
