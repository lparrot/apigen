package fr.lauparr.apigen.dtos;

import fr.lauparr.apigen.enums.EnumContentFieldType;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

@Getter
@Setter
public class ContentFieldVM {

  private String contentId;

  private String name;
  private EnumContentFieldType type;
  private boolean nullable;

  private Document params;
}
