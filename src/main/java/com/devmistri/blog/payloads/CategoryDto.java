package com.devmistri.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryDto {
	
	private int categoryId;
	
	@NotBlank
	@Size(min=4,max=100)
	private String categoryTitle;
	
	@NotBlank
	@Size(min=10,max=250)
	private String categoryDescription;

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	@Override
	public String toString() {
		return "CategoryDto [categoryId=" + categoryId + ", categoryTitle=" + categoryTitle + ", categoryDescription="
				+ categoryDescription + "]";
	}
	
}
