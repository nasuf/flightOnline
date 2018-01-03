package com.flight.model;

import java.util.List;

import org.springframework.data.domain.Page;

public class PageEntity<T> {

	private int pageNumber;
	private int pageSize;
	private int totalPages;
	private long totalSize;
	private List<T> content;

	public PageEntity() {
	}

	public PageEntity(Page<T> page) {
		this.pageNumber = page.getNumber();
		this.pageSize = page.getSize();
		this.totalSize = page.getTotalElements();
		this.totalPages = page.getTotalPages();
		this.content = page.getContent();
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

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	@Override
	public String toString() {

		return String.format("PageEntity [content=%s, totalSize=$s, pageNumber=%s, pageSize=%s, totalPage=%s", content,
				totalSize, pageNumber, pageSize, totalPages);
	}

}
