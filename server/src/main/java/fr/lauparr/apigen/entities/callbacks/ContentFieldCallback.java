package fr.lauparr.apigen.entities.callbacks;

import fr.lauparr.apigen.entities.ContentField;
import org.apache.commons.text.CaseUtils;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

@Component
public class ContentFieldCallback implements BeforeConvertCallback<ContentField> {
  @Override
  public ContentField onBeforeConvert(ContentField entity, String collection) {
    entity.setFieldName(CaseUtils.toCamelCase(entity.getName(), false));
    return entity;
  }
}
