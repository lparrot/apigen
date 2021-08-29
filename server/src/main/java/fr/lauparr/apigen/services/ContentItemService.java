package fr.lauparr.apigen.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.lauparr.apigen.entities.Content;
import fr.lauparr.apigen.exceptions.DataNotFoundException;
import fr.lauparr.apigen.repositories.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ContentItemService {

  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private ContentRepository contentRepository;

  public Page getAll(String slug, Pageable page) {
    Content content = contentRepository.findBySlug(slug).orElseThrow(DataNotFoundException::new);
    Query query = new Query();
    List<?> list = mongoTemplate.find(query, Map.class, slug);
    return PageableExecutionUtils.getPage(
      list,
      page,
      () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Object.class, slug));
  }

  public Object getById(String slug, String idItem) {
    Content content = contentRepository.findBySlug(slug).orElseThrow(DataNotFoundException::new);
    return mongoTemplate.findById(idItem, Object.class, slug);
  }

  public Object create(String slug, ObjectNode body) {
    Content content = contentRepository.findBySlug(slug).orElseThrow(DataNotFoundException::new);
    return mongoTemplate.insert(objectMapper.convertValue(body, Object.class), slug);
  }

  public Object update(String slug, String idItem, ObjectNode body) {
    Content content = contentRepository.findBySlug(slug).orElseThrow(DataNotFoundException::new);
    body.remove("_id");
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(idItem));
    return mongoTemplate.findAndReplace(query, objectMapper.convertValue(body, Object.class), slug);
  }

  public Object remove(String slug, String idItem) {
    Content content = contentRepository.findBySlug(slug).orElseThrow(DataNotFoundException::new);
    Query query = new Query();
    query.addCriteria(Criteria.where("id").is(idItem));
    return mongoTemplate.remove(query, slug);
  }
}
