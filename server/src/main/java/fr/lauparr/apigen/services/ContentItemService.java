package fr.lauparr.apigen.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.DBRef;
import fr.lauparr.apigen.config.jackson.JsonNodeToDocumentConverter;
import fr.lauparr.apigen.entities.Content;
import fr.lauparr.apigen.enums.EnumContentFieldType;
import fr.lauparr.apigen.exceptions.DataNotFoundException;
import fr.lauparr.apigen.repositories.ContentRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentItemService {

  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private ContentRepository contentRepository;

  public Page getAll(String slug, Pageable page) {
    Query query = new Query().with(page);
    List<?> list = mongoTemplate.find(query, Document.class, slug);
    return PageableExecutionUtils.getPage(
      list,
      page,
      () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Document.class, slug));
  }

  public Object getById(String slug, String idItem) {
    return mongoTemplate.findById(idItem, Document.class, slug);
  }

  public Object create(String slug, ObjectNode body) {
    Content content = contentRepository.findBySlug(slug).orElseThrow(DataNotFoundException::new);

    Document document = getDocumentFromBodyAndLinkDbRef(body, content);
    if (document == null) {
      return null;
    }

    return mongoTemplate.insert(document, slug);
  }

  public Object update(String slug, String idItem, ObjectNode body) {
    Content content = contentRepository.findBySlug(slug).orElseThrow(DataNotFoundException::new);

    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(idItem));

    Document document = getDocumentFromBodyAndLinkDbRef(body, content);
    if (document == null) {
      return null;
    }

    Update update = Update.fromDocument(document);

    return mongoTemplate.updateFirst(query, update, slug);
  }

  public Object remove(String slug, String idItem) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(idItem));
    return mongoTemplate.remove(query, slug);
  }

  private Document getDocumentFromBodyAndLinkDbRef(ObjectNode body, Content content) {
    Document document = JsonNodeToDocumentConverter.INSTANCE.convert(body);

    if (document == null) {
      return null;
    }

    content.getFields().stream().filter(field -> EnumContentFieldType.RELATION.equals(field.getType())).forEach(field -> {
      document.put(field.getFieldName(), new DBRef(field.getParams().get("relationContent", Content.class).getCollectionName(), document.get(field.getFieldName())));
    });
    return document;
  }
}
