package fr.lauparr.apigen.entities.callbacks;

import fr.lauparr.apigen.entities.Content;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveCallback;
import org.springframework.stereotype.Component;

@Component
public class ContentCallback implements BeforeSaveCallback<Content> {
	@Override
	public Content onBeforeSave(Content entity, Document document, String collection) {
		entity.setSlug(entity.getSnakeCaseName());
		return entity;
	}
}
