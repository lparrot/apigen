package fr.lauparr.apigen.entities.callbacks;

import com.github.slugify.Slugify;
import fr.lauparr.apigen.entities.Content;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

@Component
public class ContentCallback implements BeforeConvertCallback<Content> {
  @Override
  public Content onBeforeConvert(Content entity, String collection) {
    entity.setSlug(new Slugify().slugify(entity.getName()));
    entity.setCollectionName(entity.getSlug());
    return entity;
  }
}
