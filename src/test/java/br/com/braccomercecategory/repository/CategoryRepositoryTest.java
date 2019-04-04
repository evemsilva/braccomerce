package br.com.braccomercecategory.repository;

import br.com.braccomercecategory.domain.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testSave(){
	final Category category = categoryRepository.save(new Category(1, "Roupas"));
	Assert.assertEquals(Integer.valueOf(1), category.getId());
	Assert.assertEquals("Roupas", category.getName());
    }

}
