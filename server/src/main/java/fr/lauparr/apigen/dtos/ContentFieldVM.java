package fr.lauparr.apigen.dtos;

import fr.lauparr.apigen.enums.EnumContentFieldRelationType;
import fr.lauparr.apigen.enums.EnumContentFieldType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentFieldVM {

  private String contentId;

  private String name;
  private EnumContentFieldType type;
  private boolean nullable;
  private String length;

  private EnumContentFieldRelationType relationType;
  private String relationContent;
}
