package fr.lauparr.apigen.services;

import com.mongodb.MongoNamespace;
import fr.lauparr.apigen.entities.Content;
import org.apache.commons.text.CaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {
	@Autowired
	private MongoOperations operations;

	public void createCollection(Content content) {
		operations.createCollection(CaseUtils.toCamelCase(content.getName(), false));
	}

	public void updateCollection(String oldCollectionName, Content content) {
		if (oldCollectionName.equals(content.getCollectionName())) {
			return;
		}
		operations.getCollection(oldCollectionName).renameCollection(new MongoNamespace("apigen", content.getCollectionName()));
	}
}
