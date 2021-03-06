package br.com.braccomercecategory.service;

import br.com.braccomercecategory.domain.Category;
import br.com.braccomercecategory.repository.CategoryRepository;
import br.com.braccomercecategory.service.exceptions.DataIntegrityException;
import br.com.braccomercecategory.service.exceptions.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired private CategoryRepository repo;

    public Category insert(Category category) {
	category.setId(null);
	return repo.save(category);
    }

    public Category update(Category category) {
	Category categorySaved = findById(category.getId());
	categorySaved.setName(category.getName());
	return repo.save(categorySaved);
    }

    public List<Category> findAll() {
	return repo.findAll();
    }

    public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
	PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
	return repo.findAll(pageRequest);
    }

    public Category findById(Integer id) {
	Optional<Category> categoryOptional = repo.findById(id);
	return categoryOptional.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Category.class.getName()));
    }

    public void delete(Integer id) {
	try {
	    repo.deleteById(id);
	} catch (DataIntegrityViolationException e) {
	    throw new DataIntegrityException("Nao eh possivel excluir uma categoria que possui um produto", e);
	}
    }

}
