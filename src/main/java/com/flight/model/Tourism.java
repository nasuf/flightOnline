package com.flight.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Tourism {
	
	@Id
	@NotNull(message = "Id can't be null.")
	private String id;
	
	@Field
	private String title;
	
	@Field
	private String poster;
	
	@Field
	private String postDate;
	
	@Field
	private String signUpEndDate;
	
	@Field
	private String festival;
	
	@Field
	private String departureDate;
	
	@Field
	private String departureLocation;
	
	@Field
	private String arrivalLocation;

	@Field
	private String duration;
	
	@Field
	private String content;
	
	@Field
	private Integer participantCnt;
	
	@Field
	private List<String> replyList;
	
	@Field
	private Integer replyCnt;
	
	@Field
	private List<String> participants;
	
	@Field
	private Boolean isFull;
	
	@Field
	private Boolean isEnded;
	
	@Field
	private Boolean isDeleted;
	
	@Field
	private List<String> signUpList;
	
	@Field
	private String contact;
	
	@Field
	private Integer postWeekOfYear;
	
	@Field
	private Integer postYear;

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

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getPostDate() {
		return postDate;
	}
	
	public String getDepartureLocation() {
		return departureLocation;
	}

	public void setDepartureLocation(String departureLocation) {
		this.departureLocation = departureLocation;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	public String getSignUpEndDate() {
		return signUpEndDate;
	}

	public void setSignUpEndDate(String signUpEndDate) {
		this.signUpEndDate = signUpEndDate;
	}

	public String getFestival() {
		return festival;
	}

	public void setFestival(String festival) {
		this.festival = festival;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getParticipantCnt() {
		return participantCnt;
	}

	public void setParticipantCnt(Integer participantCnt) {
		this.participantCnt = participantCnt;
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

	public List<String> getParticipants() {
		return participants;
	}

	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}

	public Boolean getIsFull() {
		return isFull;
	}

	public void setIsFull(Boolean isFull) {
		this.isFull = isFull;
	}

	public Boolean getIsEnded() {
		return isEnded;
	}

	public void setIsEnded(Boolean isEnded) {
		this.isEnded = isEnded;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<String> getSignUpList() {
		return signUpList;
	}

	public void setSignUpList(List<String> signUpList) {
		this.signUpList = signUpList;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getArrivalLocation() {
		return arrivalLocation;
	}

	public void setArrivalLocation(String arrivalLocation) {
		this.arrivalLocation = arrivalLocation;
	}

	public Integer getPostWeekOfYear() {
		return postWeekOfYear;
	}

	public void setPostWeekOfYear(Integer postWeekOfYear) {
		this.postWeekOfYear = postWeekOfYear;
	}

	public Integer getPostYear() {
		return postYear;
	}

	public void setPostYear(Integer postYear) {
		this.postYear = postYear;
	}

	@Override
	public String toString() {
		return "Tourism [id=" + id + ", title=" + title + ", poster=" + poster + ", postDate=" + postDate
				+ ", signUpEndDate=" + signUpEndDate + ", festival=" + festival + ", departureDate=" + departureDate
				+ ", departureLocation=" + departureLocation + ", arrivalLocation=" + arrivalLocation + ", duration="
				+ duration + ", content=" + content + ", participantCnt=" + participantCnt + ", replyList=" + replyList
				+ ", replyCnt=" + replyCnt + ", participants=" + participants + ", isFull=" + isFull + ", isEnded="
				+ isEnded + ", isDeleted=" + isDeleted + ", signUpList=" + signUpList + ", contact=" + contact
				+ ", postWeekOfYear=" + postWeekOfYear + ", postYear=" + postYear + "]";
	}
	
	
	
	

}
