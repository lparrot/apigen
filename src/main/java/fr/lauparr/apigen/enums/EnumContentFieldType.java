package fr.lauparr.apigen.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumContentFieldType {
	STRING("String", "varchar", "255"),
	TEXT("Text", "text", null),
	RICHTEXT("Rich Text", "longtext", null),
	UID("Uid", "varchar", "255"),
	NUMBER("Number", "int", "11"),
	DATE("Date", "date", null),
	TIME("Time", "time", null),
	DATETIME("Date/Time", "datetime", null),
	RELATION("Relation", "varchar", "255");

	@Getter
	private final String name;
	@Getter
	private final String databaseType;
	@Getter
	private final String defaultLength;

	EnumContentFieldType(String name, String databaseType, String defaultLength) {
		this.name = name;
		this.databaseType = databaseType;
		this.defaultLength = defaultLength;
	}

	public String getCode() {
		return this.name();
	}
}
