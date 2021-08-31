package fr.lauparr.apigen.services;

import com.mongodb.DBRef;
import fr.lauparr.apigen.dtos.ContentFieldVM;
import fr.lauparr.apigen.entities.Content;
import fr.lauparr.apigen.entities.ContentField;
import fr.lauparr.apigen.exceptions.DataNotFoundException;
import fr.lauparr.apigen.repositories.ContentFieldRepository;
import fr.lauparr.apigen.repositories.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentFieldService {

  @Autowired
  private DatabaseService databaseService;
  @Autowired
  private ContentFieldRepository contentFieldRepository;
  @Autowired
  private ContentRepository contentRepository;

  public List<ContentField> getContentFields() {
    return contentFieldRepository.findAll(Sort.by("name"));
  }

  public ContentField getContentFieldById(String idContentField) {
    return contentFieldRepository.findById(idContentField).orElseThrow(DataNotFoundException::new);
  }

  public ContentField createContentField(ContentFieldVM body) {
    Content content = contentRepository.findById(body.getContentId()).orElseThrow(DataNotFoundException::new);

    ContentField field = new ContentField();

    updateFieldWithBody(field, body);

    field = contentFieldRepository.save(field);

    content.getFields().add(field);

    contentRepository.save(content);

    return field;
  }

  public ContentField updateContentField(String idContentField, ContentFieldVM body) {
    ContentField field = contentFieldRepository.findById(idContentField).orElseThrow(DataNotFoundException::new);

    updateFieldWithBody(field, body);

    field = contentFieldRepository.save(field);

    return field;
  }

  private void updateFieldWithBody(ContentField field, ContentFieldVM body) {
    field.setName(body.getName());
    field.setType(body.getType());
    field.setNullable(body.isNullable());
    field.setParams(body.getParams());

    // Switch for params
    switch (field.getType()) {
      case RELATION:
        field.getParams().put("relationContent", new DBRef("content", body.getParams().get("relationContent")));
//        field.getParams().put("relationType", body.getRelationType());
        break;
      case STRING:
//        field.getParams().put("password", body.getParams().get("password"));
        break;
      default:
        break;
    }
  }

  public void deleteContentField(String idContentField) {
    ContentField content = contentFieldRepository.findById(idContentField).orElseThrow(DataNotFoundException::new);

    contentFieldRepository.delete(content);
  }
}
