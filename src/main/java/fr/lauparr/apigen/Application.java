package fr.lauparr.apigen;

import fr.lauparr.apigen.entities.Content;
import fr.lauparr.apigen.entities.ContentField;
import fr.lauparr.apigen.enums.EnumContentFieldType;
import fr.lauparr.apigen.repositories.ContentFieldRepository;
import fr.lauparr.apigen.repositories.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private ContentRepository contentRepository;
	@Autowired
	private ContentFieldRepository contentFieldRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (contentRepository.count() < 1) {
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

			contentFieldRepository.saveAll(Arrays.asList(fieldCcsName, fieldCcsDescription));

			Content projects = Content.builder()
				.name("Projects")
				.displayedField("name")
				.contentField(fieldCcsName)
				.contentField(fieldCcsDescription)
				.build();

			contentRepository.save(projects);
		}
	}
}
