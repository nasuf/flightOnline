package com.flight.model;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Custom {
	
	@Id
	@NotNull(message = "Id can't be null.")
	private String id;
	
	@Field
	private String title;
	
	@Field
	private String type;
	
	@Field
	private String subType;
	
	@Field
	private String content;
	
	@Field
	private List<String> urls;
	
	@Field
	private String poster;
	
	@Field
	private Long postDate;
	
	@Field
	private String posterAvatarUrl;
	
	@Field
	private String posterNickName;
	
	@Field
	private Boolean isDeleted;
	
	@Field
	private Long deletedDate;
	
	@Field
	private List<String> replyList;
	
	@Field
	private Integer replyCnt;
	
	@Field
	private String joinType;
	
	@Field
	private Set<CustomSignUpMessage> signUpSet;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
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

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<String> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<String> replyList) {
		this.replyList = replyList;
	}

	public Integer getReplyCnt() {
		return replyCnt;
	}

	public void setReplyCnt(Integer replyCnt) {
		this.replyCnt = replyCnt;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public String getPosterAvatarUrl() {
		return posterAvatarUrl;
	}

	public void setPosterAvatarUrl(String posterAvatarUrl) {
		this.posterAvatarUrl = posterAvatarUrl;
	}

	public String getPosterNickName() {
		return posterNickName;
	}

	public void setPosterNickName(String posterNickName) {
		this.posterNickName = posterNickName;
	}

	public Long getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Long deletedDate) {
		this.deletedDate = deletedDate;
	}

	public Set<CustomSignUpMessage> getSignUpSet() {
		return signUpSet;
	}

	public void setSignUpSet(Set<CustomSignUpMessage> signUpSet) {
		this.signUpSet = signUpSet;
	}

	@Override
	public String toString() {
		return "Custom [id=" + id + ", title=" + title + ", type=" + type + ", subType=" + subType + ", content="
				+ content + ", urls=" + urls + ", poster=" + poster + ", postDate=" + postDate + ", posterAvatarUrl="
				+ posterAvatarUrl + ", posterNickName=" + posterNickName + ", isDeleted=" + isDeleted + ", deletedDate="
				+ deletedDate + ", replyList=" + replyList + ", replyCnt=" + replyCnt + ", joinType=" + joinType
				+ ", signUpSet=" + signUpSet + "]";
	}
	

}
