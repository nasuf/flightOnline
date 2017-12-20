package com.flight.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Ticket {

	@Field
	private String title;
	
	@Field
	private Boolean isSingleFlight;
	
	@Field
	private String price;
	
	@Field
	private Long departureDate;
	
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
	private String remark;
	
	@Field
	private Long pulishDate;

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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Long getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Long departureDate) {
		this.departureDate = departureDate;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getPulishDate() {
		return pulishDate;
	}

	public void setPulishDate(Long pulishDate) {
		this.pulishDate = pulishDate;
	}

	@Override
	public String toString() {
		return "Ticket [title=" + title + ", isSingleFlight=" + isSingleFlight + ", price=" + price + ", departureDate="
				+ departureDate + ", departureCountry=" + departureCountry + ", departureCity=" + departureCity
				+ ", arrivalCountry=" + arrivalCountry + ", arrivalCity=" + arrivalCity + ", isTurning=" + isTurning
				+ ", turningCity=" + turningCity + ", remark=" + remark + ", pulishDate=" + pulishDate + "]";
	}
	
	
}
