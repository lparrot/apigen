package fr.lauparr.apigen.config;

import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActuatorConfig {

  @Bean
  @ConditionalOnMissingBean
  public HttpTraceRepository httpTraceRepository() {
    return new InMemoryHttpTraceRepository();
  }

  @Bean
  @ConditionalOnMissingBean
  public AuditEventRepository auditEventRepository() {
    return new InMemoryAuditEventRepository();
  }
}
