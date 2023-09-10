package com.backend.blog.services;

import java.util.List;

import com.backend.blog.payloads.CategoryDto;

public interface CategoryService {

	CategoryDto createCetagory(CategoryDto cetagoryDto);
	
	CategoryDto updateCetagory(CategoryDto cetagoryDto,Integer id);
	
	void deleteCategory(Integer id);
	
	CategoryDto getCetagory(Integer id);
	
	List<CategoryDto> getCetagories();
}
