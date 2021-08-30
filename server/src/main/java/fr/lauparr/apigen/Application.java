package fr.lauparr.apigen;

import fr.lauparr.apigen.dtos.ContentVM;
import fr.lauparr.apigen.entities.Content;
import fr.lauparr.apigen.entities.ContentField;
import fr.lauparr.apigen.enums.EnumContentFieldType;
import fr.lauparr.apigen.repositories.ContentFieldRepository;
import fr.lauparr.apigen.repositories.ContentRepository;
import fr.lauparr.apigen.services.ContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class Application implements CommandLineRunner {

  private static ConfigurableApplicationContext context;

  @Autowired
  private AuditEventRepository auditEventRepository;
  @Autowired
  private ContentRepository contentRepository;
  @Autowired
  private ContentFieldRepository contentFieldRepository;
  @Autowired
  private ContentService contentService;

  public static void main(String[] args) {
    context = SpringApplication.run(Application.class, args);
  }

  public static void restart() {
    if (context != null) {
      log.info("Server restarting ...");

      ApplicationArguments args = context.getBean(ApplicationArguments.class);
      Thread thread = new Thread(() -> {
        context.close();
        context = SpringApplication.run(Application.class, args.getSourceArgs());

        AuditEventRepository auditEventRepository = context.getBean(AuditEventRepository.class);
        auditEventRepository.add(new AuditEvent(null, "APP_RESTARTED"));
      });

      thread.setDaemon(false);
      thread.start();
    } else {
      log.error("Application context cannot be recovered.");
    }
  }

  @Override
  public void run(String... args) {
    generateData();

    auditEventRepository.add(new AuditEvent(null, "APP_STARTED"));
  }

  private void generateData() {
    if (this.contentRepository.count() < 1) {
      ContentField fieldCcsName = ContentField.builder()
        .name("Name")
        .nullable(false)
        .type(EnumContentFieldType.STRING)
        .build();

      ContentField fieldCcsDescription = ContentField.builder()
        .name("Description")
        .nullable(false)
        .type(EnumContentFieldType.TEXT)
        .build();

      this.contentFieldRepository.saveAll(Arrays.asList(fieldCcsName, fieldCcsDescription));

      Content projects = this.contentService.createContent(ContentVM.builder().name("Projects").build());
      projects.setDisplayedField("name");
      projects.setFields(Arrays.asList(fieldCcsName, fieldCcsDescription));

      this.contentRepository.save(projects);
    }
  }
}
