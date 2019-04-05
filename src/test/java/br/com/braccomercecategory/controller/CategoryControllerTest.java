package br.com.braccomercecategory.controller;

import br.com.braccomercecategory.domain.Category;
import br.com.braccomercecategory.domain.dto.CategoryDTO;
import br.com.braccomercecategory.service.CategoryService;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static br.com.braccomercecategory.domain.CategoryBuilder.aCategory;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {

    @InjectMocks private CategoryController categoryController = new CategoryController();

    @Mock private CategoryService categoryService;

    @Test
    public void testFindById() {
	final Category categoryFootball = aCategory().withId(1).withName("Football").build();
	when(categoryService.findById(anyInt())).thenReturn(categoryFootball);
	final ResponseEntity<Category> categoryActual = categoryController.findById(1);
	assertEquals(HttpStatus.OK, categoryActual.getStatusCode());
	assertEquals(Integer.valueOf(1), categoryActual.getBody().getId());
	assertEquals("Football", categoryActual.getBody().getName());
    }

    @Test
    public void testInsert() {
	final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
	mockHttpServletRequest.setServerPort(8080);
	RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));
	final Category categoryMen = aCategory().withId(1).withName("Men").build();
	when(categoryService.insert(any(Category.class))).thenReturn(categoryMen);
	final ResponseEntity<Void> response = categoryController.insert(new CategoryDTO(aCategory().withId(1).withName("Men").build()));
	assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdate() {
	final Category categoryWomen = aCategory().withId(1).withName("Women").build();
	when(categoryService.update(any(Category.class))).thenReturn(categoryWomen);
	final ResponseEntity<Void> response = categoryController.update(new CategoryDTO(aCategory().withId(1).withName("Women").build()), 1);
	assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDelete() {
	final ResponseEntity<Void> response = categoryController.delete(1);
	assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testFindAll() {
	when(categoryService.findAll()).thenReturn(Arrays.asList(aCategory().build(), aCategory().build(), aCategory().build()));
	final ResponseEntity<List<CategoryDTO>> response = categoryController.findAll();
	assertEquals(HttpStatus.OK, response.getStatusCode());
	assertEquals(3, response.getBody().size());
    }

    @Test
    public void testFindPage() {
	when(categoryService.findPage(0,20,"id","ASC")).
			thenReturn(new PageImpl<>(Arrays.asList(aCategory().build(), aCategory().build(), aCategory().build())));
	final ResponseEntity<Page<CategoryDTO>> response = categoryController.findPage(0, 20, "id", "ASC");
	assertEquals(HttpStatus.OK, response.getStatusCode());
	assertEquals(3, response.getBody().getTotalElements());
	assertEquals(1, response.getBody().getTotalPages());
	assertEquals(3, response.getBody().getContent().size());
    }
}