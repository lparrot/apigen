package fr.lauparr.apigen.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ThreadInfoDTO {

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  private LocalDateTime timestamp;
  private double live;
  private double deamon;

  public ThreadInfoDTO(double live, double deamon) {
    this.timestamp = LocalDateTime.now();
    this.live = live;
    this.deamon = deamon;
  }
}
