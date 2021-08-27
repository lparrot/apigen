package fr.lauparr.apigen.entities.callbacks;

import fr.lauparr.apigen.entities.Content;
import fr.lauparr.apigen.entities.ContentField;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveCallback;
import org.springframework.stereotype.Component;

@Component
public class ContentFieldCallback implements BeforeSaveCallback<ContentField> {
	@Override
	public ContentField onBeforeSave(ContentField entity, Document document, String collection) {
		entity.setFieldName(entity.getSnakeCaseName());
		return entity;
	}
}
