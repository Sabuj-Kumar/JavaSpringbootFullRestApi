package com.backend.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.backend.blog.entity.Comments;

public class PostDto {

	private Long id;
	
	private String title;
	
	private String content;
    
	private String imageName;
	
	private Date addedDate;
	
	private UserDto user;
	
	private CategoryDto category;
	
	private Set<CommentDto> comments = new HashSet<>();
	
	public Set<CommentDto> getComments() {
		return comments;
	}
	public void setComments(Set<CommentDto> comments) {
		this.comments = comments;
	}
	public PostDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getImageName() {
		return imageName;
	}
    
	

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public CategoryDto getCategory() {
		return category;
	}

	public void setCategory(CategoryDto category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
