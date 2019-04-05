package br.com.braccomercecategory.domain;

public class CategoryBuilder {

    private Category category;

    private CategoryBuilder() {
	this.category = new Category();
    }

    public static CategoryBuilder aCategory() {
	return new CategoryBuilder();
    }

    public CategoryBuilder withId(Integer id) {
	this.category.setId(id);
	return this;
    }

    public CategoryBuilder withName(String name) {
	this.category.setName(name);
	return this;
    }

    public Category build() {
	return this.category;
    }

}
