package com.devmistri.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devmistri.blog.entities.Category;
import com.devmistri.blog.exceptions.ResourceNotFoundException;
import com.devmistri.blog.payloads.CategoryDto;
import com.devmistri.blog.repositories.CategoryRepo;
import com.devmistri.blog.services.CategoryService;
import com.devmistri.blog.utils.CategoryEntityObjectConverter;

@Service
public class CategoryServiceImpl implements CategoryService{

	Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		logger.info("Category: create request received");
		Category cat = this.modelMapper.map(categoryDto,Category.class);
		Boolean isExists = categoryRepo.existsByCategoryTitleIgnoreCaseAndCategoryDescriptionIgnoreCase(categoryDto.getCategoryTitle(), categoryDto.getCategoryDescription());
		Category addedCat = null;
		logger.info("Category: boolean check existance : "+isExists);
		if(!isExists) {
			addedCat = this.categoryRepo.save(cat);
			logger.info(addedCat.toString());
			logger.info("Category: created successfully with userId : "+addedCat.getCategoryId());
		}
		else {
			 addedCat = this.categoryRepo.findByCategoryTitleIgnoreCaseAndCategoryDescriptionIgnoreCase(categoryDto.getCategoryTitle(), categoryDto.getCategoryDescription());
		}
		return this.modelMapper.map(addedCat,CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		logger.info("Category: update request received for userId : "+categoryId);
		logger.info("Category: "+String.valueOf(categoryDto));
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category: ", "category Id", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedCat = this.categoryRepo.save(cat);
		logger.info("Category: "+updatedCat.toString());
		return this.modelMapper.map(updatedCat,CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category Id", categoryId));
		logger.info("Category: "+cat.toString());
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category Id", categoryId));
		logger.info("Category: "+cat.toString());
		return this.modelMapper.map(cat,CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> categoryDtos = categories.stream().map((cat)->this.modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
		logger.info("Category: "+categoryDtos.toString());
		return categoryDtos;
	}

}
