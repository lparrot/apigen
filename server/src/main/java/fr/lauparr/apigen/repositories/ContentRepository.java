package fr.lauparr.apigen.repositories;

import fr.lauparr.apigen.entities.Content;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ContentRepository extends MongoRepository<Content, String> {
  Optional<Content> findBySlug(String slug);
}
