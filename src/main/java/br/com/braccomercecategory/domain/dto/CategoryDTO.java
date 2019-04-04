package br.com.braccomercecategory.domain.dto;

import br.com.braccomercecategory.domain.Category;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = 4032836526810077803L;

    private Integer id;
	
    @NotEmpty
    @Length(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres")
    private String name;

    public CategoryDTO() {}

    public CategoryDTO(Category category) {
	    super();
	    this.id = category.getId();
	    this.name = category.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
