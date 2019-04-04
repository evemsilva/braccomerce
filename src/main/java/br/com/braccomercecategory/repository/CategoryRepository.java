package br.com.braccomercecategory.repository;

import br.com.braccomercecategory.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository
		extends JpaRepository<Category, Integer> {

}
