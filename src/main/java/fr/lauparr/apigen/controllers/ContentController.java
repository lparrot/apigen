package fr.lauparr.apigen.controllers;

import fr.lauparr.apigen.dtos.ContentVM;
import fr.lauparr.apigen.entities.Content;
import fr.lauparr.apigen.exceptions.DataNotFoundException;
import fr.lauparr.apigen.repositories.ContentRepository;
import fr.lauparr.apigen.services.ContentService;
import fr.lauparr.apigen.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contents")
public class ContentController {

	@Autowired
	private ContentService contentService;

	@GetMapping
	public List<Content> index() {
		return contentService.getContents();
	}

	@GetMapping("/{idContent}")
	public Content find(@PathVariable String idContent) {
		return contentService.getContentById(idContent);
	}

	@PostMapping
	public Content create(@RequestBody ContentVM body) {
		return contentService.createContent(body);
	}

	@PutMapping("/{idContent}")
	public Content update(@PathVariable String idContent, @RequestBody ContentVM body) {
		return contentService.updateContent(idContent, body);
	}

	@DeleteMapping("/{idContent}")
	public void delete(@PathVariable String idContent) {
		contentService.deleteContent(idContent);
	}

}
