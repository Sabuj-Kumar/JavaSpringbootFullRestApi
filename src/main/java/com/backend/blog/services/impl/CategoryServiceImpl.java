package com.backend.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.blog.entity.Category;
import com.backend.blog.exceptions.ResourceNotFoundExceptions;
import com.backend.blog.payloads.CategoryDto;
import com.backend.blog.repositories.CategoryRepository;
import com.backend.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository cetagoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCetagory(CategoryDto categoryDto) {
		
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		
		Category addCategory = this.cetagoryRepo.save(cat);
		
		return this.modelMapper.map(addCategory, CategoryDto.class); 
	}

	@Override
	public CategoryDto updateCetagory(CategoryDto cetagoryDto, Integer id) {
		
		Category category = this.cetagoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundExceptions("Category"," Category id ",id));       
		
		category.setCategoryTitle(cetagoryDto.getCategoryTitle());
		
		category.setDiscription(cetagoryDto.getDiscription());
		
		Category updatedCat = this.cetagoryRepo.save(category);
		
		return this.modelMapper.map(updatedCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer id) {
		
		Category category = this.cetagoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundExceptions("Category"," Category id ",id));
		
		this.cetagoryRepo.delete(category); 
	}

	@Override
	public CategoryDto getCetagory(Integer id) {
		
		Category category = this.cetagoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundExceptions("Category"," Category id ",id));
		
		return this.modelMapper.map(category, CategoryDto.class); 
	}

	@Override
	public List<CategoryDto> getCetagories() {
		
		List<Category> categoryList = this.cetagoryRepo.findAll();
		
		List<CategoryDto> categoryDtos = categoryList.stream().map((cat)-> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		
		return categoryDtos;
	}
}
