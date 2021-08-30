package fr.lauparr.apigen.controllers;

import fr.lauparr.apigen.dtos.ValueTextDTO;
import fr.lauparr.apigen.services.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/app/relations")
public class RelationController {

  @Autowired
  private RelationService relationService;

  @GetMapping("/{idContent}")
  public List<ValueTextDTO> getAllData(@PathVariable String idContent) {
    return relationService.getAllData(idContent);
  }

}
