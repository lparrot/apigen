package fr.lauparr.apigen.services;

import fr.lauparr.apigen.dtos.CpuUsageDTO;
import fr.lauparr.apigen.dtos.JvmInfoDTO;
import fr.lauparr.apigen.dtos.ThreadInfoDTO;
import io.micrometer.core.instrument.Statistic;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ApplicationService {

  @Getter
  private final List<ThreadInfoDTO> threads = new ArrayList<>();
  @Getter
  private final List<JvmInfoDTO> jvmInfos = new ArrayList<>();
  @Getter
  private final List<JvmInfoDTO> jvmNonHeapInfos = new ArrayList<>();
  @Getter
  private final List<CpuUsageDTO> cpuUsage = new ArrayList<>();

  @Autowired
  private MetricsEndpoint metricsEndpoint;

  @Value("${info.admin.max-chart-info}")
  private long adminMaxChartInfo;

  public void addThread() {
    updateList(this.threads, new ThreadInfoDTO(
      getValueFromMetric("jvm.threads.live", Statistic.VALUE, null),
      getValueFromMetric("jvm.threads.daemon", Statistic.VALUE, null)
    ));
  }

  public void addJvmInfo() {
    updateList(this.jvmInfos, new JvmInfoDTO(
      getValueFromMetric("jvm.memory.used", Statistic.VALUE, Collections.singletonList("area:heap")),
      getValueFromMetric("jvm.memory.committed", Statistic.VALUE, Collections.singletonList("area:heap")),
      getValueFromMetric("jvm.memory.max", Statistic.VALUE, Collections.singletonList("area:heap"))
    ));
  }

  public void addJvmNonHeapInfo() {
    updateList(this.jvmNonHeapInfos, new JvmInfoDTO(
      getValueFromMetric("jvm.memory.used", Statistic.VALUE, Collections.singletonList("area:nonheap")),
      getValueFromMetric("jvm.memory.committed", Statistic.VALUE, Collections.singletonList("area:nonheap")),
      getValueFromMetric("jvm.memory.max", Statistic.VALUE, Collections.singletonList("area:nonheap"))
    ));
  }

  public void addCpuUsage() {
    updateList(this.cpuUsage, new CpuUsageDTO(getValueFromMetric("system.cpu.usage", Statistic.VALUE, null)));
  }

  private <T> void updateList(List<T> workingList, T data) {
    if (workingList.size() >= adminMaxChartInfo) {
      workingList.remove(0);
    }
    workingList.add(data);
  }

  private double getValueFromMetric(String metricName, Statistic statistic, List<String> tags) {
    MetricsEndpoint.Sample metric = metricsEndpoint.metric(metricName, tags == null ? new ArrayList<>() : tags).getMeasurements()
      .stream().filter(measure -> measure.getStatistic().equals(statistic))
      .findFirst().orElse(null);
    return metric != null ? metric.getValue() : 0;
  }
}
