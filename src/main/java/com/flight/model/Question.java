package com.flight.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Question {
	
	@Id
	@NotNull(message = "Id can't be null.")
	private String id;
	
	@Field
	private String title;
	
	@Field
	private String postDate;
	
	@Field
	private String posterAvatarUrl;
	
	@Field
	private String poster;
	
	@Field
	private String posterNickName;
	
	@Field
	private String content;
	
	@Field
	private Boolean isDeleted;
	
	@Field
	private List<String> replyList;
	
	@Field
	private Integer replyCnt;
	
	@Field
	private Boolean isFixed;
	
	@Field
	private String bestAnswer;
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
		result = prime * result + ((isFixed == null) ? 0 : isFixed.hashCode());
		result = prime * result + ((postDate == null) ? 0 : postDate.hashCode());
		result = prime * result + ((poster == null) ? 0 : poster.hashCode());
		result = prime * result + ((posterAvatarUrl == null) ? 0 : posterAvatarUrl.hashCode());
		result = prime * result + ((posterNickName == null) ? 0 : posterNickName.hashCode());
		result = prime * result + ((replyCnt == null) ? 0 : replyCnt.hashCode());
		result = prime * result + ((replyList == null) ? 0 : replyList.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((bestAnswer == null) ? 0 : bestAnswer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		
		return true;
	}

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

	public String getPostDate() {
		return postDate;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	public String getPosterAvatarUrl() {
		return posterAvatarUrl;
	}

	public void setPosterAvatarUrl(String posterAvatarUrl) {
		this.posterAvatarUrl = posterAvatarUrl;
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

	public Boolean getIsFixed() {
		return isFixed;
	}

	public void setIsFixed(Boolean isFixed) {
		this.isFixed = isFixed;
	}

	public String getPosterNickName() {
		return posterNickName;
	}

	public void setPosterNickName(String posterNickName) {
		this.posterNickName = posterNickName;
	}

	public String getBestAnswer() {
		return bestAnswer;
	}

	public void setBestAnswer(String bestAnswer) {
		this.bestAnswer = bestAnswer;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", title=" + title + ", postDate=" + postDate + ", posterAvatarUrl="
				+ posterAvatarUrl + ", poster=" + poster + ", posterNickName=" + posterNickName + ", content=" + content
				+ ", isDeleted=" + isDeleted + ", replyList=" + replyList + ", replyCnt=" + replyCnt + ", isFixed="
				+ isFixed + ", bestAnswer=" + bestAnswer + "]";
	}
	

}
