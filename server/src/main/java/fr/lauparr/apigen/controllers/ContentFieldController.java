package fr.lauparr.apigen.controllers;

import fr.lauparr.apigen.dtos.ContentFieldVM;
import fr.lauparr.apigen.entities.ContentField;
import fr.lauparr.apigen.services.ContentFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/app/fields")
public class ContentFieldController {

  @Autowired
  private ContentFieldService contentFieldService;

  @GetMapping
  public List<ContentField> getAll() {
    return this.contentFieldService.getContentFields();
  }

  @GetMapping("/{idContentField}")
  public ContentField getById(@PathVariable String idContentField) {
    return this.contentFieldService.getContentFieldById(idContentField);
  }

  @PostMapping
  public ContentField create(@RequestBody ContentFieldVM body) {
    return this.contentFieldService.createContentField(body);
  }

  @PutMapping("/{idContentField}")
  public ContentField update(@PathVariable String idContentField, @RequestBody ContentFieldVM body) {
    return this.contentFieldService.updateContentField(idContentField, body);
  }

  @DeleteMapping("/{idContentField}")
  public void remove(@PathVariable String idContentField) {
    this.contentFieldService.deleteContentField(idContentField);
  }

}
