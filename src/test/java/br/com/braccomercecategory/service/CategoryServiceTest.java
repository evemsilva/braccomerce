package br.com.braccomercecategory.service;

import br.com.braccomercecategory.domain.Category;
import br.com.braccomercecategory.repository.CategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService = new CategoryService();

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void testInsert() {
	Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(new Category(1, "Eletrônicos"));
	final Category eletronicos = categoryService.insert(new Category(1, "Eletrônicos"));
	Assert.assertEquals(Integer.valueOf(1), eletronicos.getId());
	Assert.assertEquals("Eletrônicos", eletronicos.getName());
    }

    @Test
    public void update() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void findPage() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void delete() {
    }
}