package fr.lauparr.apigen.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.lauparr.apigen.services.ContentItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{slug}")
public class ContentItemController {

  @Autowired
  private ContentItemService contentItemService;

  @GetMapping
  public ResponseEntity<?> getAll(@PathVariable String slug, Pageable page, @RequestHeader(required = false) String fields) {
    return ResponseEntity.ok(contentItemService.getAll(slug, page, fields));
  }

  @GetMapping("/{idItem}")
  public ResponseEntity<?> getById(@PathVariable String slug, @PathVariable String idItem, @RequestHeader(required = false) String fields) {
    return ResponseEntity.ok(contentItemService.getById(slug, idItem, fields));
  }

  @PostMapping
  public ResponseEntity<?> create(@PathVariable String slug, @RequestBody(required = false) ObjectNode body) {
    return ResponseEntity.status(201).body(contentItemService.create(slug, body));
  }

  @PutMapping("/{idItem}")
  public ResponseEntity<?> update(@PathVariable String slug, @PathVariable String idItem, @RequestBody(required = false) ObjectNode body) {
    return ResponseEntity.ok(contentItemService.update(slug, idItem, body));
  }

  @DeleteMapping("/{idItem}")
  public ResponseEntity<?> remove(@PathVariable String slug, @PathVariable String idItem) {
    return ResponseEntity.ok(contentItemService.remove(slug, idItem));
  }

}
