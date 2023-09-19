package com.backend.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.blog.payloads.ApiResponse;
import com.backend.blog.payloads.CategoryDto;
import com.backend.blog.payloads.CategoryResponse;
import com.backend.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categortDto){
		
		CategoryDto categoryDto = this.categoryService.createCetagory(categortDto);
		
		return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("catId") Long id){
		
		CategoryDto updatedCategoryDto = this.categoryService.updateCetagory(categoryDto, id);
		
		return new ResponseEntity<CategoryDto>(updatedCategoryDto,HttpStatus.OK); 
	}
	
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("catId") Long id){
		
		this.categoryService.deleteCategory(id);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Delete Succesfully.",true),HttpStatus.OK);
	}
	
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("catId") Long id){
		
		CategoryDto updatedCategoryDto = this.categoryService.getCetagory(id);
		
		return new ResponseEntity<CategoryDto>(updatedCategoryDto,HttpStatus.OK); 
	}
	
	@GetMapping("/")
	public ResponseEntity<CategoryResponse> getCategories(
			@RequestParam(value ="pageNumber", defaultValue = "0",required = false) Integer pageNumber,
			@RequestParam(value ="pageSize", defaultValue = "5",required = false) Integer pageSize
			){
		 
		CategoryResponse updatedCategoryDto = this.categoryService.getCetagories(pageNumber,pageSize);
		
		return new ResponseEntity<CategoryResponse>(updatedCategoryDto,HttpStatus.OK); 
	}
}
