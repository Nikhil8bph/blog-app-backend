package com.devmistri.blog.utils;

import com.devmistri.blog.entities.Category;
import com.devmistri.blog.payloads.CategoryDto;

public class CategoryEntityObjectConverter {

	public static Category dtoToCategory(CategoryDto categoryDto) {
		Category category = new Category();
		category.setCategoryId(categoryDto.getCategoryId());
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		return category;
	}
	
	public static CategoryDto categoryToDto(Category category) {
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setCategoryId(category.getCategoryId());
		categoryDto.setCategoryTitle(category.getCategoryTitle());
		categoryDto.setCategoryDescription(category.getCategoryDescription());
		return categoryDto;
	}
	
	public static CategoryDto categoryToDtoEntry(Category category) {
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setCategoryTitle(category.getCategoryTitle());
		categoryDto.setCategoryDescription(category.getCategoryDescription());
		return categoryDto;
	}
	
	public static Category dtoToCategoryEntry(CategoryDto categoryDto) {
		Category category = new Category();
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		return category;
	}
}
