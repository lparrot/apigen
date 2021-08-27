package fr.lauparr.apigen.repositories;

import fr.lauparr.apigen.entities.Content;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface ContentRepository extends MongoRepository<Content, String> {
}
