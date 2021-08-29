package fr.lauparr.apigen.controllers;

import fr.lauparr.apigen.enums.EnumContentFieldRelationType;
import fr.lauparr.apigen.enums.EnumContentFieldType;
import fr.lauparr.apigen.services.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/app/lists")
public class ListController {

  @Autowired
  private ListService listService;

  @GetMapping("/field_types")
  public List<EnumContentFieldType> getAllFieldTypes() {
    return this.listService.getAllFieldTypes();
  }

  @GetMapping("/field_relation_types")
  public List<EnumContentFieldRelationType> getAllFieldRelationTypes() {
    return this.listService.getAllFieldRelationTypes();
  }

}
