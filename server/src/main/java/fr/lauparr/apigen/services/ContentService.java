package fr.lauparr.apigen.services;

import fr.lauparr.apigen.dtos.ContentVM;
import fr.lauparr.apigen.entities.Content;
import fr.lauparr.apigen.exceptions.DataNotFoundException;
import fr.lauparr.apigen.repositories.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {

  @Autowired
  private DatabaseService databaseService;
  @Autowired
  private ContentRepository contentRepository;

  public List<Content> getContents() {
    return contentRepository.findAll(Sort.by("name"));
  }

  public Content getContentById(String idContent) {
    return contentRepository.findById(idContent).orElseThrow(DataNotFoundException::new);
  }

  public Content createContent(ContentVM body) {
    Content content = Content.builder()
      .name(body.getName())
      .build();

    content = contentRepository.save(content);
    databaseService.createCollection(content);

    return content;
  }

  public Content updateContent(String idContent, ContentVM body) {
    Content content = contentRepository.findById(idContent).orElseThrow(DataNotFoundException::new);

    String oldCollectionName = content.getCollectionName();

    content.setName(body.getName());

    content = contentRepository.save(content);
    databaseService.updateCollection(oldCollectionName, content);

    return content;
  }

  public void deleteContent(String idContent) {
    Content content = contentRepository.findById(idContent).orElseThrow(DataNotFoundException::new);

    contentRepository.delete(content);
  }
}
