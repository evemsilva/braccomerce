package br.com.braccomercecategory.service;

import br.com.braccomercecategory.domain.Category;
import br.com.braccomercecategory.repository.CategoryRepository;
import br.com.braccomercecategory.service.exceptions.DataIntegrityException;
import br.com.braccomercecategory.service.exceptions.ObjectNotFoundException;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static java.lang.Integer.valueOf;
import static java.util.Arrays.asList;
import static java.util.Optional.of;

import static br.com.braccomercecategory.domain.CategoryBuilder.aCategory;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.Direction.ASC;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @InjectMocks private CategoryService categoryService = new CategoryService();

    @Mock private CategoryRepository categoryRepository;

    @Rule public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testInsert() {
	final Category categoryGames = aCategory().withId(1).withName("Games").build();
	when(categoryRepository.save(any(Category.class))).thenReturn(categoryGames);
	final Category categorySaved = categoryService.insert(aCategory().withName("Games").build());
	assertEquals(valueOf(1), categorySaved.getId());
	assertEquals("Games", categorySaved.getName());
    }

    @Test
    public void testUpdate() {
	final Category categoryGamesBeforeUpdated = aCategory().withId(1).withName("Games").build();
	final Category categoryGamesUpdated = aCategory().withId(1).withName("Games Updated").build();
	when(categoryRepository.findById(anyInt())).thenReturn(of(categoryGamesBeforeUpdated));
	when(categoryRepository.save(any(Category.class))).thenReturn(categoryGamesUpdated);
	final Category categorySaved = categoryService.update(categoryGamesBeforeUpdated);
	assertEquals(valueOf(1), categorySaved.getId());
	assertEquals("Games Updated", categorySaved.getName());
    }

    @Test
    public void testUpdateWithCategoryNotFound() {
	final Category categoryGamesBeforeUpdated = aCategory().withId(1).withName("Games").build();
	final ObjectNotFoundException exception = new ObjectNotFoundException(String.format("Objeto não encontrado! Id: 1, Tipo: %s", Category.class.getName()));
	when(categoryRepository.findById(anyInt())).thenThrow(exception);
	exceptionRule.expect(ObjectNotFoundException.class);
	exceptionRule.expectMessage("Objeto não encontrado! Id: 1, Tipo: br.com.braccomercecategory.domain.Category");
	categoryService.update(categoryGamesBeforeUpdated);
    }

    @Test
    public void testfindAll() {
	final List<Category> categoriesExpected = asList(aCategory().withId(1).withName("Cars").build(), aCategory().withId(2).withName("Clothes").build());
	when(categoryRepository.findAll()).thenReturn(categoriesExpected);
	final List<Category> categoriesActual = categoryService.findAll();
	assertEquals(2, categoriesActual.size());
    }

    @Test
    public void testFindPage() {
	final Page<Category> categoriesPageExpected = new PageImpl<>(asList(aCategory().withId(1).withName("Flags").build(), aCategory().withId(2).withName("Banners").build()));
	int page = 0, linesPerPage = 20;
	final String orderBy = "id";
	when(categoryRepository.findAll(PageRequest.of(page, linesPerPage, ASC, orderBy))).thenReturn(categoriesPageExpected);
	final Page<Category> categoriesPageActual = categoryService.findPage(page, linesPerPage, orderBy, ASC.name());
	assertEquals(1, categoriesPageActual.getTotalPages());
	assertEquals(2, categoriesPageActual.getTotalElements());
	assertEquals(valueOf(1), categoriesPageActual.getContent().get(0).getId());
	assertEquals("Flags", categoriesPageActual.getContent().get(0).getName());
	assertEquals(valueOf(2), categoriesPageActual.getContent().get(1).getId());
	assertEquals("Banners", categoriesPageActual.getContent().get(1).getName());
    }

    @Test
    public void testFindById() {
	final Category categoryTrip = aCategory().withId(1).withName("Trip").build();
	when(categoryRepository.findById(anyInt())).thenReturn(of(categoryTrip));
	final Category categoryActual = categoryService.findById(1);
	assertEquals(valueOf(1), categoryActual.getId());
	assertEquals("Trip", categoryActual.getName());
    }

    @Test
    public void testDelete() {
	categoryService.delete(1);
    }

    @Test(expected = DataIntegrityException.class)
    public void testDeleteWithDataIntegrityException() {
	doThrow(DataIntegrityViolationException.class).when(categoryRepository).deleteById(anyInt());
	categoryService.delete(1);
    }
}