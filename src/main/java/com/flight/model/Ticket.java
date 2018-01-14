package com.flight.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Ticket {
	
	@Id
	@NotNull(message = "Id can't be null.")
	private String id;

	@Field
	private String title;
	
	@Field
	private Boolean isSingleFlight;
	
	@Field
	private String airline;
	
	@Field
	private String price;
	
	@Field
	private String departureDateFrom;
	
	@Field
	private String departureDateTo;
	
	@Field
	private String departureCountry;
	
	@Field
	private String departureCity;
	
	@Field
	private String arrivalCountry;
	
	@Field
	private String arrivalCity;
	
	@Field
	private Boolean isTurning;
	
	@Field
	private String turningCity;
	
	@Field
	private String info;
	
	@Field
	private String publishDate;
	
	@Field
	private Integer publishWeekOfYear;
	
	@Field
	private String publisher;
	
	@Field
	private Boolean isDeleted;
	
	@Field
	private List<String> replyList;
	
	@Field
	private Integer replyCnt;
	
	@Field
	private String tag;

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

	public Boolean getIsSingleFlight() {
		return isSingleFlight;
	}

	public void setIsSingleFlight(Boolean isSingleFlight) {
		this.isSingleFlight = isSingleFlight;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDepartureDateFrom() {
		return departureDateFrom;
	}

	public void setDepartureDateFrom(String departureDateFrom) {
		this.departureDateFrom = departureDateFrom;
	}

	public String getDepartureCountry() {
		return departureCountry;
	}

	public void setDepartureCountry(String departureCountry) {
		this.departureCountry = departureCountry;
	}

	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public String getArrivalCountry() {
		return arrivalCountry;
	}

	public void setArrivalCountry(String arrivalCountry) {
		this.arrivalCountry = arrivalCountry;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public Boolean getIsTurning() {
		return isTurning;
	}

	public void setIsTurning(Boolean isTurning) {
		this.isTurning = isTurning;
	}

	public String getTurningCity() {
		return turningCity;
	}

	public void setTurningCity(String turningCity) {
		this.turningCity = turningCity;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
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
	
	public String getDepartureDateTo() {
		return departureDateTo;
	}

	public void setDepartureDateTo(String departureDateTo) {
		this.departureDateTo = departureDateTo;
	}
	

	public Integer getPublishWeekOfYear() {
		return publishWeekOfYear;
	}

	public void setPublishWeekOfYear(Integer publishWeekOfYear) {
		this.publishWeekOfYear = publishWeekOfYear;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", title=" + title + ", isSingleFlight=" + isSingleFlight + ", airline=" + airline
				+ ", price=" + price + ", departureDateFrom=" + departureDateFrom + ", departureDateTo="
				+ departureDateTo + ", departureCountry=" + departureCountry + ", departureCity=" + departureCity
				+ ", arrivalCountry=" + arrivalCountry + ", arrivalCity=" + arrivalCity + ", isTurning=" + isTurning
				+ ", turningCity=" + turningCity + ", info=" + info + ", publishDate=" + publishDate
				+ ", publishWeekOfYear=" + publishWeekOfYear + ", publisher=" + publisher + ", isDeleted=" + isDeleted
				+ ", replyList=" + replyList + ", replyCnt=" + replyCnt + ", tag=" + tag + "]";
	}



}
