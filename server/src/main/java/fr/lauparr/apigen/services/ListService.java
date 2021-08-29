package fr.lauparr.apigen.services;

import fr.lauparr.apigen.enums.EnumContentFieldRelationType;
import fr.lauparr.apigen.enums.EnumContentFieldType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ListService {

  public List<EnumContentFieldType> getAllFieldTypes() {
    return Arrays.asList(EnumContentFieldType.values());
  }

  public List<EnumContentFieldRelationType> getAllFieldRelationTypes() {
    return Arrays.asList(EnumContentFieldRelationType.values());
  }

}
