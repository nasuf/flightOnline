package com.flight.model;


import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Message {
	
	@Id
	@NotNull(message = "Id can't be null.")
	private String id;
	
	@Field
	private String type; // TOPIC | REPLY
	
	@Field
	private String beRepliedPoster;
	
	@Field
	private String beRepliedTopicId;
	
	@Field
	private String beRepliedTopicTitle;
	
	@Field
	private String beRepliedMessageId;
	
	@Field
	private Long beRepliedPostDate;
	
	@Field
	private String subject; // TICKET | QUESTION | TOURISM | CUSTOM
	
	@Field
	private Long postDate;
	
	@Field
	private String poster;
	
	@Field
	private String posterAvatarUrl;
	
	@Field
	private String posterNickName;
	
	@Field
	private Boolean isDeleted;
	
	@Field
	private Long deletedDate;
	
	@Field
	private Integer replyCnt;
	
	@Field
	private Boolean isSticky;
	
	@Field
	private String content;
	
	@Field
	private List<String> replyList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public Integer getReplyCnt() {
		return replyCnt;
	}

	public void setReplyCnt(Integer replyCnt) {
		this.replyCnt = replyCnt;
	}

	public Boolean getIsSticky() {
		return isSticky;
	}

	public void setIsSticky(Boolean isSticky) {
		this.isSticky = isSticky;
	}
	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBeRepliedPoster() {
		return beRepliedPoster;
	}

	public void setBeRepliedPoster(String beRepliedPoster) {
		this.beRepliedPoster = beRepliedPoster;
	}

	public String getBeRepliedTopicId() {
		return beRepliedTopicId;
	}

	public void setBeRepliedTopicId(String beRepliedTopicId) {
		this.beRepliedTopicId = beRepliedTopicId;
	}

	public List<String> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<String> replyList) {
		this.replyList = replyList;
	}

	public String getBeRepliedMessageId() {
		return beRepliedMessageId;
	}

	public void setBeRepliedMessageId(String beRepliedMessageId) {
		this.beRepliedMessageId = beRepliedMessageId;
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

	public Long getBeRepliedPostDate() {
		return beRepliedPostDate;
	}

	public void setBeRepliedPostDate(Long beRepliedPostDate) {
		this.beRepliedPostDate = beRepliedPostDate;
	}

	public String getBeRepliedTopicTitle() {
		return beRepliedTopicTitle;
	}

	public void setBeRepliedTopicTitle(String beRepliedTopicTitle) {
		this.beRepliedTopicTitle = beRepliedTopicTitle;
	}

	public Long getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Long deletedDate) {
		this.deletedDate = deletedDate;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", type=" + type + ", beRepliedPoster=" + beRepliedPoster + ", beRepliedTopicId="
				+ beRepliedTopicId + ", beRepliedTopicTitle=" + beRepliedTopicTitle + ", beRepliedMessageId="
				+ beRepliedMessageId + ", beRepliedPostDate=" + beRepliedPostDate + ", subject=" + subject
				+ ", postDate=" + postDate + ", poster=" + poster + ", posterAvatarUrl=" + posterAvatarUrl
				+ ", posterNickName=" + posterNickName + ", isDeleted=" + isDeleted + ", deletedDate=" + deletedDate
				+ ", replyCnt=" + replyCnt + ", isSticky=" + isSticky + ", content=" + content + ", replyList="
				+ replyList + "]";
	}

}
