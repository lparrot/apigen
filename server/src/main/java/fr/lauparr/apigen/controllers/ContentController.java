package fr.lauparr.apigen.controllers;

import fr.lauparr.apigen.dtos.ContentVM;
import fr.lauparr.apigen.entities.Content;
import fr.lauparr.apigen.services.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/app/contents")
public class ContentController {

  @Autowired
  private ContentService contentService;

  @GetMapping
  public List<Content> getAll() {
    return this.contentService.getContents();
  }

  @GetMapping("/{idContent}")
  public Content getById(@PathVariable String idContent) {
    return this.contentService.getContentById(idContent);
  }

  @PostMapping
  public Content create(@RequestBody ContentVM body) {
    return this.contentService.createContent(body);
  }

  @PutMapping("/{idContent}")
  public Content update(@PathVariable String idContent, @RequestBody ContentVM body) {
    return this.contentService.updateContent(idContent, body);
  }

  @DeleteMapping("/{idContent}")
  public void remove(@PathVariable String idContent) {
    this.contentService.deleteContent(idContent);
  }

}
