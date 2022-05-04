package com.devmistri.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devmistri.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Category c WHERE lower(c.categoryTitle) = lower(:categoryTitle) and lower(c.categoryDescription) = lower(:categoryDescription)")
	Boolean existsByCategoryTitleIgnoreCaseAndCategoryDescriptionIgnoreCase(String categoryTitle,
			String categoryDescription);
	
	@Query("SELECT c FROM Category c WHERE lower(c.categoryTitle) = lower(:categoryTitle) and lower(c.categoryDescription) = lower(:categoryDescription)")
	Category findByCategoryTitleIgnoreCaseAndCategoryDescriptionIgnoreCase(String categoryTitle, String categoryDescription);

}
