package com.backend.blog.payloads;

import java.util.List;

public class CategoryResponse {

	private List<CategoryDto> content;
	private int pageNumber;
	private int pageSize;
	private Long totalElements;
	private int totalPages;
	private boolean lastPage;
	
	public List<CategoryDto> getContent() {
		return content;
	}
	public void setContent(List<CategoryDto> content) {
		this.content = content;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public Long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public boolean isLastPage() {
		return lastPage;
	}
	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}
	
}
