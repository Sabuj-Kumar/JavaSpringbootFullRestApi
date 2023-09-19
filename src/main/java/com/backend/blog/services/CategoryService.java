package com.backend.blog.services;
import com.backend.blog.payloads.CategoryDto;
import com.backend.blog.payloads.CategoryResponse;

public interface CategoryService {

	CategoryDto createCetagory(CategoryDto cetagoryDto);
	
	CategoryDto updateCetagory(CategoryDto cetagoryDto,Long id);
	
	void deleteCategory(Long id);
	
	CategoryDto getCetagory(Long id);
	
	CategoryResponse getCetagories(Integer pageNumber,Integer pageSize);
}
