package fr.lauparr.apigen.repositories;

import fr.lauparr.apigen.entities.Content;
import fr.lauparr.apigen.entities.ContentField;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContentFieldRepository extends MongoRepository<ContentField, String> {
}
