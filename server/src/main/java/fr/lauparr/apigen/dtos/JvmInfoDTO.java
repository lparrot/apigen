package fr.lauparr.apigen.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class JvmInfoDTO {

  private final LocalDateTime timestamp;
  private final double used;
  private final double size;
  private final double max;

  public JvmInfoDTO(double used, double size, double max) {
    this.timestamp = LocalDateTime.now();
    this.used = used;
    this.size = size;
    this.max = max;
  }
}
