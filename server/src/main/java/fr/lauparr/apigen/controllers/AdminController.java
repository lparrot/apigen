package fr.lauparr.apigen.controllers;

import fr.lauparr.apigen.Application;
import fr.lauparr.apigen.dtos.CpuUsageDTO;
import fr.lauparr.apigen.dtos.JvmInfoDTO;
import fr.lauparr.apigen.dtos.ThreadInfoDTO;
import fr.lauparr.apigen.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/app/admin")
public class AdminController {

  @Autowired
  private ApplicationService applicationService;

  @GetMapping("/cpu_usage")
  public List<CpuUsageDTO> getAllCpuUsageInfo() {
    return applicationService.getCpuUsage();
  }

  @GetMapping("/threads")
  public List<ThreadInfoDTO> getAllThreadInfo() {
    return applicationService.getThreads();
  }

  @GetMapping("/jvm")
  public List<JvmInfoDTO> getAllJvmInfo(@RequestParam(required = false, defaultValue = "true") boolean heap) {
    if (heap) {
      return applicationService.getJvmInfos();
    }
    return applicationService.getJvmNonHeapInfos();
  }

  @PostMapping("/restart")
  public void restart() {
    Application.restart();
  }

}
