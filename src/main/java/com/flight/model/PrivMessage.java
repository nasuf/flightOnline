package com.flight.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class PrivMessage {
	
	@Id
	@NotNull(message = "Id can't be null.")
	private String id;
	
	@Field
	private Long postDate;
	
	@Field
	private String poster;
	
	@Field
	private String posterNickName;
	
	@Field
	private String content;
	
	@Field
	private String wechatId;
	
	@Field
	private String type;

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

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPosterNickName() {
		return posterNickName;
	}

	public void setPosterNickName(String posterNickName) {
		this.posterNickName = posterNickName;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "PrivMessage [id=" + id + ", postDate=" + postDate + ", poster=" + poster + ", posterNickName="
				+ posterNickName + ", content=" + content + ", wechatId=" + wechatId + ", type=" + type + "]";
	}
	

}
