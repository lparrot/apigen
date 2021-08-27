package fr.lauparr.apigen.entities.callbacks;

import fr.lauparr.apigen.entities.Content;
import org.apache.commons.text.CaseUtils;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

@Component
public class ContentCallback implements BeforeConvertCallback<Content> {
	@Override
	public Content onBeforeConvert(Content entity, String collection) {
		entity.setCollectionName(CaseUtils.toCamelCase(entity.getName(), false));
		return entity;
	}
}
