package fr.lauparr.apigen.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValueTextDTO {

  private String text;
  private Object value;

  @Builder
  public ValueTextDTO(String text, Object value) {
    this.text = text;
    this.value = value;
  }
}
