package fr.lauparr.apigen.services;

import fr.lauparr.apigen.dtos.ValueTextDTO;
import fr.lauparr.apigen.entities.Content;
import fr.lauparr.apigen.exceptions.DataNotFoundException;
import fr.lauparr.apigen.repositories.ContentRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelationService {

  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private ContentRepository contentRepository;

  public List<ValueTextDTO> getAllData(String idContent) {
    Content content = contentRepository.findById(idContent).orElseThrow(DataNotFoundException::new);
    String textField = content.getDisplayedField() != null ? content.getDisplayedField() : "_id";
    return mongoTemplate
      .find(new Query(), Document.class, content.getCollectionName())
      .stream()
      .map(document -> ValueTextDTO.builder().text(document.get(textField).toString()).value(document.get("_id")).build())
      .collect(Collectors.toList());
  }

}
