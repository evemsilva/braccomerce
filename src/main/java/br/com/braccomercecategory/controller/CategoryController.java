package br.com.braccomercecategory.controller;

import br.com.braccomercecategory.domain.Category;
import br.com.braccomercecategory.domain.dto.CategoryDTO;
import br.com.braccomercecategory.service.CategoryService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/categorias")
public class CategoryController {

    @Autowired private CategoryService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> findById(@PathVariable Integer id) {
	return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody @Valid CategoryDTO categoryDTO) {
	Category category = service.insert(new Category(categoryDTO.getId(), categoryDTO.getName()));
	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(category.getId()).toUri();
	return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody @Valid CategoryDTO categoryDTO, @PathVariable Integer id) {
	Category category = new Category(id, categoryDTO.getName());
	service.update(category);
	return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
	service.delete(id);
	return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
	List<Category> list = service.findAll();
	List<CategoryDTO> listDTO = list.stream().map(CategoryDTO::new).collect(Collectors.toList());
	return ResponseEntity.ok(listDTO);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<CategoryDTO>> findPage(@RequestParam(name = "page", defaultValue = "0") Integer page,
						      @RequestParam(name = "linesPerPage", defaultValue = "24") Integer linesPerPage,
						      @RequestParam(name = "orderBy", defaultValue = "nome") String orderBy,
						      @RequestParam(name = "direction", defaultValue = "ASC") String direction) {
	Page<Category> list = service.findPage(page, linesPerPage, orderBy, direction);
	Page<CategoryDTO> listDTO = list.map(CategoryDTO::new);
	return ResponseEntity.ok(listDTO);
    }

}
