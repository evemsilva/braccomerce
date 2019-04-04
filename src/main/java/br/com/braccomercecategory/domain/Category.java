package br.com.braccomercecategory.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Category implements Serializable {

    private static final long serialVersionUID = 6648566914792609366L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public Category() {}

    public Category(Integer id, String name) {
	    super();
	    this.id = id;
	    this.name = name;
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

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;
	Category category = (Category)o;
	return Objects.equals(id, category.id) && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
	return Objects.hash(id, name);
    }
}
