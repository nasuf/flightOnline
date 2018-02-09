package com.flight.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class CustomSignUpMessage {
	
	@Id
	@NotNull(message = "Id can't be null.")
	private String id;
	
	@Field
	private String customId;
	
	@Field
	private String type;
	
	@Field
	private Long signUpDate;
	
	@Field
	private String title;
	
	@Field
	private String wechatId;
	
	@Field
	private String departureDate;
	
	@Field
	private String content;
	
	@Field
	private String openid;
	
	@Field
	private String nickName;
	
	@Field
	private String count;
	
	@Field
	private String item;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomId() {
		return customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSignUpDate() {
		return signUpDate;
	}

	public void setSignUpDate(Long signUpDate) {
		this.signUpDate = signUpDate;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
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

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "CustomSignUpMessage [id=" + id + ", customId=" + customId + ", type=" + type + ", signUpDate="
				+ signUpDate + ", title=" + title + ", wechatId=" + wechatId + ", departureDate=" + departureDate
				+ ", content=" + content + ", openid=" + openid + ", nickName=" + nickName + ", count=" + count
				+ ", item=" + item + "]";
	}
	
	

}
