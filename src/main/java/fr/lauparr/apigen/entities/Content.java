package fr.lauparr.apigen.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import static fr.lauparr.apigen.utils.StringUtils.toSnakeCase;

@Getter
@Setter
@Document
public class Content {

	@Id
	private String id;
	@Indexed
	private String name;
	@Indexed
	private String slug;
	@Indexed
	private String displayedField;
	@DBRef
	private List<ContentField> contentFields;

	@Builder
	public Content(String name, String slug, String displayedField, @Singular List<ContentField> contentFields) {
		this.name = name;
		this.slug = slug;
		this.displayedField = displayedField;
		this.contentFields = contentFields;
	}

	@JsonIgnore
	public String getSnakeCaseName() {
		return toSnakeCase(this.name);
	}
}
