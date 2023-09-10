package com.backend.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryDto {

    private Integer categoryId;
    
    @NotBlank
    @Size(min = 6,message = "Category must be minimum 6")
	private String categoryTitle;
    
    @NotBlank
    @Size(max = 20,message = "Category description maximum 20")
	private String discription;
	
	public CategoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryTitle() {
		return categoryTitle;
	}
	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	
}
