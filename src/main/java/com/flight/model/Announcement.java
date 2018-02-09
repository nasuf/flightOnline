package com.flight.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Announcement {
	
	@Id
	@NotNull(message = "Id can't be null.")
	private String id;
	
	private Long postDate;
	
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getPostDate() {
		return postDate;
	}

	public void setPostDate(Long postDate) {
		this.postDate = postDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Announcement [id=" + id + ", postDate=" + postDate + ", content=" + content + "]";
	}
	

}
