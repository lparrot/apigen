package fr.lauparr.apigen.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CpuUsageDTO {

  private final LocalDateTime timestamp;
  private final double used;

  public CpuUsageDTO(double used) {
    this.timestamp = LocalDateTime.now();
    this.used = used * 100;
  }
}
