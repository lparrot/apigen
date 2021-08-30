package fr.lauparr.apigen.timers;

import fr.lauparr.apigen.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AdminMetricTimer {

  @Autowired
  private ApplicationService applicationService;
  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @Scheduled(fixedRateString = "${info.admin.refresh-time-seconds}")
  public void execute() {
    applicationService.addCpuUsage();
    applicationService.addThread();
    applicationService.addJvmInfo();
    applicationService.addJvmNonHeapInfo();
    simpMessagingTemplate.convertAndSend("/topic/metrics", true);
  }
}
